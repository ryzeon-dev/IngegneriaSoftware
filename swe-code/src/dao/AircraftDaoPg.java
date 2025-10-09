package dao;
import java.sql.SQLException;
import java.util.Vector;

import db.PgDB;
import db.PreparedStatementQueries;
import model.Aircraft;
import model.AircraftModel;
import model.DimensionClass;

public class AircraftDaoPg  implements dao.interfaces.AircraftDaoI {
    private static Aircraft buildAircraftFromRow(Vector<String> dbRow){
        String plate = dbRow.get(0);
        String manufacturer = dbRow.get(1);

        String model = dbRow.get(2);
        String specification = dbRow.get(3);

        int range = Integer.parseInt(dbRow.get(4));
        int assistantsNumber = Integer.parseInt(dbRow.get(5));

        String dClass = dbRow.get(6);
        DimensionClass dimensionClass = DimensionClass.fromString(dClass);

        int seats = Integer.parseInt(dbRow.get(7));
        return new Aircraft(plate, manufacturer, model, specification, dimensionClass, assistantsNumber, range, seats);
    }

    private static AircraftModel buildAircarftModelFromRow(Vector<String> dbRow){
        int modelId=Integer.parseInt(dbRow.get(0));
        String manufacturer = dbRow.get(1);

        String model = dbRow.get(2);
        String specification = dbRow.get(3);

        int range = Integer.parseInt(dbRow.get(4));
        int assistantsNumber = Integer.parseInt(dbRow.get(5));

        String dClass = dbRow.get(6);
        DimensionClass dimensionClass = DimensionClass.fromString(dClass);

        int seats = Integer.parseInt(dbRow.get(7));
        return new AircraftModel(modelId, manufacturer, model, specification, range, assistantsNumber, dimensionClass, seats);
    }

    @Override
    public Vector<AircraftModel> getAllModels(){
        PgDB db = new PgDB();
        Vector<AircraftModel> aircraftModels= new Vector<>();

        var result= db.runAndFetch(PreparedStatementQueries.getAircraftModels);
        for (var row : result) {
            aircraftModels.add(buildAircarftModelFromRow(row));
        }

        db.close();
        return aircraftModels;
    }

    @Override
    public Vector<String> getAllModelNames() {
        Vector<String> models = new Vector<>();
        PgDB db = new PgDB();

        var rows = db.runAndFetch(PreparedStatementQueries.getAircraftModelNames);
        db.close();

        for (var row : rows) {
            models.add(row.firstElement());
        }

        return models;
    }

    @Override
    public Vector<Aircraft> getAllInstances() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(PreparedStatementQueries.getCompanyAircrafts);
        Vector<Aircraft> aircrafts = new Vector<>();

        for (var row : result) {
            aircrafts.add(buildAircraftFromRow(row));
        }

        db.close();
        return aircrafts;
    }

    @Override
    public void createModel(AircraftModel aircraft) {
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
            db.close();
            throw new RuntimeException(e);
        }

        db.close();
    }

    @Override
    public void createInstance(String plate, String modelId) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.insertAircraftInstance);
            preparedStatement.setString(1, plate);

            preparedStatement.setInt(2, Integer.parseInt(modelId));
            preparedStatement.execute();
            db.commit();

        } catch (SQLException e) {
            db.close();
            throw new RuntimeException(e);

        } finally {
            db.close();
        }

    }

    @Override
    public void deleteInstance(String plate) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.deleteAircraftInstance);
            preparedStatement.setString(1, plate);

            preparedStatement.execute();
            db.commit();

        } catch (SQLException e) {
            db.close();
            throw new RuntimeException(e);

        } finally {
            db.close();
        }
    }

    @Override
    public void deleteModel(String modelId) {
        PgDB db = new PgDB();

        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.deleteAircraftModel);
            preparedStatement.setInt(1, Integer.parseInt(modelId));

            preparedStatement.execute();
            db.commit();

        } catch (SQLException e) {
            db.close();
            throw new RuntimeException(e);
        }

        db.close();
    }
}