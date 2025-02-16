package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.postgresql.core.SqlCommand;

import db.ConstantQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.FlightRoute;

public class FlightRouteDaoPg implements dao.interfaces.FlightRouteDaoI {
    public Vector<FlightRoute> getAll() {
        PgDB db = new PgDB();
        Vector<FlightRoute> routes = new Vector<>();
        try {
            var result = db.makePreparedStatement(PreparedStatementQueries.getRoutes).executeQuery();
            while (result.next()) {
                routes.add(buildFromRow(result));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        db.close();
        return routes;
    }

    public FlightRoute getRouteById(int id) {
        ResultSet result=null;

        PgDB db = new PgDB();
        FlightRoute route=null;

        try{
            var preparedStatement= db.makePreparedStatement(PreparedStatementQueries.getRouteForID);

            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();

            result.next();
            route=buildFromRow(result);

        } catch(SQLException e) {
            e.printStackTrace();

        } finally {
            db.close();
        }

        return route;
    }

    public static FlightRoute buildFromRow(ResultSet res) throws SQLException {
        int id = res.getInt(1);
        int distance = res.getInt(2);

        int duration = res.getInt(3);
        String departure = res.getString(4);

        String stepover = res.getString(5);
        String arrival = res.getString(6);

        return new FlightRoute(id, distance, duration, departure, stepover, arrival);
    }

    @Override
    public void create(int distance, int duration, String departure, String arrival) {
        PgDB pgDB = new PgDB();
        var preparedStatement= pgDB.makePreparedStatement(PreparedStatementQueries.insertRoute);

        try {
            preparedStatement.setInt(1,distance);
            preparedStatement.setInt(2,duration);

            preparedStatement.setString(3, departure);
            preparedStatement.setString(4, arrival);

            preparedStatement.execute();
            pgDB.commit();

        } catch (SQLException e) {
            pgDB.close();
            throw new RuntimeException();

        } finally {
            pgDB.close();
        }
    }

    @Override
    public void delete(String id) {
        PgDB pgDB = new PgDB();
        var preparedStatement= pgDB.makePreparedStatement(PreparedStatementQueries.getRouteForID);

        try {
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            pgDB.commit();

        } catch (SQLException e) {
            pgDB.close();
            throw new RuntimeException();

        } finally {
            pgDB.close();
        }
    }
}
