package at.davideko.perdia.queries;

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
