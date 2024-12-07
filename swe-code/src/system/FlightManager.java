package system;

import java.util.Vector;

import dao.interfaces.AircraftDaoI;
import dao.interfaces.AirportDaoI;
import dao.interfaces.EmployeeDaoI;
import model.Aircraft;
import model.Airport;
import model.Employee;

public class FlightManager {
    private AircraftDaoI aircraftDao;
    private Vector<Aircraft> aircrafts;

    private EmployeeDaoI employeeDao;
    private Vector<Employee> employees;
    private Vector<Employee> commanders = new Vector<>();
    private Vector<Employee> firstOfficers = new Vector<>();
    private Vector<Employee> flightAssistants = new Vector<>();

    private AirportDaoI airportDao;
    private Vector<Airport> airports = new Vector<>();


    public FlightManager(AircraftDaoI aircraftDao, EmployeeDaoI employeeDao, AirportDaoI airportDao) {
        this.aircraftDao = aircraftDao;
        this.employeeDao = employeeDao;
        this.airportDao = airportDao;

        aircrafts = this.aircraftDao.getAll();
        makeEmployeesVectors();
        airports = this.airportDao.getAll();
    }

    private void makeEmployeesVectors() {

        this.employees = employeeDao.getAll();

        for (Employee employee : employees) {
            switch (employee.role) {
                case Commander:
                    commanders.add(employee);
                    break;

                case FirstOfficer:
                    firstOfficers.add(employee);
                    break;

                case FlightAssistant:
                    flightAssistants.add(employee);
                    break;
            }
        }
    }

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

    public Vector<Employee> getCommanders() {
        return this.commanders;
    }

    public Vector<Employee> getFirstOfficiers() {
        return this.firstOfficers;
    }

    public Vector<Employee> getFlightAssistants() {
        return this.flightAssistants;
    }

    public Vector<Employee> getAllEmployees() {
        return this.employees;
    }

    public Employee getEmployeeById(int id) {
        for (var employee : this.employees) {
            if (employee.id == id) {
                return employee;
            }
        }

        return null;
    }

    public Vector<Airport> getAirports(){
        return this.airports;
    }

    public Vector<Aircraft> getAircrafts(){
        return this.aircrafts;
    }

    public int employeesNumber() {
        return this.employees.size();
    }

    public void removeAircraft(String plate) {
        aircraftDao.deleteInstance(plate);
    }
}
