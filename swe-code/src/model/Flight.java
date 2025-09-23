package model;

import dao.FlightDaoPg;

import java.util.Vector;

public class Flight {
    public int id;
    public final String departureTime;

    public final int passengersNumber;
    public final FlightRoute route;

    public final String aircraftPlate;

    public final Vector<Employee> commanders;
    public final Vector<Employee> firstOfficers;
    public final Vector<Employee> flightAssistants;

    public Vector<Integer> employeeIds = new Vector<>();
    
    public Flight(int id, String departureTime, int passengersNumber, FlightRoute route,
                  String aircraftPlate, Vector<Employee> commanders, Vector<Employee> firstOfficers,
                  Vector<Employee> flightAssistants){
        this.id = id;
        this.departureTime = departureTime;

        this.passengersNumber = passengersNumber;
        this.route = route;

        this.aircraftPlate = aircraftPlate;
        this.commanders = commanders;

        this.firstOfficers = firstOfficers;
        this.flightAssistants = flightAssistants;

        for (var commander : this.commanders) {
            this.employeeIds.add(commander.id);
        }

        for (var firstOfficer : this.firstOfficers) {
            this.employeeIds.add(firstOfficer.id);
        }

        for (var flightAssistant : this.flightAssistants) {
            this.employeeIds.add(flightAssistant.id);
        }
    }

    public String toString() {
        String repr = "Flight " + this.id + "\n";
        repr += "\tAircraft: " + this.aircraftPlate + "\n";

        repr += "\tDeparture Time: " + this.departureTime + "\n";
        repr += "\tPassengers Number: " + this.passengersNumber + "\n";

        repr += "\tRoute:" + "\n";
        repr += "\t\tDeparture: " + this.route.departure + "\n";

        if (this.route.stepover != null) {
            repr += "\t\tStepover: " + this.route.stepover + "\n";
        }

        repr += "\t\tArrival: " + this.route.arrival + "\n";
        repr += "\t\tDistance: " + this.route.distance + " km\n";
        repr += "\t\tDuration: " + this.route.duration + " minutes\n";

        repr += "\tCommander(s):\n";
        for (var commander : this.commanders) {
            repr += "\t\t" + commander.name + " " + commander.lastName + "\n";
        }

        repr += "\tFirst Officer(s):\n";
        for (var firstOfficer : this.firstOfficers) {
            repr += "\t\t" + firstOfficer.name + " " + firstOfficer.lastName + "\n";
        }

        repr += "\tFlight Assistant(s):\n";
        for (var flightAssistant : this.flightAssistants) {
            repr += "\t\t" + flightAssistant.name + " " + flightAssistant.lastName + "\n";
        }

        return repr + "\n";
    }

    public void commitFlight() {
        FlightDaoPg flightDaoPg = new FlightDaoPg();
        this.id = flightDaoPg.commitFlight(this);
    }
}
