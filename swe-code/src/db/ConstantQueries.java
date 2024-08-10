package db;

public class ConstantQueries {
    public static final String getCompanyAircrafts =
        "select ai.plate, a.manufacturer, a.model, a.specification, " +
        "a.range, a.assistants_number, a.class, a.seats " +
        "from aircraft_instance ai join aircraft a on " +
        "ai.aircraft = a.id";

    public static final String getCompanyEmployees =
        "select * from personal";

    public static final String getFlights =
        "select * from flight";

    public static String getCommandersForFlightID(String id) {
        return "select commander from commanders where flight=" + id + ";";
    }

    public static String getFirstOfficersForFlightID(String id) {
        return "select commander from commanders where flight=" + id + ";";
    }

    public static String getFlightAssistantsForFlightID(String id) {
        return "select commander from commanders where flight=" + id + ";";
    }

    public static String getEmployeeInfo(String id) {
        return "select * from personal where id=" + id + ";";
    }

    public static final String getPersonalForFlight =
        "select f.id, c.commander, fo.first_officer, fa.assistant " +
        "from flight f, commanders c, first_officers fo, flight_assistants fa " +
        "where f.id = c.flight and f.id = fo.flight and f.id = fa.flight_assistants " +
        "order by f.id";

    public static final String getAirports =
        "select * from airport";

    public static final String getRoutes =
        "select * from route";

    public static String getRouteForID(String id) {
        return "select * from route where id=" + id + ";";
    }

    public static final String getCredentials =
        "select * from credentials";

    public static final String commitFlight(String departureTime, String passengersNumber, String route, String aircraft) {
        return "insert into flight (departure_time, passengers_number, route, aircraft) values ('" +
                departureTime + "', " + passengersNumber + ", " + route + ", '" + aircraft + "');";
    }

    public static String getLastFlightId =
        "select id from flight group by id having id=max(id);";
}
