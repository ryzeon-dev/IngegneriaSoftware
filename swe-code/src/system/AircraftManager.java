package system;

import dao.interfaces.AircraftDaoI;
import model.Aircraft;

public class AircraftManager {
    private AircraftDaoI aircraftDao;

    public AircraftManager(AircraftDaoI aircraftDao){
        this.aircraftDao=aircraftDao;
    }

    public void insertAircraftModel(Aircraft aircraft){
        //TODO handle creation errors
        aircraftDao.create(aircraft);
    }
}
