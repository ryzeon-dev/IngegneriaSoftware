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
    public Vector<Employee> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(PreparedStatementQueries.getCompanyEmployees);
        Vector<Employee> employees = new Vector<>();

        for (var row : result) {
            employees.add(this.buildFromRow(row));
        }

        return employees;
    }

    public Employee getEmployeeById(String id) {
        Employee employee;
        PgDB db = new PgDB();

        ResultSet result;

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.getEmployeeInfo);
            preparedStatement.setInt(1, Integer.parseInt(id));

            result = preparedStatement.executeQuery();

            Vector<String> vectorRow = new Vector<>();

            for (int i = 0; i < 4; i++) {
                vectorRow.add(result.getRowId(i).toString());
            }

            employee = buildFromRow(vectorRow);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        db.close();
        return employee;
    }

    public Employee buildFromRow(Vector<String> row) {
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
            throw new RuntimeException(e);
        }

        db.close();
    }
}
