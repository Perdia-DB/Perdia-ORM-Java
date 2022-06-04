package at.davideko.perdia.queries;

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
