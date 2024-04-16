package system.scheduling.schedulingStrategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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

public class SimpleSchedule  implements SchedulingStrategy{
    private FlightRouteDaoI flightRouteDao;
    private AirportDaoI airportDao;
    private ParkingDaoI parkingDao;
    private AirportGraph graph=null;

    private Map<Airport,Boolean> visited= new HashMap<>();
    private Map<Airport,Vector<Aircraft>> aircraftLocation =new HashMap<>();

    public SimpleSchedule(FlightRouteDaoI flightRouteDao,AirportDaoI airportDao,ParkingDaoI parkingDao){
        this.airportDao=airportDao;
        this.flightRouteDao=flightRouteDao;
        this.parkingDao=parkingDao;
    }

    @Override
    public Vector<Flight> run() {
        System.out.println("Running Scheduling Algorithm.\n");
        graph=buildGraphFromFlightRoute();
        //Keep track of where aircraft are during dfs
        //Run dfs algorithm
        exploreBfs();
        System.out.println(graph);
        return null;
    }

    private void exploreBfs(){
        for (Airport airport : graph.getVertexList()) {
            System.out.println("Starting Bfs from: "+airport.toString());
            Vector<Aircraft> parkedAircrafts=parkingDao.getParked(airport.icao);
            if (!parkedAircrafts.isEmpty()){
                System.out.println("ParkedAircrafts: "+airport.toString());
                for (Aircraft a : parkedAircrafts) {
                    System.out.println(a.toString());
                }
            }
            bfs(graph, airport);
        }
    }

    private void bfs(AirportGraph G,Airport source){
        for (Airport airport : graph.getVertexList()) {
            visited.put(airport, false);
        }
        Queue<Airport> fifo= new LinkedList<>();
        fifo.add(source);
        while (!fifo.isEmpty()) {
            Airport airport= fifo.remove();
            //Get aircraft that are in this airport and sort them by range.

            //TODO sort airport adjacentVertex by distance.
            for ( AirportWeighted next : graph.getAdjacentVertex(airport)) {
                if (visited.get(next.airport) != true) {
                    System.out.println("flight:"+airport.toString()+ "->" + next.airport.toString());
                    //Visit the next airports
                    visited.replace(next.airport, true);
                    fifo.add(next.airport);
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
                graph.addEdge(departure, arrival, r.distance,r.id);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return graph;
    }
}
