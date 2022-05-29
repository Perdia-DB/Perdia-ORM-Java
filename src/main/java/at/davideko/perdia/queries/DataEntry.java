package at.davideko.perdia.queries;

public class DataEntry {
    DataType type = DataType.UNDEFINED;
    String stringBuffer = "";
    long intBuffer = 0;
    double floatBuffer = 0.0;

    public DataEntry(DataType type) {
        this.type = type;
    }

    public DataEntry(DataType type, Object value) {
        this.type = type;
        write(value);
    }

    /*
    public DataEntry(Object value) {
        write(value);
    }
    */

    public void write(Object value) {
        String converted = value.toString();
        /*
        if (value instanceof String) {
            stringBuffer = (String) value;
            this.type = DataType.STRING;
        } else if (value instanceof Long) {
            intBuffer = (long) value;
            this.type = DataType.INTEGER;
        } else if (value instanceof Double) {
            floatBuffer = (double) value;
            this.type = DataType.FLOAT;
        }
     */
        switch (this.type) {
            case STRING -> stringBuffer = converted;
            //case INTEGER -> intBuffer = (Long) value;
            case INTEGER -> intBuffer = Long.parseLong(converted);
            //case FLOAT -> floatBuffer = (Double) value;
            case FLOAT -> floatBuffer = Double.parseDouble(converted);
        }
    }

    public void read() {
        switch (this.type) {
            case STRING -> {
                getStringBuffer();
            }
            case INTEGER -> {
                getIntBuffer();
            }
            case FLOAT -> {
                getFloatBuffer();
            }
        }
    }

    public String getStringBuffer() {
        return stringBuffer;
    }

    public long getIntBuffer() {
        return intBuffer;
    }

    public double getFloatBuffer() {
        return floatBuffer;
    }

    public String toString() {
        return read().toString();
    }
}


