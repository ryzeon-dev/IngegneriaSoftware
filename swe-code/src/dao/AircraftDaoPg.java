package dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import db.ConstantQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.Aircraft;

public class AircraftDaoPg  implements dao.interfaces.AircraftDaoI{
    public Vector<Aircraft> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(PreparedStatementQueries.getCompanyAircrafts);
        Vector<Aircraft> aircrafts = new Vector<>();

        for (var row : result) {
            aircrafts.add(helpers.buildAircraftFromRow(row));
        }

        db.close();
        return aircrafts;
    }

    public void create(Aircraft aircraft) {
        PgDB db = new PgDB();
        var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.insertAirCraft);

        try {
            preparedStatement.setString(1, aircraft.manufacturer);
            preparedStatement.setString(2, aircraft.model);

            preparedStatement.setString(3, aircraft.specification);
            preparedStatement.setInt(4, aircraft.range);

            preparedStatement.setInt(5, aircraft.assistantsNumber);
            preparedStatement.setString(6, aircraft.dimensionClass.toString());
            preparedStatement.setInt(7, aircraft.seats);

            preparedStatement.execute();
            db.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        db.close();
    }

    public void createInstance(String plate, String modelId) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.insertAircraftInstance);
            preparedStatement.setString(1, plate);

            preparedStatement.setInt(2, Integer.parseInt(modelId));
            preparedStatement.executeQuery();
            db.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        db.close();
    }

    public void deleteInstance(String plate) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.deleteAircraftInstance);
            preparedStatement.setString(1, plate);

            preparedStatement.executeQuery();
            db.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        db.close();
    }
}