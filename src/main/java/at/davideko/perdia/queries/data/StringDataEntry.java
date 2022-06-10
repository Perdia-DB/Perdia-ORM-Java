package at.davideko.perdia.queries.data;

/**
 * Class extending from the abstract DataEntry class which stores Strings respectively.
 */
public class StringDataEntry extends DataEntry {

    /**
     * {@link DataEntry#DataEntry()}
     */
    public StringDataEntry() {
        super();
    }

    /**
     * {@link DataEntry#write(Object)}
     * @param value String to be written to the DataEntry
     */
    public StringDataEntry(String value) {
        write(value);
    }

    /**
     * {@link DataEntry#getDataType()}
     */
    @Override
    public DataType getDataType() {
        return DataType.STRING;
    }
}
