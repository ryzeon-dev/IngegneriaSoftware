package model.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dao.AircraftDaoPg;
import dao.AirportDaoPg;
import dao.interfaces.AircraftDaoI;
import dao.interfaces.AirportDaoI;
import model.Aircraft;
import model.Airport;
import org.junit.Test;

import java.util.Vector;

public class AircraftTest {
    @Test
    public void canGoTest() {
        AircraftDaoI aircraftDao = new AircraftDaoPg();
        Vector<Aircraft> allAircrafts = aircraftDao.getAllInstances();

        Aircraft smallAircraft = allAircrafts.firstElement();
        Aircraft bigAircraft = allAircrafts.lastElement();

        AirportDaoI airportDao = new AirportDaoPg();
        Airport smallAirport = airportDao.getByIcao("LIRQ");
        Airport bigAirport = airportDao.getByIcao("LIRF");

        assertTrue(smallAircraft.canGo(smallAirport));
        assertTrue(smallAircraft.canGo(bigAirport));
        assertTrue(bigAircraft.canGo(bigAirport));
    }

    @Test
    public void cannotGoTest() {
        AircraftDaoI aircraftDao = new AircraftDaoPg();
        Vector<Aircraft> allAircrafts = aircraftDao.getAllInstances();

        Aircraft bigAircraft = allAircrafts.lastElement();

        AirportDaoI airportDao = new AirportDaoPg();
        Airport smallAirport = airportDao.getByIcao("LIRQ");
        Airport anotherSmallAirport = airportDao.getByIcao("LIBR");

        assertFalse(bigAircraft.canGo(smallAirport));
        assertFalse(bigAircraft.canGo(anotherSmallAirport));
    }
}
