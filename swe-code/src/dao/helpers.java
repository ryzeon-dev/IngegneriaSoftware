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

        String dClass = dbRow.get(6);

        DimensionClass dimClass;
        if (dClass.equals("3C")) {
            dimClass = DimensionClass.C3;

        } else if (dClass.equals("4C")) {
            dimClass = DimensionClass.C4;

        } else {
            dimClass = DimensionClass.E4;
        }

        int seats = Integer.parseInt(dbRow.get(7));

        return new Aircraft(plate, manufacturer, model, specification, dimClass, assistantsNumber, range, seats);
    }
}
