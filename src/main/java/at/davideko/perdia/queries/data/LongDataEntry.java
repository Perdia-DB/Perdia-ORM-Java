package at.davideko.perdia.queries.data;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;

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
