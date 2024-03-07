import java.util.Vector;

import cli.CLI;
import dao.AircraftDaoPg;
import dao.AirportDaoPg;
import dao.EmployeeDaoPg;
import dao.interfaces.AirportDaoI;
import system.ManagementSystem;
import system.FlightManager;

public class Main {
    public static void main(String[] args) {
        AircraftDaoPg aircraftDao= new AircraftDaoPg();

        EmployeeDaoPg employeeDao = new EmployeeDaoPg();
        AirportDaoPg airportDaoPg = new AirportDaoPg();

        FlightManager manager = new FlightManager(aircraftDao, employeeDao, airportDaoPg);
        System.out.println(manager);

        ManagementSystem managementSystem = new ManagementSystem(manager);
        CLI cli = new CLI(managementSystem);
        cli.run();
    }
}