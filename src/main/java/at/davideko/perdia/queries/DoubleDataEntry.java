package at.davideko.perdia.queries;

/**
 * Class extending from the abstract DataEntry class which stores Doubles respectively.
 */
public class DoubleDataEntry extends DataEntry {

    /**
     * {@link DataEntry#DataEntry()}
     */
    public DoubleDataEntry() {
        super();
    }

    /**
     * {@link DataEntry#write(Object)}
     */
    public DoubleDataEntry(Double value) {
        write(value);
    }

    /**
     * {@link DataEntry#getDataType()}
     */
    @Override
    public DataType getDataType() {
        return DataType.FLOAT;
    }
}
