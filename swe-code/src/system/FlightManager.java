package system;

import java.util.Vector;

import dao.FlightRouteDaoPg;
import dao.interfaces.FlightRouteDaoI;
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

    private FlightRouteDaoI flightRouteDao;

    public FlightManager(AircraftDaoI aircraftDao, EmployeeDaoI employeeDao, AirportDaoI airportDao, FlightRouteDaoI flightRouteDao) {
        this.aircraftDao = aircraftDao;
        this.employeeDao = employeeDao;
        this.airportDao = airportDao;
        this.flightRouteDao = flightRouteDao;

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

    public boolean addAirport(String ICAO, String dimensionClass, String name, String nation, String city) {
        try {
            airportDao.create(ICAO, dimensionClass, name, nation, city);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean updateAirport(String ICAO, String dimensionClass) {
        try {
            airportDao.update(ICAO, dimensionClass);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean removeAirport(String ICAO) {
        try {
            airportDao.delete(ICAO);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean createRoute(int distance, int duration, String departure, String stepover, String arrival) {
        try{
            this.flightRouteDao.create(distance, duration, departure, arrival);

        } catch (RuntimeException e) {
            return false;
        }

        return true;
    }

    public boolean deleteRoute(int id) {
        try {
            this.flightRouteDao.delete(String.valueOf(id));

        } catch (RuntimeException e) {
            return false;
        }

        return true;
    }
}
