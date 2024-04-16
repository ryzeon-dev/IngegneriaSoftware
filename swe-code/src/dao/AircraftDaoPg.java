package dao;
import java.util.Vector;

import db.ConstantQueries;
import db.PgDB;
import model.Aircraft;

public class AircraftDaoPg  implements dao.interfaces.AircraftDaoI{
    public Vector<Aircraft> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getCompanyAircrafts);
        Vector<Aircraft> aircrafts = new Vector<>();

        for (var row : result) {
            aircrafts.add(helpers.buildAircraftFromRow(row));
        }

        db.close();
        return aircrafts;
    }

}
