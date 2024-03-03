import java.util.Vector;

import dao.AircraftDaoPg;

public class Main {
    public static void main(String[] args) {
        AircraftDaoPg aircraftDao= new AircraftDaoPg();
        FlightManager manbager = new FlightManager(aircraftDao);

        System.out.println(manbager);
        //System.out.println(employees);
        //System.out.println(airports);
    }
}