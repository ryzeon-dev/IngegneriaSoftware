package system;

import java.util.Vector;

import model.Flight;
import system.scheduling.schedulingStrategy.SchedulingStrategy;

public class FlightSchedule {
    FlightManager manager;
    SchedulingStrategy strategy=null;

    public FlightSchedule(FlightManager manager,SchedulingStrategy strategy) {
        this.manager = manager;
        this.strategy = strategy;
    }

    public Vector<Flight> makeSchedule() {
        if(strategy != null){
            return strategy.run();
        }
        return null;
    }
}
