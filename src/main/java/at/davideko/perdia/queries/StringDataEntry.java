package at.davideko.perdia.queries;

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
