package at.davideko.perdia.queries.data;

public abstract class DataEntry {
    public Object value;
    public DataType dt = DataType.UNDEFINED;

    public DataEntry() {}

    public void write(Object value) {
        this.value = value;
    }

    public Object read() {
        return this.value;
    }

    public DataType getDataType() {
        return dt;
    }
}
