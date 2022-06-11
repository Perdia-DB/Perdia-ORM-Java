package com.github.perdia.queries.data;

/**
 * Class extending from the abstract DataEntry class which stores Longs respectively.
 */
public class LongDataEntry extends DataEntry {

    /**
     * {@link DataEntry#DataEntry()}
     */
    public LongDataEntry() {
        super();
    }

    /**
     * {@link DataEntry#write(Object)}
     * @param value Long to be written to the DataEntry
     */
    public LongDataEntry(Long value) {
        write(value);
    }

    /**
     * {@link DataEntry#getDataType()}
     */
    @Override
    public DataType getDataType() {
        return DataType.INTEGER;
    }
}
