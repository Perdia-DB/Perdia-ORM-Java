package at.davideko.perdia.queries;

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
