import java.util.Vector;

public class ManagementSystem extends Thread {
    private SimulatedClock clock = new SimulatedClock();
    private FlightManager manager;
    private FlightSchedule flightSchedule;

    ManagementSystem(FlightManager manager) {
        this.manager=manager;
        this.flightSchedule = new FlightSchedule(this.manager);
    }


}
