package dao;
import java.util.Vector;

import db.ConstantQueries;
import db.PgDB;
import model.Aircraft;
import model.DimensionClass;
public class AircraftDaoPg  implements dao.interfaces.AircraftDaoI{
  public Vector<Aircraft> getAll() {
    PgDB db = new PgDB();
    var result = db.runAndFetch(ConstantQueries.getCompanyAircrafts);
    Vector<Aircraft> aircrafts = new Vector<>();

    for (var row : result) {
        aircrafts.add(this.buildFromRow(row));
    }

    db.close();
    return aircrafts;
}

private Aircraft buildFromRow(Vector<String> dbRow){
    Aircraft aircraft= new Aircraft();
    aircraft.setPlate(dbRow.get(0));
    aircraft.setManufacturer(dbRow.get(1));
    aircraft.setModel(dbRow.get(2));
    aircraft.setSpecification(dbRow.get(3));

    aircraft.setRange(Integer.parseInt(dbRow.get(4)));
    aircraft.setAssistantsNumber(Integer.parseInt(dbRow.get(5)));

    String dClass = dbRow.get(6);

    DimensionClass dimClass;
    if (dClass.equals("3C")) {
        dimClass = DimensionClass.C3;
    } else if (dClass.equals("4C")) {
        dimClass = DimensionClass.C4;
    } else {
        dimClass = DimensionClass.E4;
    }
    aircraft.setDimensionClass(dimClass);
    aircraft.setSeats(Integer.parseInt(dbRow.get(7)));

    return aircraft;
}


}
