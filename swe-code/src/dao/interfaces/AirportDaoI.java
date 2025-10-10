package dao.interfaces;

import java.util.Vector;
import model.Airport;

public interface AirportDaoI {
    public Vector<Airport> getAll();
    public Airport getByIcao(String icao);
    public void create(String icao, String dimensionClass, String name, String nation, String city);
    public void delete(String icao);
    public void update(String icao, String dimensionClass);
}
