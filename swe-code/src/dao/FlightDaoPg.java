package dao;


import db.ConstantQueries;
import db.PgDB;
import model.Employee;
import model.Flight;
import model.FlightRoute;
import org.checkerframework.checker.units.qual.C;

import java.util.Vector;

public class FlightDaoPg implements dao.interfaces.FlightDaoI {
    @Override
    public Vector<Flight> getAll() {
       Vector<Flight> flights = new Vector<>();

        PgDB db = new PgDB();
        Vector<Vector<String>> res = db.runAndFetch(ConstantQueries.getFlights);
        db.close();

        for (var row : res) {
            flights.add(buildFromRow(row));
        }

        return flights;
    }

    public Flight buildFromRow(Vector<String> row) {
        String stringID = row.get(0);
        int id = Integer.parseInt(row.get(0));
        String departureTime = row.get(1);

        int passengerNumber = Integer.parseInt(row.get(2));
        FlightRouteDaoPg routeDao = new FlightRouteDaoPg();
        FlightRoute route = routeDao.getRouteById(row.get(3));

        String aircraft = row.get(4);

        Vector<Employee> commanders = getCommanders(stringID);
        Vector<Employee> firstOfficers = getFirstOfficers(stringID);
        Vector<Employee> flightAssistants = getFlightAssistants(stringID);

        return new Flight(id, departureTime, passengerNumber, route, aircraft, commanders, firstOfficers, flightAssistants);
    }

    private Vector<Employee> getCommanders(String id) {
        PgDB db = new PgDB();

        var result = db.runAndFetch(ConstantQueries.getCommandersForFlightID(id));
        db.close();

        Vector<Employee> commanders = new Vector<>();
        EmployeeDaoPg employeeDao = new EmployeeDaoPg();

        for (var commanderId : result) {
            commanders.add(
                employeeDao.getEmployeeById(commanderId.get(0))
            );
        }

        return commanders;
    }

    private Vector<Employee> getFirstOfficers(String id) {
        PgDB db = new PgDB();

        var result = db.runAndFetch(ConstantQueries.getFirstOfficersForFlightID(id));
        db.close();

        Vector<Employee> firstOfficers = new Vector<>();
        EmployeeDaoPg employeeDao = new EmployeeDaoPg();

        for (var commanderId : result) {
            firstOfficers.add(
                employeeDao.getEmployeeById(commanderId.get(0))
            );
        }

        return firstOfficers;
    }

    private Vector<Employee> getFlightAssistants(String id) {
        PgDB db = new PgDB();

        var result = db.runAndFetch(ConstantQueries.getFlightAssistantsForFlightID(id));
        db.close();

        Vector<Employee> flightAssistants = new Vector<>();
        EmployeeDaoPg employeeDao = new EmployeeDaoPg();

        for (var commanderId : result) {
            flightAssistants.add(
                    employeeDao.getEmployeeById(commanderId.get(0))
            );
        }

        return flightAssistants;
    }

    public int commitFlight(Flight flight) {
        PgDB db = new PgDB();

        db.run(ConstantQueries.commitFlight (
            flight.departureTime, String.valueOf(flight.passengersNumber),
            String.valueOf(flight.route.id), flight.aircraftPlate
        ));
        db.commit();

        var result = db.runAndFetch(ConstantQueries.getLastFlightId);
        db.close();

        return Integer.parseInt(result.get(0).get(0));
    }
}
