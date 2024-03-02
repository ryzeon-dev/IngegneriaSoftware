import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Vector<Aircraft> aircrafts = Aircraft.getAllFromQuery();
        Vector<Employee> employees = Employee.getAllFromQuery();
        Vector<Airport> airports = Airport.getAllFromQuery();

        System.out.println(aircrafts);
        System.out.println(employees);
        System.out.println(airports);
    }
}