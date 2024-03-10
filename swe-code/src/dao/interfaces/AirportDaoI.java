package dao.interfaces;

import java.util.Vector;
import model.Airport;

public interface AirportDaoI {
    public Vector<Airport> getAll();
    public Airport getById(String icao);
}
