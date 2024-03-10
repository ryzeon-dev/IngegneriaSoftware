package system.scheduling.schedulingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import dao.interfaces.AirportDaoI;
import dao.interfaces.FlightRouteDaoI;
import model.Airport;
import model.Flight;
import model.FlightRoute;
import system.scheduling.AirportGraph;

public class SimpleSchedule  implements SchedulingStrategy{
    private FlightRouteDaoI flightRouteDao;
    private AirportDaoI airportDao;
    private AirportGraph graph=null;
    public SimpleSchedule(FlightRouteDaoI flightRouteDao,AirportDaoI airportDao){
        this.airportDao=airportDao;
        this.flightRouteDao=flightRouteDao;
    }
    @Override
    public Vector<Flight> run() {
        System.out.println("Running Scheduling Algorithm.\n");
        graph=buildGraphFromFlightRoute();
        System.out.println(graph);
        return null;
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
                Airport arrival=airportDict.get(r.arrival);
                Airport departure=airportDict.get(r.departure);
                graph.addEdge(departure, arrival, r.distance);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return graph;
    }
}
