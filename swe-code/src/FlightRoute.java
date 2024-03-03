import java.util.Vector;

import db.ConstantQueries;
import db.PgDB;

public class FlightRoute {
    public final int id;
    public final int distance;
    public final int duration;
    public final String departure;
    public final String stepover;
    public final String arrival;

    FlightRoute(Vector<String> row) {
        this.id = Integer.parseInt(row.get(0));
        this.distance = Integer.parseInt(row.get(1));

        this.duration = Integer.parseInt(row.get(2));
        this.departure = row.get(3);

        this.stepover = row.get(4);
        this.arrival = row.get(5);
    }

    public static Vector<FlightRoute> getAllFromQuery() {
        PgDB db = new PgDB();
        var dbResult = db.runAndFetch(ConstantQueries.getRoutes);
        Vector<FlightRoute> routes = new Vector<>();

        for (var row: dbResult) {
            routes.add(new FlightRoute(row));
        }

        return routes;
    }

    @Override
    public String toString() {
        return "Route {" + this.departure + "->" + this.arrival + "}";
    }

}
