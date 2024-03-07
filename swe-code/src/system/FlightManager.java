package system;

import java.util.Vector;

import dao.interfaces.AircraftDaoI;
import dao.interfaces.AirportDaoI;
import dao.interfaces.EmployeeDaoI;
import model.Aircraft;
import model.Airport;
import model.Employee;
import model.EmployeeRole;

public class FlightManager {
    private AircraftDaoI aircraftDao;
    public Vector<Aircraft> aircrafts;

    private EmployeeDaoI employeeDao;
    public Vector<Employee> commanders = new Vector<>();
    public Vector<Employee> firstOfficers = new Vector<>();
    public Vector<Employee> flightAssistants = new Vector<>();

    private AirportDaoI airportDao;
    public Vector<Airport> airports = new Vector<>();


    FlightManager(AircraftDaoI aircraftDao, EmployeeDaoI employeeDao, AirportDaoI airportDao) {
        this.aircraftDao = aircraftDao;
        this.employeeDao = employeeDao;
        this.airportDao = airportDao;

        aircrafts = this.aircraftDao.getAll();
        makeEmployeesVectors();
        airports = this.airportDao.getAll();
    }

    private void makeEmployeesVectors() {
        Vector<Employee> employees = employeeDao.getAll();

        for (Employee employee : employees) {
            switch (employee.role) {
                case EmployeeRole.Commander -> commanders.add(employee);
                case EmployeeRole.FirstOfficer -> firstOfficers.add(employee);
                case EmployeeRole.FlightAssistant -> flightAssistants.add(employee);
            }
        }
    }

    @Override
    public String toString() {
        String res = "{\n";
        res += "\tAircrafts: " + aircrafts + "\n\n";
        res += "\tCommanders: " + commanders + "\n";
        res += "\tFirst Officers: " + firstOfficers + "\n";
        res += "\tFlight Assistants: " + flightAssistants + "\n\n";
        res += "\tAirports: " + airports + "\n";
        res += "}";
        return res;
    }

}
