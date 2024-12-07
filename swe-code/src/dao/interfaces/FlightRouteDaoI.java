package dao.interfaces;

import java.util.Vector;
import model.FlightRoute;

public interface FlightRouteDaoI {
    public Vector<FlightRoute> getAll();
    public void create(int distance, int duration, String departure, String arrival);
    public void delete(String id);
}
