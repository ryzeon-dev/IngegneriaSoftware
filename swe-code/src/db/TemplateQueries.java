package db;

import model.Employee;

public class TemplateQueries {
    public String getEmployeeById(int id) {
        return "select * from personal where id=" + String.valueOf(id);
    }

    public String getRouteById(int id) {
        return "select * from route where id=" + String.valueOf(id);
    }
}
