import org.checkerframework.checker.units.qual.A;

import db.ConstantQueries;
import db.PgDB;
import model.DimensionClass;

import java.util.Vector;

public class Airport {
    public final String icao;
    public final DimensionClass dimensionClass;
    public final String name;
    public final String nation;
    public final String city;

    Airport(Vector<String> row) {
        this.icao = row.get(0);

        String dimensionClass = row.get(1);
        switch (dimensionClass) {
            case "3C":
                this.dimensionClass = DimensionClass.C3;
                break;

            case "4C":
                this.dimensionClass = DimensionClass.C4;
                break;

            case "4E":
                this.dimensionClass = DimensionClass.E4;
                break;

            default:
                this.dimensionClass = DimensionClass.E4;
        }

        this.name = row.get(2);
        this.nation = row.get(3);
        this.city = row.get(4);
    }

    public static Vector<Airport> getAllFromQuery() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getAirports);
        Vector<Airport> airports = new Vector<>();

        for (var row : result) {
            airports.add(new Airport(row));
        }

        return airports;
    }

    @Override
    public String toString() {
        return "Airport {" + this.icao + " " + this.dimensionClass + " " + this.city + "}";
    }
}
