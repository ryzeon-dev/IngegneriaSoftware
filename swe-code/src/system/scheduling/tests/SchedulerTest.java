package system.scheduling.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import model.Flight;
import system.scheduling.schedulingStrategy.SimpleSchedule;

public class SchedulerTest {
    // Test di integrazione per verificare il funzionamento  dello scheduler
    // Con un input conosciuto.
    @Test
    public void testScheduler() {
        SimpleSchedule scheduler = new SimpleSchedule();
        Vector<Flight> result = scheduler.run();
        //Aircraft EI-HLA
        assertEquals(result.get(0).aircraftPlate, "EI-HLA");
        assertEquals(result.get(0).route.departure, "EDDF");
        assertEquals(result.get(0).route.arrival, "LIRF");
        assertEquals(result.get(0).commanders.get(0).name, "Alfonso");
        
        //Aircraft EI-IFD
        assertEquals(result.get(1).aircraftPlate, "EI-IFD");
        assertEquals(result.get(1).route.departure, "LIRF");
        assertEquals(result.get(1).route.arrival, "KJFK");
        assertEquals(result.get(1).commanders.get(0).name, "Davide");

        //Aircraft EI-IML
        assertEquals(result.get(2).aircraftPlate, "EI-IML");
        assertEquals(result.get(2).route.departure, "LIRF");
        assertEquals(result.get(2).route.arrival, "OEJN");
        assertEquals(result.get(2).commanders.get(0).name, "Grazia");
        //Aircraft EI-HLA 
        assertEquals(result.get(3).aircraftPlate, "EI-IMB");
        assertEquals(result.get(3).route.departure, "LIRF");
        assertEquals(result.get(3).route.arrival, "EHAM");
        assertEquals(result.get(3).commanders.get(0).name, "Ernesto");

        //Aircraft EI-HLA
        assertEquals(result.get(4).aircraftPlate, "EI-HLA");
        assertEquals(result.get(4).route.departure, "LIRF");
        assertEquals(result.get(4).route.arrival, "EBBR");
        assertEquals(result.get(4).commanders.get(0).name, "Alfonso");

        //Aircraft EI-IFD Return
        assertEquals(result.get(5).aircraftPlate, "EI-IFD");
        assertEquals(result.get(5).route.departure, "KJFK");
        assertEquals(result.get(5).route.arrival, "LIRF");
        assertEquals(result.get(5).commanders.get(0).name, "Davide");

        //Aircraft EI-HLA Return
        assertEquals(result.get(6).aircraftPlate, "EI-HLA");
        assertEquals(result.get(6).route.departure, "EBBR");
        assertEquals(result.get(6).route.arrival, "LIRF");
        assertEquals(result.get(6).commanders.get(0).name, "Alfonso");

        //Aircraft 
        assertEquals(result.get(7).aircraftPlate, "EI-IFD");
        assertEquals(result.get(7).route.departure, "LIRF");
        assertEquals(result.get(7).route.arrival, "KJFK");
        assertEquals(result.get(7).commanders.get(0).name, "Davide");
        
    }
}
