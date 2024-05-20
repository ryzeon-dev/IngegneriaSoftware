package system.scheduling.schedulingStrategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;
import dao.interfaces.AirportDaoI;
import dao.interfaces.FlightRouteDaoI;
import dao.interfaces.ParkingDaoI;
import model.Aircraft;
import model.Airport;
import model.Flight;
import model.FlightRoute;
import system.scheduling.AirportGraph;
import system.scheduling.AirportWeighted;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SimpleSchedule  implements SchedulingStrategy{
    private FlightRouteDaoI flightRouteDao;
    private AirportDaoI airportDao;
    private ParkingDaoI parkingDao;
    private AirportGraph graph=null;
    private final int turnArountAmountMin=30;
    private final int iterations=2;
    private Vector<Flight> flights= new Vector<>();
    private Map<Airport,Boolean> visited= new HashMap<>();
    private LocalDateTime StartTime=LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private Map<Airport,Vector<Aircraft>> aircraftLocation=new HashMap<>();
    private Map<Aircraft,LocalDateTime> aircraftTime=new HashMap<>();

    public SimpleSchedule(FlightRouteDaoI flightRouteDao,AirportDaoI airportDao,ParkingDaoI parkingDao){
        this.airportDao=airportDao;
        this.flightRouteDao=flightRouteDao;
        this.parkingDao=parkingDao;
    }

    @Override
    public Vector<Flight> run() {
        System.out.println("Running Scheduling Algorithm.\n");
        graph=buildGraphFromFlightRoute();
        //Keep track of where aircraft are during now()bfs
        //Run bfs algorithm
        for (Airport airport : graph.getVertexList()) {
            Vector<Aircraft> parkedAircrafts=parkingDao.getParked(airport.icao);
            for (Aircraft a : parkedAircrafts) {
                aircraftTime.put(a,StartTime);
            }
            aircraftLocation.put(airport, parkedAircrafts);
        }
        for (int iteration = 0; iteration < iterations; iteration++) {
            exploreBfs();
        }
        
        return null;
    }

    private void exploreBfs(){
        for (Airport airport : graph.getVertexList()) {
            System.out.println("Starting Bfs from: "+airport.toString());
            bfs(graph, airport);
        }
    }

    private void bfs(AirportGraph G,Airport source){
        for (Airport airport : graph.getVertexList()) {
            visited.put(airport, false);
        }
        Queue<Airport> fifo= new LinkedList<>();
        fifo.add(source);
        visited.replace(source, true);
        while (!fifo.isEmpty()) {
            Airport airport= fifo.remove();
            //Get aircraft that are in this airport and sort them by range.

            //Build parked aircraft priorityQueue
            PriorityQueue<Aircraft> parkedAircraftsPq = new PriorityQueue<>(new Comparator<Aircraft>() {
                @Override
                public int compare(Aircraft aircraft0, Aircraft aircraft1) {
                    return aircraft1.range - aircraft0.range;
                }
            });
            for (Aircraft a : aircraftLocation.get(airport)) {
                parkedAircraftsPq.add(a);
            }

            var adjacentAirports=graph.getAdjacentVertex(airport);
            PriorityQueue<AirportWeighted> airportPq = new PriorityQueue<>(adjacentAirports.size(),new Comparator<AirportWeighted>() {

                @Override
                public int compare(AirportWeighted airport0, AirportWeighted airport1) {
                    return airport1.weight - airport0.weight;
                }
            });
            for (var adjacentAirport : adjacentAirports) {
                airportPq.add(adjacentAirport);
            }

            while (!airportPq.isEmpty() && !parkedAircraftsPq.isEmpty()) {
                var nextAirport=airportPq.remove();
                var nextAircraft=parkedAircraftsPq.remove();
                if (visited.get(nextAirport.airport) != true) {
                    aircraftLocation.get(airport).remove(nextAircraft);
                    if(aircraftLocation.containsKey(nextAirport.airport)){
                        aircraftLocation.get(nextAirport.airport).add(nextAircraft);
                    }else{
                        Vector<Aircraft> aircrafts=new Vector<>();
                        aircrafts.add(nextAircraft);
                        aircraftLocation.put(nextAirport.airport,aircrafts);
                    }

                    //Departure
                    LocalDateTime departure=aircraftTime.get(nextAircraft).plusMinutes(turnArountAmountMin);
                    //New AircraftTime
                    LocalDateTime eta=departure.plusMinutes(nextAirport.duration);
                    aircraftTime.replace(nextAircraft,eta);
                    System.out.println("flight:\n"+airport.toString()+ "->" + nextAirport.airport.toString());
                    System.out.println("FlightDuration:"+nextAirport.duration);
                    System.out.println("Departure:\n"+departure.format(dateTimeFormat));
                    System.out.println("Estimated arrival: "+eta.format(dateTimeFormat));
                    System.out.println("Assigned aircraft:\n"+nextAircraft.toString());
                    var flight=new Flight(
                        0, 
                        departure.format(dateTimeFormat), 
                        0, 
                        nextAirport.routeId, 
                        nextAircraft.plate,
                        null, 
                        null, 
                        null
                    );
                    flights.add(flight);
                    //Visit the next airports
                    visited.replace(nextAirport.airport, true);
                    fifo.add(nextAirport.airport);
                }
            }
        }
    }
    
    //Bring aircraft back to the original airport following the minimum path.
    private void bringBackAircrafts(){

    }

    private AirportGraph buildGraphFromFlightRoute(){
        AirportGraph graph= new AirportGraph();
        Vector<FlightRoute> routes= flightRouteDao.getAll();
        Vector<Airport> airports=airportDao.getAll();
        Map<String,Airport> airportDict=new HashMap<>();

        for (var airport : airports) {
            airportDict.put(airport.icao, airport);
        }

        for (FlightRoute r : routes) {
            try {
                Airport departure=airportDict.get(r.departure);
                Airport arrival=airportDict.get(r.arrival);
                //TODO add StepOver
                graph.addEdge(departure, arrival, r.distance,r.id,r.duration);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return graph;
    }
    //Check if aircraft cat fly to given airport.
    private boolean canFly(Aircraft aircraft,AirportWeighted airportWeighted){
        //Check if the aircraft class is compatible with the airport class.
        if (!aircraft.canGo(airportWeighted.airport)){
            return false;
        }
        if(aircraft.range < airportWeighted.weight){
            return false;
        }
        return true;
    }
}
