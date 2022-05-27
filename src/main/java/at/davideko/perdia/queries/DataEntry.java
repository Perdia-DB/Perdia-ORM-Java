package at.davideko.perdia.queries;

public class DataEntry {
    DataType type = null;
    String stringBuffer = "";
    long intBuffer = 0;
    double floatBuffer = 0.0;

    public DataEntry(DataType type) {
        this.type = type;
    }

    public void write(Object value) {
        switch (this.type) {
            case STRING -> stringBuffer = (String) value;
            case INTEGER -> intBuffer = (long) value;
            case FLOAT -> floatBuffer = (double) value;
        }
    }

    public Object read() {
        switch (this.type) {
            case STRING -> {
                return stringBuffer;
            }
            case INTEGER -> {
                return intBuffer;
            }
            case FLOAT -> {
                return floatBuffer;
            }
        }

        return null;
    }

    public String toString() {
        return read().toString();
    }
}


