package at.davideko.perdia.queries.data;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;

public class StringDataEntry extends DataEntry {

    public StringDataEntry() {
        super();
    }

    public StringDataEntry(String value) {
        write(value);
    }

    public DataType getDataType() {
        return DataType.STRING;
    }
}
