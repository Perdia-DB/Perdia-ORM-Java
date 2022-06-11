package com.github.perdia.queries.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Java Unit Testing class for the abstract DataEntry class and its subclasses
 */
class DataEntryTest {

    @Test
    void StringDataEntryTest() {
        String testString = "Test";
        DataEntry stringDataEntry = new StringDataEntry(testString);

        assertEquals(DataType.STRING, stringDataEntry.getDataType());
        assertEquals(testString, stringDataEntry.read());
        assertEquals(String.class, stringDataEntry.read().getClass());
    }

    @Test
    void doubleDataEntryTest() {
        double testDouble = 35453000465.68004555555334;
        DataEntry doubleDataEntry = new DoubleDataEntry(testDouble);

        assertEquals(DataType.FLOAT, doubleDataEntry.getDataType());
        assertEquals(testDouble, doubleDataEntry.read());
        assertEquals(Double.class, doubleDataEntry.read().getClass());
    }

    @Test
    void longDataEntryTest() {
        long testLong = 333769798564535L;
        DataEntry longDataEntry = new LongDataEntry(testLong);

        assertEquals(DataType.INTEGER, longDataEntry.getDataType());
        assertEquals(testLong, longDataEntry.read());
        assertEquals(Long.class, longDataEntry.read().getClass());
    }

    @Test
    void emptyDataEntryTest() {
        DataEntry emptyDataEntry = new LongDataEntry();
        assertNull(emptyDataEntry.read());

        emptyDataEntry = new DoubleDataEntry();
        assertNull(emptyDataEntry.read());

        emptyDataEntry = new StringDataEntry();
        assertNull(emptyDataEntry.read());
    }
}