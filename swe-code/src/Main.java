import cli.CLI;
import dao.AircraftDaoPg;
import dao.AirportDaoPg;
import dao.EmployeeDaoPg;
import dao.FlightRouteDaoPg;
import dao.ParkingDaoPg;
import system.ManagementSystem;
import system.scheduling.schedulingStrategy.SimpleSchedule;
import system.AircraftManager;
import system.FlightManager;
import system.FlightSchedule;

public class Main {
    public static void main(String[] args) {
        //DAO Instantiation.
        AircraftDaoPg aircraftDao= new AircraftDaoPg();
        EmployeeDaoPg employeeDao = new EmployeeDaoPg();
        AirportDaoPg airportDaoPg = new AirportDaoPg();
        FlightRouteDaoPg flightRoutePg= new FlightRouteDaoPg();
        ParkingDaoPg parkingDaoPg= new ParkingDaoPg();
        FlightManager flightManager = new FlightManager(aircraftDao, employeeDao, airportDaoPg);
        AircraftManager aircraftManager = new AircraftManager(aircraftDao);
        //System.out.println(manager);
        SimpleSchedule schedulingStrategy=new SimpleSchedule(flightRoutePg,parkingDaoPg ,flightManager);
        FlightSchedule flightSchedule=new FlightSchedule(schedulingStrategy);
        ManagementSystem managementSystem = new ManagementSystem(flightManager,aircraftManager,flightSchedule);
        CLI cli = new CLI(managementSystem);
        cli.run();
    }
}