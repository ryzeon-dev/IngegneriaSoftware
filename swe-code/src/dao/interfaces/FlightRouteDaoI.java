package dao.interfaces;

import java.util.Vector;
import model.FlightRoute;

public interface FlightRouteDaoI {
    public Vector<FlightRoute> getAll();
    public FlightRoute getRouteById(int id);
    public void create(int distance, int duration, String departure, String stepover, String arrival);
    public void delete(String id);
}
