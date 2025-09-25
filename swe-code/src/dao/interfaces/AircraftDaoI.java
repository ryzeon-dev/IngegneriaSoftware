package dao.interfaces;

import java.util.Vector;
import model.Aircraft;
import model.AircraftModel;

public interface AircraftDaoI {
  public Vector<AircraftModel> getAllModels();
  public Vector<Aircraft> getAllInstances();
  public void createModel(Aircraft airctaft);
  public void createInstance(String plate, String modelId);
  public void deleteInstance(String plate);
  public void deleteModel(String modelId);
  public Vector<String> getAllModelNames();
}
