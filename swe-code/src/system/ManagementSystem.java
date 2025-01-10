package system;

import dao.AircraftDaoPg;
import dao.FlightRouteDaoPg;
import model.Aircraft;
import model.Flight;
import model.FlightRoute;

import java.util.Vector;

public class ManagementSystem extends Thread {
    private SimulatedClock clock = new SimulatedClock();
    public FlightManager flightManager;
    public AircraftManager aircraftManager;
    public FlightSchedule flightSchedule;
    public Vector<Flight> scheduledFlights;

    private FlightRouteDaoPg flightRouteDao = new FlightRouteDaoPg();
    private Vector<FlightRoute> flightRoutes;

    public ManagementSystem(FlightManager manager,AircraftManager aircraftManager ,FlightSchedule fSchedule) {
        this.flightManager = manager;
        this.aircraftManager = aircraftManager;
        this.flightSchedule = fSchedule;

        this.flightRoutes = flightRouteDao.getAll();
        clock.start();
    }

    public void quit() {
        this.clock.run = false;
        this.clock.interrupt();

        try {
            this.clock.join();

        } catch (InterruptedException e) {
        }
    }

    public String getTime() {
        return clock.getTime();
    }

    public Vector<Aircraft> getAircraftDetails() {
        return this.flightManager.getAircrafts();
    }

    public String getRouteDetails() {
        Vector<String> res = new Vector<>();

        for (var route : this.flightRoutes) {
            String departureCity = "";
            String departureCountry = "";

            String arrivalCity = "";
            String arrivalCountry = "";

            for (var airport : this.flightManager.getAirports()) {
                if (airport.icao.equals(route.departure)) {
                    departureCity = airport.city;
                    departureCountry = airport.nation;

                } else if (airport.icao.equals(route.arrival)) {
                    arrivalCity = airport.city;
                    arrivalCountry = airport.nation;
                }
            }

            String routeLine = "From: " + route.departure + " (" + departureCity + ", " + departureCountry +  ")\n" +
                    "To: " + route.arrival + " (" + arrivalCity + ", " + arrivalCountry +  ")\n" +
                    "Distance: " + route.distance + " km\n" +
                    "Duration: " + route.duration + " minutes";
            res.add(routeLine);
        }

        return String.join("\n\n", res);
    }

    public void runScheduling(){
        this.scheduledFlights = flightSchedule.makeSchedule();
    }

    public void deleteAircraft(String plate) {
        this.flightManager.removeAircraft(plate);
    }

}
