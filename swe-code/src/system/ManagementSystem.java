package system;

import dao.AirportDaoPg;
import dao.FlightRouteDaoPg;
import model.Aircraft;
import model.Flight;
import model.FlightRoute;

import java.util.Vector;

public class ManagementSystem extends Thread {

    private SimulatedClock clock = new SimulatedClock();
    public FlightSchedule flightSchedule;
    public Vector<Flight> scheduledFlights;

    public ManagementSystem(FlightSchedule fSchedule) {
        flightSchedule = fSchedule;
        clock.start();
    }

    public void quit() {
        this.clock.run = false;
        this.clock.interrupt();

        try {
            this.clock.join();

        } catch (InterruptedException e) {
        }
    }

    public String getTime() {
        return clock.getTime();
    }

    public void runScheduling(){
        this.scheduledFlights = flightSchedule.makeSchedule();
    }
}