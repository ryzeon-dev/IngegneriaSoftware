import java.util.Vector;

import cli.CLI;
import dao.AircraftDaoPg;
import dao.AirportDaoPg;
import dao.EmployeeDaoPg;
import dao.FlightRouteDaoPg;
import dao.interfaces.AirportDaoI;
import model.FlightRoute;
import system.ManagementSystem;
import system.scheduling.schedulingStrategy.SimpleSchedule;
import system.FlightManager;
import system.FlightSchedule;

public class Main {
    public static void main(String[] args) {
        AircraftDaoPg aircraftDao= new AircraftDaoPg();

        EmployeeDaoPg employeeDao = new EmployeeDaoPg();
        AirportDaoPg airportDaoPg = new AirportDaoPg();
        FlightRouteDaoPg flightRoutePg= new FlightRouteDaoPg();
        FlightManager manager = new FlightManager(aircraftDao, employeeDao, airportDaoPg);
        //System.out.println(manager);
        SimpleSchedule schedulingStrategy=new SimpleSchedule(flightRoutePg,airportDaoPg);
        FlightSchedule flightSchedule=new FlightSchedule(manager,schedulingStrategy);
        ManagementSystem managementSystem = new ManagementSystem(manager,flightSchedule);
        CLI cli = new CLI(managementSystem);
        cli.run();
    }
}