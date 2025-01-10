package dao.interfaces;

import java.util.Vector;
import model.Aircraft;
import model.AircraftModel;

import org.checkerframework.checker.units.qual.A;

public interface AircraftDaoI {
  public Vector<AircraftModel> getAllModels();
  public Vector<Aircraft> getAll();
  public void create(Aircraft airctaft);
  public void createInstance(String plate, String modelId);
  public void deleteInstance(String plate);
}
