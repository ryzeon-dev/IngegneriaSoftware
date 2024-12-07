package dao;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import db.PgDB;
import db.PreparedStatementQueries;
import model.Aircraft;
import model.DimensionClass;

public class ParkingDaoPg implements dao.interfaces.ParkingDaoI {

    @Override
    public Vector<Aircraft> getParked(String icao) {
        PgDB db = new PgDB();
        Vector<Aircraft> aircrafts = new Vector<>();
        try {
            var preparedStatement = db.makePreparedStatement(PreparedStatementQueries.GetParkedAircrafts);
            preparedStatement.setString(1, icao);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String plate=result.getString("plate");
                String manufacturer=result.getString("manufacturer");
                String model=result.getString("model");
                String specification=result.getString("specification");
                DimensionClass dimClass=DimensionClass.fromString(result.getString("class"));
                int assistants= result.getInt("assistants_number");
                int range= result.getInt("range");
                int seats= result.getInt("seats");
                aircrafts.add(new Aircraft(plate, manufacturer,model,specification, dimClass,assistants , range, seats)); 
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            db.close();
        }
        return aircrafts;
    }
    
}
