package system;

import java.util.Vector;

import model.Flight;
import system.scheduling.schedulingStrategy.SchedulingStrategy;

public class FlightSchedule {
    private SchedulingStrategy strategy=null;

    public FlightSchedule(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public Vector<Flight> makeSchedule() {
        if(strategy != null){
            return strategy.run();
        }

        return null;
    }
}
