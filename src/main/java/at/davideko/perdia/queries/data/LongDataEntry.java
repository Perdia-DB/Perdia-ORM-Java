package at.davideko.perdia.queries.data;

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
