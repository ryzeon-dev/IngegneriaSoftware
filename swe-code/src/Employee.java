import java.util.Vector;

public class Employee {
    public final int id;
    public final String name;
    public final String lastName;
    public final EmployeeRole role;
    public final String dimensionClass;
    public String position = "";

    Employee(Vector<String> row) {
        this.id = Integer.parseInt(row.get(0));
        this.name = row.get(1);

        this.lastName = row.get(2);
        String role = row.get(3);

        switch (role) {
            case "commander":
                this.role = EmployeeRole.Commander;
                break;

            case "firstofficer":
                this.role = EmployeeRole.FirstOfficer;
                break;

            case "hostess/steward":
                this.role = EmployeeRole.FlightAssistant;
                break;

            default:
                this.role = EmployeeRole.FlightAssistant;
                break;
        }

        this.dimensionClass = row.get(4);
    }

    public static Vector<Employee> getAllFromQuery() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getCompanyEmployees);
        Vector<Employee> employees = new Vector<>();

        for (var row : result) {
            employees.add(new Employee(row));
        }

        return employees;
    }

    public void setPosition(String icao) {
        this.position = icao;
    }

    @Override
    public String toString() {
        return this.role + " {" + this.name + " " + this.lastName + "}";
    }
}
