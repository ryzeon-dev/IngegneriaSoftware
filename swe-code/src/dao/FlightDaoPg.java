package dao;


import db.PreparedStatementQueries;
import db.PgDB;
import model.Employee;
import model.Flight;
import model.FlightRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FlightDaoPg implements dao.interfaces.FlightDaoI {
    @Override
    public Vector<Flight> getAll() {
       Vector<Flight> flights = new Vector<>();

        PgDB db = new PgDB();
        try {
            var res = db.makePreparedStatement(PreparedStatementQueries.getFlights).executeQuery();
            while (res.next()) {
                flights.add(buildFromRow(res));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            db.close();
        }

        return flights;
    }

    private Flight buildFromRow(ResultSet row) {
        int id;
        String departureTime;

        int passengerNumber;
        FlightRoute route;

        String aircraft;
        Vector<Employee> commanders;

        Vector<Employee> firstOfficers;
        Vector<Employee> flightAssistants;

        try {
            int intId=row.getInt(1);
            id = row.getInt(1);
            departureTime = row.getString(2);

            passengerNumber = row.getInt(3);
            FlightRouteDaoPg routeDao = new FlightRouteDaoPg();
            route = routeDao.getRouteById(row.getInt(3));

            aircraft = row.getString(4);
            commanders = getCommanders(id);

            firstOfficers = getFirstOfficers(id);
            flightAssistants = getFlightAssistants(id);

            return new Flight(intId, departureTime, passengerNumber, route, aircraft, commanders, firstOfficers, flightAssistants);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private Vector<Employee> getCommanders(int id) {
        PgDB db = new PgDB();

        var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.getCommandersForFlightID);
        Vector<Employee> commanders = new Vector<>();

        try {
            preparedStatement.setInt(1, id);
            var res=preparedStatement.executeQuery();

            EmployeeDaoPg employeeDao = new EmployeeDaoPg();
            while (res.next()) {
                commanders.add(
                    employeeDao.getEmployeeById(res.getInt(1))
                );
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally{
            db.close();
        }


        return commanders;
    }

    private Vector<Employee> getFirstOfficers(int id) {
        PgDB db = new PgDB();
        var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.getFirstOfficersForFlightID);

        Vector<Employee> firstOfficers = new Vector<>();
        EmployeeDaoPg employeeDao = new EmployeeDaoPg();

        try {
            preparedStatement.setInt(1, id);
            var res=preparedStatement.executeQuery();

            while (res.next()) {
                firstOfficers.add(
                    employeeDao.getEmployeeById(res.getInt(1))
                );
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            db.close();
        }

        return firstOfficers;
    }

    private Vector<Employee> getFlightAssistants(int id) {
        PgDB db = new PgDB();
        var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.getFlightAssistantsForFlightID);

        Vector<Employee> flightAssistants = new Vector<>();
        EmployeeDaoPg employeeDao = new EmployeeDaoPg();

        try {
            preparedStatement.setInt(1, id);
            var res=preparedStatement.executeQuery();

            while (res.next()) {
                flightAssistants.add(
                    employeeDao.getEmployeeById(res.getInt(1))
                );
            }
        } catch (SQLException e) {
            db.close();
            e.printStackTrace();

        } finally {
            db.close();
        }
        
        return flightAssistants;
    }

    public int commitFlight(Flight flight) {
        PgDB db = new PgDB();
        var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.commitFlight);

        try {
            preparedStatement.setString(1, flight.departureTime);
            preparedStatement.setInt(2, flight.passengersNumber);

            preparedStatement.setInt(3, flight.route.id);
            preparedStatement.setString(4, flight.aircraftPlate);

            preparedStatement.execute();
            db.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        var result = db.runAndFetch(PreparedStatementQueries.getLastFlightId);
        db.close();

        return Integer.parseInt(result.get(0).get(0));
    }
}
