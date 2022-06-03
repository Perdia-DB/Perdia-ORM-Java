package at.davideko.perdia.queries.data;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;

public class LongDataEntry extends DataEntry {

    public LongDataEntry() {
        super();
    }

    public LongDataEntry(Long value) {
        write(value);
    }

    public DataType getDataType() {
        return DataType.INTEGER;
    }
}
