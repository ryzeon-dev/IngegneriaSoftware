package model.tests;
import model.DimensionClass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DimesionClassTest {

    @Test
    public void IsCompatibleTestSuccess() {
        assertTrue(DimensionClass.C3.isCompatible(DimensionClass.C3));
        assertTrue(DimensionClass.C3.isCompatible(DimensionClass.C4));
        assertTrue(DimensionClass.C4.isCompatible(DimensionClass.E4));
    }
    @Test
    public void IsCompatibleTestFail() {
        assertFalse(DimensionClass.E4.isCompatible(DimensionClass.C3));
        assertFalse(DimensionClass.C4.isCompatible(DimensionClass.C3));
        assertFalse(DimensionClass.C4.isCompatible(DimensionClass.C3));
    }

}
