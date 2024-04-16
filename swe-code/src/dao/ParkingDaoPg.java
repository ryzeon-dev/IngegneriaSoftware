package dao;

import java.util.Vector;

import db.PgDB;
import db.PreparedStatementQueries;
import model.Aircraft;

public class ParkingDaoPg implements dao.interfaces.ParkingDaoI {

    @Override
    public Vector<Aircraft> getParked(String icao) {
        PgDB db = new PgDB();
        var result = db.runPstmtAndFetch(PreparedStatementQueries.GetParkedAircrafts,icao);
        Vector<Aircraft> aircrafts = new Vector<>();

        for (var row : result) {
            aircrafts.add(helpers.buildAircraftFromRow(row));
        }
        
        return aircrafts;
    }
    
}
