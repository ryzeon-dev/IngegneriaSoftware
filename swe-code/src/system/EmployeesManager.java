package system;

import dao.EmployeeDaoPg;
import model.Employee;
import model.EmployeeRole;

import java.util.Vector;

public class EmployeesManager {
    private EmployeeDaoPg employeeDao = new EmployeeDaoPg();
    private Vector<Employee> employees;

    private Vector<Employee> commanders;
    private Vector<Employee> firstofficers;
    private Vector<Employee> flightAssistants;

    public EmployeesManager() {
        employees = employeeDao.getAll();

        for (var employee : this.employees) {
            switch (employee.role) {
                case EmployeeRole.Commander:
                    commanders.add(employee);
                    break;

                case EmployeeRole.FirstOfficer:
                    firstofficers.add(employee);
                    break;

                case EmployeeRole.FlightAssistant:
                    flightAssistants.add(employee);
                    break;
            }
        }
    }

    public Employee getEmployeeById(int id) {
        for (Employee employee : this.employees) {
            if (employee.id == id) {
                return employee;
            }
        }

        return null;
    }

    public Vector<Employee> getCommanders() {
        return this.commanders;
    }

    public Vector<Employee> getFirstOfficiers() {
        return this.firstofficers;
    }

    public Vector<Employee> getFlightAssistants() {
        return this.flightAssistants;
    }
}
