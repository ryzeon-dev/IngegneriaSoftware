package dao;

import dao.interfaces.EmployeeDaoI;
import db.ConstantQueries;
import db.PgDB;
import model.Employee;
import model.EmployeeRole;

import java.util.Vector;

public class EmployeeDaoPg implements dao.interfaces.EmployeeDaoI {
    public Vector<Employee> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getCompanyEmployees);
        Vector<Employee> employees = new Vector<>();

        for (var row : result) {
            employees.add(this.buildFromRow(row));
        }

        return employees;
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
}
