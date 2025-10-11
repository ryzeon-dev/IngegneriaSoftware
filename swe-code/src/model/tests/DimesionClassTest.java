package model.tests;
import model.DimensionClass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DimesionClassTest {
    @Test
    public void IsCompatibleTest_Success() {
        DimensionClass class3C = DimensionClass.fromString("3C");
        DimensionClass class4C = DimensionClass.fromString("4C");
        DimensionClass class4E = DimensionClass.fromString("4E");

        assertTrue(class3C.isCompatible(class3C));
        assertTrue(class3C.isCompatible(class4C));
        assertTrue(class4C.isCompatible(class4E));
    }

    @Test
    public void IsCompatibleTest_Fail() {
        DimensionClass class3C = DimensionClass.fromString("3C");
        DimensionClass class4C = DimensionClass.fromString("4C");
        DimensionClass class4E = DimensionClass.fromString("4E");

        assertFalse(class4E.isCompatible(class3C));
        assertFalse(class4C.isCompatible(class3C));
    }

}
