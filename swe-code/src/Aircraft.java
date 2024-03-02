import java.util.Vector;

public class Aircraft {
    public final String plate;
    public final String manufacturer ;
    public final String model;
    public final String specification;
    public final DimensionClass dimensionClass;

    public final int assistantsNumber;
    public final int range;
    public final int seats;
    public boolean busy = false;

    Aircraft(Vector<String> dbRow) {
        this.plate = dbRow.get(0);
        this.manufacturer = dbRow.get(1);

        this.model = dbRow.get(2);
        this.specification = dbRow.get(3);

        this.range = Integer.parseInt(dbRow.get(4));
        this.assistantsNumber = Integer.parseInt(dbRow.get(5));

        String dClass = dbRow.get(6);
        if (dClass.equals("3C")) {
            this.dimensionClass = DimensionClass.C3;

        } else if (dClass.equals("4C")) {
            this.dimensionClass = DimensionClass.C4;

        } else {
            this.dimensionClass = DimensionClass.E4;
        }

        this.seats = Integer.parseInt(dbRow.get(7));
    }

    public static Vector<Aircraft> getAllFromQuery() {
        PgDB db = new PgDB();
        var result = db.runAndFetch(ConstantQueries.getCompanyAircrafts);
        Vector<Aircraft> aircrafts = new Vector<>();

        for (var row : result) {
            aircrafts.add(new Aircraft(row));
        }

        db.close();
        return aircrafts;
    }

    @Override
    public String toString() {
        return "Aircraft {" + this.manufacturer + " " + this.model + " " + this.plate + "}";
    }
}