package at.davideko.perdia.queries.data;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;

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
