import java.util.Vector;

import dao.AircraftDaoPg;
import dao.interfaces.AircraftDaoI;
import model.Aircraft;

public class FlightManager {
    private AircraftDaoI aircraftDao= new AircraftDaoPg();
    public Vector<Aircraft> aircrafts = aircraftDao.getAllFromQuery();

    public Vector<Airport> airports = Airport.getAllFromQuery();
    public Vector<Employee> commanders = new Vector<>();

    public Vector<Employee> firstOfficers = new Vector<>();
    public Vector<Employee> flightAssistants = new Vector<>();

    FlightManager(AircraftDaoI aircraftDao) {

        makeEmployeesVectors();
    }


    private void makeEmployeesVectors() {
        Vector<Employee> employees = Employee.getAllFromQuery();

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
