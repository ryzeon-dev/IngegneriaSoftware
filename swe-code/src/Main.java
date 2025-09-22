import cli.CLI;
import system.ManagementSystem;
import system.scheduling.schedulingStrategy.SimpleSchedule;
import system.FlightSchedule;

public class Main {
    public static void main(String[] args) {
        SimpleSchedule schedulingStrategy = new SimpleSchedule();

        FlightSchedule flightSchedule = new FlightSchedule(schedulingStrategy);
        ManagementSystem managementSystem = new ManagementSystem(flightSchedule);

        CLI cli = new CLI(managementSystem);
        cli.run();
    }
}