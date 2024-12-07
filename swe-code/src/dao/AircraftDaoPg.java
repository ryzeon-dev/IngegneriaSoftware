package dao;
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

    public void create(Aircraft airctaft) {
        PgDB db = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(airctaft.manufacturer);

        params.add(airctaft.model);
        params.add(airctaft.specification);

        params.add(String.valueOf(airctaft.range));
        params.add(String.valueOf(airctaft.assistantsNumber));

        params.add(airctaft.dimensionClass.toString());
        params.add(String.valueOf(airctaft.seats));

        db.runPstmtAndFetch(PreparedStatementQueries.insertAirCraft, params);
        db.commit();
        db.close();
    }

    public void createInstance(String manufacturer, String model, String specification, String plate) {
        PgDB db = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(manufacturer);

        params.add(model);
        params.add(specification);

        var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.getAircraftId);


        Vector<Vector<String>> modelId = db.runPstmtAndFetch(, params);

        params.clear();
        params.add(plate);
        params.add(modelId.get(0).get(0));

        db.runPstmtAndFetch(PreparedStatementQueries.insertAircraftInstance, params);
        db.commit();
        db.close();
    }

    public void deleteInstance(String plate) {
        PgDB db = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(plate);

        db.runPstmtAndFetch(PreparedStatementQueries.deleteAircraftInstance, params);
        db.commit();
        db.close();
    }
}