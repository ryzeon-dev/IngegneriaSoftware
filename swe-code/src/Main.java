import java.util.Vector;

import dao.AircraftDaoPg;
import dao.AirportDaoPg;
import dao.EmployeeDaoPg;
import dao.interfaces.AirportDaoI;

public class Main {
    public static void main(String[] args) {
        AircraftDaoPg aircraftDao= new AircraftDaoPg();

        EmployeeDaoPg employeeDao = new EmployeeDaoPg();
        AirportDaoPg airportDaoPg = new AirportDaoPg();

        FlightManager manager = new FlightManager(aircraftDao, employeeDao, airportDaoPg);
        System.out.println(manager);
    }
}