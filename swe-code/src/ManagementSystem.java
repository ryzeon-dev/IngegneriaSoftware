import java.util.Vector;

public class ManagementSystem extends Thread {
    private SimulatedClock clock = new SimulatedClock();
    private FlightManager manager = new FlightManager();
    private FlightSchedule flightSchedule;

    ManagementSystem() {
        this.flightSchedule = new FlightSchedule(this.manager);
    }


}
