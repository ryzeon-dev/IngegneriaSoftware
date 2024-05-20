package model;

import java.util.Vector;

public class Flight {
    public final int id;
    public final String departureTime;

    public final int passengersNumber;
    public final int route;

    public final String aircraftPlate;
    public final Vector<Employee> commanders;
    public final Vector<Employee> firstOfficers;
    public final Vector<Employee> flightAssistants;
    
    public Flight(int id, String departureTime, int passengersNumber, int route,
                  String aircraftPlate, Vector<Employee> commanders, Vector<Employee> firstOfficers, Vector<Employee> flightAssistants){
        this.id = id;
        this.departureTime = departureTime;

        this.passengersNumber = passengersNumber;
        this.route = route;

        this.aircraftPlate = aircraftPlate;
        this.commanders = commanders;

        this.firstOfficers = firstOfficers;
        this.flightAssistants = flightAssistants;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
