package at.davideko.perdia.queries;

public class DataEntry {
    DataType type = null;
    String stringBuffer = "";
    int intBuffer = 0;
    float floatBuffer = 0f;

    public DataEntry(DataType type) {
        this.type = type;
    }

    public void write(Object value) {
        switch (this.type) {
            case STRING -> stringBuffer = (String) value;
            case INTEGER -> intBuffer = (int) value;
            case FLOAT -> floatBuffer = (float) value;
        }
    }
}


