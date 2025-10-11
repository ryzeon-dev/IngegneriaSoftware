package dao.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dao.AirportDaoPg;
import dao.interfaces.AirportDaoI;
import model.Airport;
import model.DimensionClass;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

public class AirportDaoTest {
    @Test
    public void getAirportByIcaoTest_Success() {
        AirportDaoI dao = new AirportDaoPg();
        Airport airport = dao.getByIcao("LIRF");

        assertTrue(airport != null);
        assertTrue(airport.icao.equals("LIRF"));
        assertTrue(airport.name.equals("Aeroporto di Roma-Fiumicino"));
        assertTrue(airport.city.equals("Roma"));
        assertTrue(airport.nation.equals("Italia"));
        assertTrue(airport.dimensionClass.equals(DimensionClass.fromString("4F")));
    }

    @Test
    public void getAirportByIcaoTest_Fail() {
        AirportDaoI dao = new AirportDaoPg();
        Airport airport = dao.getByIcao("LIRP");

        assertTrue(airport == null);
    }

    @Test
    public void airportCrudTest() {
        String icao = "LIRP";
        DimensionClass class_ = DimensionClass.fromString("4E");
        String name =  "Aeroporto di Pisa-San Giusto";
        String nation = "Italia";
        String city = "Pisa";

        AirportDaoI dao = new AirportDaoPg();

        boolean error = false;
        try {
            dao.create(icao, class_.toString(), name, nation, city);
        } catch (Exception e) {
            e.printStackTrace();
            error = true;
        }
        assertFalse(error);

        DimensionClass newClass = DimensionClass.fromString("4F");

        error = false;
        try {
            dao.update(icao, newClass.toString());
        } catch (Exception e) {
            error = true;
        }
        assertFalse(error);

        error = false;
        try {
            dao.delete(icao);
        } catch (Exception e) {
            error = true;
        }
        assertFalse(error);
    }
}
