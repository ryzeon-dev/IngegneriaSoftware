package system;

import java.util.Vector;

import dao.interfaces.AircraftDaoI;
import model.Aircraft;
import model.AircraftModel;

public class AircraftManager {
    private AircraftDaoI aircraftDao;

    public AircraftManager(AircraftDaoI aircraftDao){
        this.aircraftDao=aircraftDao;
    }
    public void insertAircraftInstance(String plate, int modelId){
        aircraftDao.createInstance(plate, Integer.toString(modelId));

    }

    public void insertAircraftModel(Aircraft aircraft){
        //TODO handle creation errors
        aircraftDao.create(aircraft);
    }

    public Vector<AircraftModel> getAllModels(){
        //TODO handle creation errors
        return aircraftDao.getAllModels();
    }

    public void deleteAircraftModel(AircraftModel model){
        aircraftDao.deleteAircraftModel(Integer.toString(model.modelId));
    }

    public void deleteAircraft(String plate) {
        aircraftDao.deleteInstance(plate);
    }
}
