public class ConstantQueries {
    public static final String getCompanyAircrafts =
        "select ai.plate, a.manufacturer, a.model, a.specification, " +
        "a.range, a.assistants_number, a.class, a.seats " +
        "from aircraft_instance ai join aircraft a on " +
        "ai.aircraft = a.id";

    public static final String getCompanyEmployees =
        "select * from personal";

    public static final String getFlights =
        "select f.id, f.departure_time, f.passengers_number, f.route, " +
        "r.distance, r.duration, r.departure, r.stepover, r.arrival, " +
        "f.aircraft, f.commander, f.firstOfficial" +
        "from flight f join route r on" +
        "f.route = r.id";

    public static final String getAirports =
        "select * from airport";

    public static final String getRoutes =
        "select * from route";
}
