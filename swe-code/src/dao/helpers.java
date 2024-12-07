package dao;

import java.util.Vector;

import model.Aircraft;
import model.DimensionClass;

public class helpers {
    public static Aircraft buildAircraftFromRow(Vector<String> dbRow){
        String plate = dbRow.get(0);

        String manufacturer = dbRow.get(1);

        String model = dbRow.get(2);
        String specification = dbRow.get(3);

        int range = Integer.parseInt(dbRow.get(4));
        int assistantsNumber = Integer.parseInt(dbRow.get(5));
        DimensionClass dimensionClass;
        String dClass = dbRow.get(6);
        switch (dClass) {
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

        int seats = Integer.parseInt(dbRow.get(7));

        return new Aircraft(plate, manufacturer, model, specification, dimensionClass, assistantsNumber, range, seats);
    }
}
