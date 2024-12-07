package dao;


import dao.interfaces.ParkingDaoI;
import db.PreparedStatementQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.Employee;
import model.Flight;
import model.FlightRoute;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Vector;

public class FlightDaoPg implements dao.interfaces.FlightDaoI {
    @Override
    public Vector<Flight> getAll() {
       Vector<Flight> flights = new Vector<>();

        PgDB db = new PgDB();
        Vector<Vector<String>> res = db.runAndFetch(PreparedStatementQueries.getFlights);
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

        ArrayList<String> params = new ArrayList<>();
        params.add(id);

        var result = db.runPstmtAndFetch(PreparedStatementQueries.getCommandersForFlightID, params);
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

        ArrayList<String> params = new ArrayList<>();
        params.add(id);

        var result = db.runPstmtAndFetch(PreparedStatementQueries.getFirstOfficersForFlightID, params);
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

        ArrayList<String> params = new ArrayList<>();
        params.add(id);

        var result = db.runPstmtAndFetch(PreparedStatementQueries.getFlightAssistantsForFlightID, params);
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

        ArrayList<String> params = new ArrayList<>();
        params.add(flight.departureTime);
        params.add(String.valueOf(flight.passengersNumber));

        params.add(String.valueOf(flight.route.id));
        params.add(flight.aircraftPlate);

        db.runPstmtAndFetch(PreparedStatementQueries.commitFlight, params);
        db.commit();

        var result = db.runAndFetch(PreparedStatementQueries.getLastFlightId);
        db.close();

        return Integer.parseInt(result.get(0).get(0));
    }
}
