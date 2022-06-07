package at.davideko.perdia.queries.data;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;

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
