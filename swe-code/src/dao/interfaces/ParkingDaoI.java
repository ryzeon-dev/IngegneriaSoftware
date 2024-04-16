package dao.interfaces;
import java.util.Vector;

import model.Aircraft;
public interface ParkingDaoI {
    //Return parkedAircraft on a given airport.
    public Vector<Aircraft> getParked(String icao);
}