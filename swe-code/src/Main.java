import java.util.Vector;

import dao.AircraftDaoPg;
import dao.AirportDaoPg;
import dao.EmployeeDaoPg;
import dao.interfaces.AirportDaoI;
import system.ManagementSystem;

public class Main {
    public static void main(String[] args) {
        /*AircraftDaoPg aircraftDao= new AircraftDaoPg();

        EmployeeDaoPg employeeDao = new EmployeeDaoPg();
        AirportDaoPg airportDaoPg = new AirportDaoPg();

        system.FlightManager manager = new system.FlightManager(aircraftDao, employeeDao, airportDaoPg);
        System.out.println(manager);*/

        ManagementSystem managementSystem = new ManagementSystem();
    }
}