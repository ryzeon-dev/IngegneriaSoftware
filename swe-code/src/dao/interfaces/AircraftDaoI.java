package dao.interfaces;

import java.util.Vector;
import model.Aircraft;

public interface AircraftDaoI {
  public Vector<Aircraft> getAllFromQuery();
}
