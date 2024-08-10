package dao.interfaces;

import model.Flight;
import model.FlightRoute;

import java.util.Vector;

public interface FlightDaoI {
    public Vector<Flight> getAll();
}
