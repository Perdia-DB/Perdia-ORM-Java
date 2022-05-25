package at.davideko.perdia.queries;

import java.util.HashMap;
import java.util.Map;

public class Template {
    public String type;
    HashMap<String, DataEntry> data = new HashMap<String, DataEntry>();

    public Template(String type) {
        this.type = type;
    }

    public Template(String type, HashMap<String, DataEntry> entries) {
        this.type = type;
        this.data = entries;
    }

    public void addEntry(String name, DataType dt) {
        DataEntry entry = new DataEntry(dt);
        data.put(name, entry);
    }

    public void addEntry(String name, DataType dt, QueryObject starting) {
        DataEntry entry = new DataEntry(dt);
        entry.write(starting);
        data.put(name, entry);
    }

    public String toString() {
        StringBuilder r = new StringBuilder();

        r.append("TYPE \"" + this.type + "\"; \n");

        for (Map.Entry<String, DataEntry> set: this.data.entrySet()) {
            switch (set.getValue().type) {
                case STRING -> {
                    if (set.getValue().stringBuffer.equals("")) {
                        r.append("NAME \"" + set.getKey() + "\" TYPE STRING;");
                    } else {
                        r.append("NAME \"" + set.getKey() + "\" TYPE STRING STARTING \"" + set.getValue().stringBuffer + "\";");
                    }
                }
                case INTEGER -> {
                    if (set.getValue().intBuffer == 0) {
                        r.append("NAME \"" + set.getKey() + "\" TYPE INTEGER;");
                    } else {
                        r.append("NAME \"" + set.getKey() + "\" TYPE INTEGER STARTING " + set.getValue().intBuffer + ";");
                    }
                }
                case FLOAT -> {
                    if (set.getValue().floatBuffer == 0f) {
                        r.append("NAME \"" + set.getKey() + "\" TYPE FLOAT;");
                    } else {
                        r.append("NAME \"" + set.getKey() + "\" TYPE FLOAT STARTING " + set.getValue().floatBuffer + ";");
                    }
                }
            }
        }

        return r.toString();
    }

    // TODO: add "toPreset" function which writes the toString output to a JSON file in src/main/presets
}
