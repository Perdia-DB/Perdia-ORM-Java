package at.davideko.perdia.queries.data;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;

public class DoubleDataEntry extends DataEntry {

    public DoubleDataEntry() {
        super();
    }

    public DoubleDataEntry(Double value) {
        write(value);
    }

    public DataType getDataType() {
        return DataType.FLOAT;
    }
}
