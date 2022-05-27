package at.davideko.perdia.queries;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Template {
    public String type;
    HashMap<String, DataEntry> data = new HashMap<>();

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

    public void addEntry(String name, DataType dt, Object starting) {
        DataEntry entry = new DataEntry(dt);
        entry.write(starting);
        data.put(name, entry);
    }

    public String toQuery() {
        StringBuilder r = new StringBuilder();

        r.append("TYPE \"" + this.type + "\"; \n");

        for (Map.Entry<String, DataEntry> set: this.data.entrySet()) {
            switch (set.getValue().type) {
                case STRING -> {
                    if (set.getValue().stringBuffer.equals("")) {
                        r.append("NAME \"" + set.getKey() + "\" TYPE STRING; \n");
                    } else {
                        r.append("NAME \"" + set.getKey() + "\" TYPE STRING STARTING \"" + set.getValue().stringBuffer + "\"; \n");
                    }
                }
                case INTEGER -> {
                    if (set.getValue().intBuffer == 0) {
                        r.append("NAME \"" + set.getKey() + "\" TYPE INTEGER; \n");
                    } else {
                        r.append("NAME \"" + set.getKey() + "\" TYPE INTEGER STARTING " + set.getValue().intBuffer + "; \n");
                    }
                }
                case FLOAT -> {
                    if (set.getValue().floatBuffer == 0f) {
                        r.append("NAME \"" + set.getKey() + "\" TYPE FLOAT; \n");
                    } else {
                        r.append("NAME \"" + set.getKey() + "\" TYPE FLOAT STARTING " + set.getValue().floatBuffer + "; \n");
                    }
                }
            }
        }

        r.append("END; \n");

        return r.toString();
    }

    public void toPreset(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("presets/" + filename + ".pang"));
            writer.write(toQuery());
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Error, could not write preset to file: " + e.getMessage());
        }
    }

    // TODO: make readPreset work
    public void readPreset(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("presets/" + filename + ".pang"));
            reader.read();

            reader.close();
        } catch (IOException e) {
            System.out.println("IO Error, could not read preset from file: " + e.getMessage());
        }
    }

    public String toString() {
        return this.type;
    }
}
