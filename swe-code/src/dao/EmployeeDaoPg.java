package dao;

import dao.interfaces.EmployeeDaoI;
import db.ConstantQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.Employee;
import model.EmployeeRole;
import org.checkerframework.checker.units.qual.A;

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

        ArrayList<String> params = new ArrayList<>();
        params.add(id);

        PgDB db = new PgDB();
        var result = db.runPstmtAndFetch(PreparedStatementQueries.getEmployeeInfo, params);

        employee = buildFromRow(result.get(0));
        db.close();

        return employee;
    }

    public Employee buildFromRow(Vector<String> row) {
        Employee employee;
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
        PgDB pgDB = new PgDB();
        ArrayList<String> params = new ArrayList<>();

        params.add(name);
        params.add(lastName);

        params.add(role);
        params.add(abilitation);

        pgDB.runPstmtAndFetch(PreparedStatementQueries.insertPersonal, params);

        pgDB.commit();
        pgDB.close();
    }

    @Override
    public void delete(String id) {
        PgDB pgDB = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(id);

        pgDB.runPstmtAndFetch(PreparedStatementQueries.deletePersonal, params);

        pgDB.commit();
        pgDB.close();
    }
}
