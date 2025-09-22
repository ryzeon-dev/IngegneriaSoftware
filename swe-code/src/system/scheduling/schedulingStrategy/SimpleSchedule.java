package system.scheduling.schedulingStrategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

import dao.*;
import dao.interfaces.*;
import model.Aircraft;
import model.Airport;
import model.Employee;
import model.Flight;
import model.FlightRoute;
import system.scheduling.AirportGraph;
import system.scheduling.AirportWeighted;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SimpleSchedule  implements SchedulingStrategy {
    private static final int MinutesPerHour = 60;
    private static final int LONG_FLIGHT_TIME = 9;
    //Constraints
    private static final double RangeTollerance = 0.15;
    private final int turnArountAmountMin=30;

    private FlightRouteDaoI flightRouteDao = new FlightRouteDaoPg();
    private ParkingDaoI parkingDao = new ParkingDaoPg();
    private AirportDaoI airportDao = new AirportDaoPg();

    private final int iterations=2;

    private Vector<Flight> flights= new Vector<>();
    private AirportGraph graph=null;
    private Map<Airport, Boolean> visited= new HashMap<>();
    private Map<Airport,Vector<Aircraft>> aircraftLocation=new HashMap<>();
    private Map<Aircraft,LocalDateTime> aircraftTime=new HashMap<>();

    private LocalDateTime StartTime=LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    //Employee
    private EmployeeDaoI employeeDao = new EmployeeDaoPg();
    private Queue<Employee> commanders = new LinkedList<>();
    private Queue<Employee> firstOfficers = new LinkedList<>();
    private Queue<Employee> flightAssistants = new LinkedList<>();
    private Map<Airport,LinkedList<Employee>> commandersLocation=new HashMap<>();
    private Map<Airport,LinkedList<Employee>> firstOfficersLocation=new HashMap<>();
    private Map<Airport,LinkedList<Employee>> flightAssistantsLocation=new HashMap<>();

    public SimpleSchedule(){
        makeEmployeesQueues();
    }

    private void makeEmployeesQueues() {
        employeeDao.getAllCommanders().forEach(commander -> this.commanders.add(commander));
        employeeDao.getAllFirstOfficers().forEach(firstOfficer -> this.firstOfficers.add(firstOfficer));
        employeeDao.getAllFlightAssistants().forEach(flightAssistant -> this.flightAssistants.add(flightAssistant));
    }
    
    @Override
    public Vector<Flight> run() {
        graph=buildGraphFromFlightRoute();
        //Keep track of where aircraft are during bfs
        //Run bfs algorithm
        for (Airport airport : graph.getVertexList()) {
            graph.getAdjacentVertex(airport).sort(new Comparator<AirportWeighted>() {
                @Override
                public int compare(AirportWeighted airport0, AirportWeighted airport1) {
                    return airport1.weight - airport0.weight;
                }
            });

            Vector<Aircraft> parkedAircrafts=parkingDao.getParked(airport.icao);
            for (Aircraft a : parkedAircrafts) {
                aircraftTime.put(a,StartTime);
            }

            aircraftLocation.put(airport, parkedAircrafts);
        }
        
        for (int iteration = 0; iteration < iterations; iteration++) {
            exploreBfs();
        }
        
        return this.flights;
    }

    private void exploreBfs(){
        for (Airport airport : graph.getVertexList()) {
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
            visited.replace(airport, true);

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
            
            while (!parkedAircraftsPq.isEmpty()) {
                for (var airportWeighted : graph.getAdjacentVertex(airport)) {
                    if(!parkedAircraftsPq.isEmpty()) {
                        var nextAircraft = parkedAircraftsPq.remove();
                        if (visited.get(airportWeighted.airport) != true && canFly(nextAircraft, airportWeighted)) {
                            aircraftLocation.get(airport).remove(nextAircraft);

                            if (aircraftLocation.containsKey(airportWeighted.airport)) {
                                aircraftLocation.get(airportWeighted.airport).add(nextAircraft);
                            } else {
                                Vector<Aircraft> aircrafts = new Vector<>();
                                aircrafts.add(nextAircraft);
                                aircraftLocation.put(airportWeighted.airport, aircrafts);
                            }

                            //Departure
                            LocalDateTime departure = aircraftTime.get(nextAircraft).plusMinutes(turnArountAmountMin);

                            //New AircraftTime
                            LocalDateTime eta = departure.plusMinutes(airportWeighted.duration);
                            aircraftTime.replace(nextAircraft, eta);

                            //assign personal to flight
                            //chose flightAssistants

                            var currentFlightAssistants = getFlightAssistants(nextAircraft.assistantsNumber, airport, airportWeighted.airport);

                            int neededCommanders = 1;

                            if (isLongFlight(airportWeighted)) {
                                neededCommanders = 2;
                            }

                            var currentCommanders = getCommanders(neededCommanders, airport, airportWeighted.airport, nextAircraft.model);
                            var currentFirstOfficers = getFirstOfficers(neededCommanders, airport, airportWeighted.airport, nextAircraft.model);

                            var flight = new Flight(
                                    0,
                                    departure.format(dateTimeFormat),
                                    0,
                                    airportWeighted.route,
                                    nextAircraft.plate,
                                    currentCommanders,
                                    currentFirstOfficers,
                                    currentFlightAssistants
                            );

                            if (isFlightValid(flight, nextAircraft, neededCommanders)) {
                                flight.commitFlight();
                                flights.add(flight);

                            }

                            //Continue to the next airport
                            fifo.add(airportWeighted.airport);
                        }                            //

                    }
                }
            }
        }
    }

    //Check if flight is longer than 7 hours.
    private boolean isLongFlight(AirportWeighted airportWeighted) {
        return (airportWeighted.duration / MinutesPerHour) >= LONG_FLIGHT_TIME;
    }
    

    private Vector<Employee> getCommanders(int neededCommanders, Airport source, Airport destination,String aircraftModel) {
        Vector<Employee> currentCommanders= new Vector<>();
        //Check if the Commander has the correct aircraft abilitation.
        for (int i = 0; i < neededCommanders; i++) {
            Employee selectedCommander=null;
            if(commandersLocation.containsKey(source) && !commandersLocation.get(source).isEmpty()){
                for (Employee commander : commandersLocation.get(source)) {
                    if(isAbilitationValid(commander.abilitation, aircraftModel)){
                        selectedCommander=commander;
                        commandersLocation.get(source).remove(selectedCommander);
                        currentCommanders.add(selectedCommander);
                        break;
                    }
                }
            }
            if(selectedCommander == null){
                for (Employee commander : commanders) {
                    if(isAbilitationValid(commander.abilitation, aircraftModel)){
                        selectedCommander=commander;
                        commanders.remove(selectedCommander);
                        currentCommanders.add(selectedCommander);
                        break;
                        }
                }
            }

            if(selectedCommander != null){
                commandersLocation.putIfAbsent(destination,new LinkedList<>());
                commandersLocation.get(destination).add(selectedCommander);
            }

        }
        return currentCommanders;
    }

    private Vector<Employee> getFirstOfficers(int neededFirstOfficers, Airport source, Airport destination,String aircraftModel) {
        Vector<Employee> currentFirstOfficers= new Vector<>();
        //Check if the firstOfficer has the correct aircraft abilitation.
        for (int i = 0; i < neededFirstOfficers; i++) {
            Employee selectedFirstOfficers=null;
            if(firstOfficersLocation.containsKey(source) && !firstOfficersLocation.get(source).isEmpty()){
                for (Employee firstOfficer : firstOfficersLocation.get(source)) {
                    if(isAbilitationValid(firstOfficer.abilitation, aircraftModel)){
                        selectedFirstOfficers=firstOfficer;
                        firstOfficersLocation.get(source).remove(selectedFirstOfficers);
                        currentFirstOfficers.add(selectedFirstOfficers);
                        break;
                    }
                }
            }

            if(selectedFirstOfficers == null){
                for (Employee firstOfficer : firstOfficers) {
                    if(isAbilitationValid(firstOfficer.abilitation, aircraftModel)){
                        selectedFirstOfficers=firstOfficer;
                        firstOfficers.remove(selectedFirstOfficers);
                        currentFirstOfficers.add(selectedFirstOfficers);
                        break;
                        }
                }
            }

            if(selectedFirstOfficers != null){
                firstOfficersLocation.putIfAbsent(destination,new LinkedList<>());
                firstOfficersLocation.get(destination).add(selectedFirstOfficers);
            }
            
        }
        return currentFirstOfficers;
    }

    private boolean isFlightValid(Flight f,Aircraft a,int neededCommanders){
        if(f.commanders.size() < neededCommanders || f.firstOfficers.size() < neededCommanders){
            return false;
        }

        if(f.flightAssistants.size() < a.assistantsNumber){
            return false;
        }

        return true;
    }

    private AirportGraph buildGraphFromFlightRoute(){
        AirportGraph graph= new AirportGraph();
        Vector<FlightRoute> routes= flightRouteDao.getAll();

        Vector<Airport> airports = airportDao.getAll();
        Map<String,Airport> airportDict=new HashMap<>();

        for (var airport : airports) {
            airportDict.put(airport.icao, airport);
        }

        for (FlightRoute r : routes) {
            try {
                Airport departure=airportDict.get(r.departure);
                Airport arrival=airportDict.get(r.arrival);
                graph.addEdge(departure, arrival, r.distance,r.id,r.duration);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return graph;
    }
    //Check if aircraft can fly to given airport.
    private boolean canFly(Aircraft aircraft,AirportWeighted airportWeighted){
        //Check if the aircraft class is compatible with the airport class.
        if (!aircraft.canGo(airportWeighted.airport)){
            return false;
        }

        if(aircraft.range < airportWeighted.weight || (aircraft.range-airportWeighted.weight) < airportWeighted.weight*RangeTollerance){
            return false;
        }

        return true;
    }

    private Vector<Employee> getFlightAssistants(int assistantNumber,Airport source,Airport destination){
        Vector<Employee> currentFlightAssistants= new Vector<>();

        for (int i = 0; i < assistantNumber; i++) {
            Employee flightAssistant=null;

            //check if there are available flight assistant in the airport
            if(flightAssistantsLocation.containsKey(source) && !flightAssistantsLocation.get(source).isEmpty()){
                flightAssistant=flightAssistantsLocation.get(source).remove();
                currentFlightAssistants.add(flightAssistant);                
            }else{
                try {
                    flightAssistant=flightAssistants.remove();
                    currentFlightAssistants.add(flightAssistant);
                } catch (Exception e) {
    
                }
            }

            if(flightAssistant != null){
                flightAssistantsLocation.putIfAbsent(destination,new LinkedList<>());
                flightAssistantsLocation.get(destination).add(flightAssistant);
            }

        }
        return currentFlightAssistants;
    }

    Boolean isAbilitationValid(String abilitation,String aircraftModel){
        if(abilitation.equals(aircraftModel)){
            return true;
        }

        if(abilitation.equals("A319")){
            return aircraftModel.equals("A318") || aircraftModel.equals("A320") ||  aircraftModel.equals("A321");
        }

        return false;
    }

}

