package dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import db.PgDB;
import db.PreparedStatementQueries;
import model.Aircraft;

public class ParkingDaoPg implements dao.interfaces.ParkingDaoI {

    @Override
    public Vector<Aircraft> getParked(String icao) {
        PgDB db = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(icao);

        var result = db.runPstmtAndFetch(PreparedStatementQueries.GetParkedAircrafts, params);
        Vector<Aircraft> aircrafts = new Vector<>();

        for (var row : result) {
            aircrafts.add(helpers.buildAircraftFromRow(row));
        }
        
        return aircrafts;
    }
    
}
