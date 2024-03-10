package dao;


import db.ConstantQueries;
import db.PgDB;
import model.Airport;
import model.DimensionClass;

import java.util.Vector;

public class AirportDaoPg implements dao.interfaces.AirportDaoI {
    public Vector<Airport> getAll() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getAirports);
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
}
