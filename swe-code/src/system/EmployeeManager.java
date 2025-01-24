package system;

import dao.interfaces.EmployeeDaoI;
import model.Employee;

public class EmployeeManager {
    private EmployeeDaoI employeeDao;

    public EmployeeManager(EmployeeDaoI employeeDao){
        this.employeeDao=employeeDao;
    }

    public void insertEmployee(Employee employee){
        employeeDao.create(employee.name, employee.lastName, employee.role.toString(), employee.abilitation);
    }

    public void deleteEmployee(int id){
        employeeDao.delete(id);
    }
}
