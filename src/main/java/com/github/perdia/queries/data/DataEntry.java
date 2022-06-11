package com.github.perdia.queries.data;

/**
 * Abstract class all other DataEntry classes inherit from in a hierarchical order.
 */
public abstract class DataEntry {
    /**
     * Variable storing the data of the respective DataEntry. It is of type Object because it has to house Strings,
     * Longs and Doubles.
     */
    private Object value;

    /**
     * Constructor which doesn't do anything other than create a new DataEntry Object.
     */
    public DataEntry() {}

    /**
     * Writes the given value to the DataEntry. Any already existing value is overwritten.
     * @param value The value to be written to the DataEntry
     */
    public void write(Object value) {
        this.value = value;
    }

    /**
     * Reads the value currently stored in the DataEntry.
     * @return The value stored in the DataEntry
     */
    public Object read() {
        return this.value;
    }

    /**
     * Returns the DataType of the respective DataEntry.
     * @return DataType of the DataEntry
     */
    public DataType getDataType() {
        return DataType.UNDEFINED;
    }
}
