package system.scheduling.schedulingStrategy;

import java.util.Vector;

import model.Flight;

public interface SchedulingStrategy {
    Vector<Flight> makeSchedule();
}