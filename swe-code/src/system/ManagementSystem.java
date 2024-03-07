package system;

import java.util.Vector;

public class ManagementSystem extends Thread {
    private SimulatedClock clock = new SimulatedClock();
    public FlightManager manager;
    private FlightSchedule flightSchedule;

    public ManagementSystem(FlightManager manager) {
        this.manager = manager;
        this.flightSchedule = new FlightSchedule(this.manager);
        clock.start();
    }

    public void quit() {
        this.clock.run = false;
        this.clock.interrupt();

        try {
            this.clock.join();
            System.out.println("Clock thread closed");

        } catch (InterruptedException e) {
            System.out.println("Error closing clock thread");
        }
    }

    public String getTime() {
        return clock.getTime();
    }
}
