package dao.interfaces;

import java.util.Vector;
import model.FlightRoute;

public interface FlightRouteDaoI {
    public Vector<FlightRoute> getAll();
    public void create(String distance, String duration, String departure, String arrival);
    public void delete(String id);
}
