package dao.interfaces;

import java.util.Vector;
import model.Employee;

public interface EmployeeDaoI {
    public Vector<Employee> getAll();

    public Employee getEmployeeById(String id);
    public void create(String name, String lastName, String role, String abilitation);
    public void delete(String id);
}
