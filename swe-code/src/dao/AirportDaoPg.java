package dao;


import db.ConstantQueries;
import db.PgDB;
import db.PreparedStatementQueries;
import model.Airport;
import model.DimensionClass;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Vector;

public class AirportDaoPg implements dao.interfaces.AirportDaoI {
    public Vector<Airport> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(PreparedStatementQueries.getAirports);
        Vector<Airport> airports = new Vector<>();

        for (var row : result) {
            airports.add(this.buildFromRow(row));
        }

        return airports;
    }

    @Override
    public Airport getById(String icao) {
        // TODO Improve Quick and dirty implementation.
        // this should be using prepared statement
        for (Airport airport : this.getAll()) {
            if (airport.icao.equals(icao)) {
                return airport;
            }
        }
        return null;
    }
    public Airport buildFromRow(Vector<String> row) {
        String icao = row.get(0);

        String strDimensionClass = row.get(1);
        DimensionClass dimensionClass;
        switch (strDimensionClass) {
            case "3C":
                dimensionClass = DimensionClass.C3;
                break;

            case "4C":
                dimensionClass = DimensionClass.C4;
                break;

            case "4E":
                dimensionClass = DimensionClass.E4;
                break;

            default:
                dimensionClass = DimensionClass.E4;
                break;
        }

        String name = row.get(2);
        String nation = row.get(3);
        String city = row.get(4);

        return new Airport(icao, dimensionClass, name, nation, city);
    }

    @Override
    public void create(String icao, DimensionClass dimensionClass, String name, String nation, String city) {
        PgDB db = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(icao);

        params.add(dimensionClass.toString());
        params.add(name);

        params.add(nation);
        params.add(city);

        db.runPstmtAndFetch(PreparedStatementQueries.insertAirport, params);
        db.commit();
        db.close();
    }

    @Override
    public void delete(String icao) {
        PgDB db = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(icao);

        db.runPstmtAndFetch(PreparedStatementQueries.deleteAirport, params);
        db.commit();
        db.close();
    }

    @Override
    public void update(String icao, DimensionClass dimensionClass) {
        PgDB pgDB = new PgDB();

        ArrayList<String> params = new ArrayList<>();
        params.add(dimensionClass.toString());
        params.add(icao);

        pgDB.runPstmtAndFetch(PreparedStatementQueries.updateAirport, params);

        pgDB.commit();
        pgDB.close();
    }
}
