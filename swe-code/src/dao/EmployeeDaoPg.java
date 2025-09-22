package dao;

import dao.interfaces.EmployeeDaoI;
import db.ConstantQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.Employee;
import model.EmployeeRole;
import org.checkerframework.checker.units.qual.A;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.Vector;

public class EmployeeDaoPg implements dao.interfaces.EmployeeDaoI {
    private Vector<Employee> getAllFromQuery(String query) {
        PgDB db = new PgDB();
        Vector<Employee> employees = new Vector<>();

        var result = db.runAndFetch(query);
        result.forEach(row -> employees.add(this.buildFromRow(row)));

        return employees;
    }

    @Override
    public Vector<Employee> getAll() {
        return getAllFromQuery(PreparedStatementQueries.getCompanyEmployees);
    }

    @Override
    public Vector<Employee> getAllCommanders() {
        return this.getAllFromQuery(PreparedStatementQueries.getAllCommanders);
    }

    @Override
    public Vector<Employee> getAllFirstOfficers() {
        return this.getAllFromQuery(PreparedStatementQueries.getAllFirstOfficers);
    }

    @Override
    public Vector<Employee> getAllFlightAssistants() {
        return this.getAllFromQuery(PreparedStatementQueries.getAllFlightAssistants);
    }

    @Override
    public Employee getEmployeeById(String id) {
        Employee employee;
        PgDB db = new PgDB();

        ResultSet result;

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.getEmployeeInfo);
            preparedStatement.setInt(1, Integer.parseInt(id));

            result = preparedStatement.executeQuery();
            if (result.getFetchSize() == 0) {
                db.close();
                return null;
            }

            Vector<String> vectorRow = new Vector<>();

            for (int i = 0; i < 4; i++) {
                vectorRow.add(result.getRowId(i).toString());
            }

            employee = buildFromRow(vectorRow);
        } catch (SQLException e) {
            db.close();
            return null;
        }

        db.close();
        return employee;
    }

    private Employee buildFromRow(Vector<String> row) {
        int id = Integer.parseInt(row.get(0));
        String name = row.get(1);

        String lastName = row.get(2);
        String strRole = row.get(3);

        EmployeeRole role;
        switch (strRole) {
            case "commander":
                role = EmployeeRole.Commander;
                break;

            case "firstofficer":
                role = EmployeeRole.FirstOfficer;
                break;

            case "hostess/steward":
                role = EmployeeRole.FlightAssistant;
                break;

            default:
                role = EmployeeRole.FlightAssistant;
                break;
        }

        String abilitation = row.get(4);

        return new Employee(id, name, lastName, role, abilitation);
    }

    @Override
    public void create(String name, String lastName, String role, String abilitation) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.insertPersonal);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);

            preparedStatement.setString(3, role);
            if(abilitation.isBlank()){
                preparedStatement.setString(4, null);
            }else{
                preparedStatement.setString(4, abilitation);
            }

            preparedStatement.execute();
            db.commit();

        } catch (SQLException e) {
            db.close();
            throw new RuntimeException(e);
        }

        db.close();
    }

    @Override
    public void delete(int id) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.deletePersonal);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            db.commit();

        } catch (SQLException e) {
            db.close();
            throw new RuntimeException(e);
        }

        db.close();
    }
}
