package db;

public class PreparedStatementQueries {
    /* EMPLOYEE */
    public static final String getCompanyEmployees = "select * from personal";

    public static String getEmployeeById = "select * from personal where id=?";

    public static final String insertPersonal = "INSERT INTO personal (name, lastname, role, abilitation) values (?, ?, ?, ?)";

    public static final String deletePersonal = "DELETE FROM personal where id=?";

    public static final String getAllCommanders = "SELECT * FROM personal WHERE role='commander'";

    public static final String getAllFirstOfficers = "SELECT * FROM personal WHERE role='firstofficer'";

    public static final String getAllFlightAssistants = "SELECT * FROM personal WHERE role='hostess/steward'";

    public static final String getCredentials = "select * from credentials";

    public static final String getCredentialsForUname = "select * from credentials where username=?";

    public static final String getPasswdHashForUname = "select passwd from credentials where username=?";

    /* AIRCRAFTS */

    public static final String deleteAircraftModel= "DELETE FROM aircraft WHERE id=?";

    public static final String getAircraftModels= "SELECT id,manufacturer,model,specification,range,assistants_number, class,seats from aircraft";

    public static final String getCompanyAircrafts = "select ai.plate, a.manufacturer, a.model, a.specification, " +
        "a.range, a.assistants_number, a.class, a.seats " +
        "from aircraft_instance ai join aircraft a on " +
        "ai.aircraft = a.id";

    public static final String insertAirCraft = "INSERT INTO aircraft " +
            "(manufacturer, model, specification, range, assistants_number, class, seats) values (?, ?, ?, ?, ?, ?, ?)";

    public static final String insertAircraftInstance = "INSERT INTO aircraft_instance (plate, aircraft) values (?, ?)";

    public static final String getAircraftId = "SELECT id FROM airctaft WHERE manufacturer=? and model=? and specification=?";

    public static final String deleteAircraftInstance = "DELETE FROM aircraft_instance WHERE plate=?";

    public static final String getParkedAircrafts = "select ai.plate, a.manufacturer, a.model, a.specification, a.range, a.assistants_number, a.class, a.seats "+
            "from parking p "+
            "join aircraft_instance ai on ai.plate = p.aircraft "+
            "join aircraft a on ai.aircraft = a.id "+
            "where p.airport = ?";

    public static final String getAircraftModelNames = "select distinct model from aircraft";

    /* FLIGHT */

    public static final String getFlights = "select * from flight";

    public static String getCommandersForFlightID = "select commander from commanders where flight=?";

    public static String getFirstOfficersForFlightID =  "select firstOfficer from first_officer where flight=?";

    public static String getFlightAssistantsForFlightID = "select assistant from flight_assistants where flight=?";

    public static final String getPersonalForFlight = "select f.id, c.commander, fo.first_officer, fa.assistant " +
        "from flight f, commanders c, first_officers fo, flight_assistants fa " +
        "where f.id = c.flight and f.id = fo.flight and f.id = fa.flight_assistants " +
        "order by f.id";

    public static final String commitFlight = "insert into flight (departure_time, passengers_number, route, aircraft) values (?, ?, ?, ?)";

    public static String getLastFlightId = "select id from flight where id=(select max(id) from flight);";

    /* AIRPORT */

    public static final String getAirports = "select * from airport";

    public static final String getRoutes = "select * from route";

    public static String getRouteForID = "select * from route where id=?";

    public static final String getAirportById = "SELECT * FROM airport WHERE id=?";

    public static final String insertAirport = "INSERT INTO airport (icao, class, name, nation, city) values (?, ?, ?, ?, ?)";

    public static final String deleteAirport = "DELETE FROM airport where icao=?";

    public static final String updateAirport = "UPDATE airport SET class=? WHERE icao=?";

    /* ROUTE */

    public static final String insertRoute = "INSERT INTO route (distance, duration, departure, stepover, arrival) values (?, ?, ?, ?, ?)";

    public static final String deleteRoute = "DELETE FROM route WHERE id=?";
}
