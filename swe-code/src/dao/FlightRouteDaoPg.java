package dao;
import java.util.Vector;

import db.ConstantQueries;
import db.PgDB;
import model.FlightRoute;

public class FlightRouteDaoPg implements dao.interfaces.FlightRouteDaoI {
    public Vector<FlightRoute> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getRoutes);
        Vector<FlightRoute> routes = new Vector<>();

        for (var row : result) {
            routes.add(this.buildFromRow(row));
        }

        db.close();
        return routes;
    }

    public FlightRoute getRouteById(String id) {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getRouteForID(id));

        db.close();
        return buildFromRow(result.get(0));
    }

    public static FlightRoute buildFromRow(Vector<String> row) {
        int id = Integer.parseInt(row.get(0));
        int distance = Integer.parseInt(row.get(1));

        int duration = Integer.parseInt(row.get(2));
        String departure = row.get(3);

        String stepover = row.get(4);
        String arrival = row.get(5);

        return new FlightRoute(id, distance, duration, departure, stepover, arrival);
    }

}
