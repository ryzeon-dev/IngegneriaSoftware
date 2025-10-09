package dao.interfaces;

import java.util.Vector;
import model.Employee;

public interface EmployeeDaoI {
    public Vector<Employee> getAll();
    public Vector<Employee> getAllCommanders();
    public Vector<Employee> getAllFirstOfficers();
    public Vector<Employee> getAllFlightAssistants();
    public Employee getEmployeeById(int id);

    public void create(String name, String lastName, String role, String abilitation);
    public void delete(int id);
}
