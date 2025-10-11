package dao.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dao.AircraftDaoPg;
import dao.interfaces.AircraftDaoI;
import model.Aircraft;
import model.AircraftModel;
import model.DimensionClass;
import org.junit.Test;

import java.util.Vector;

public class AircraftDaoTests {
    @Test
    public void getAllModelsTest() {
        AircraftDaoI dao = new AircraftDaoPg();
        Vector<AircraftModel> allModels = dao.getAllModels();

        AircraftModel genericModel = new AircraftModel(
                6, "Airbus", "A321", "Neo", 7400, 4,
                DimensionClass.fromString("4C"), 165
        );

        assertTrue(allModels.contains(genericModel));
    }

    @Test
    public void getAllInstancesTest() {
        AircraftDaoI dao = new AircraftDaoPg();
        Vector<Aircraft> allInstances = dao.getAllInstances();

        Aircraft genericInstance = new Aircraft(
                "EI-HLA", "Airbus", "A220", "100", DimensionClass.fromString("3C"),3, 6204, 125
        );

        assertTrue(allInstances.contains(genericInstance));
    }

    @Test
    public void AircraftCrudTest() {
        AircraftDaoI dao = new AircraftDaoPg();

        AircraftModel F22 = new AircraftModel("Lockeed&Martin", "F22-Raptor", "NATF", 8000, 0, DimensionClass.fromString("1B"), 1);
        dao.createModel(F22);

        Vector<AircraftModel> allInstances = dao.getAllModels();
        int index = allInstances.indexOf(F22);

        // Index is -1 if not found
        assertFalse(index == -1);
        int modelId = allInstances.get(index).modelId;

        String F22InstancePlate = "US-RPT";
        boolean error = false;

        try {
            dao.createInstance(F22InstancePlate, String.valueOf(modelId));

        } catch (Exception e) {
            error = true;
        }

        assertFalse(error);

        error = false;
        try {
            dao.deleteInstance(F22InstancePlate);
        } catch (Exception e) {
            error = true;
        }

        assertFalse(error);

        error = false;
        try {
            dao.deleteModel(String.valueOf(modelId));
        } catch (Exception e) {
            error = true;
        }

        assertFalse(error);
    }
}