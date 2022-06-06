package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public void addEntry(String name, DataType dt, Object starting) {
        DataEntry entry = null;

        switch (dt) {
            case STRING -> entry = new StringDataEntry();
            case INTEGER -> entry = new LongDataEntry();
            case FLOAT -> entry = new DoubleDataEntry();
        }

        entry.write(starting);

        data.put(name, entry);
    }

    public String toQuery() {
        StringBuilder r = new StringBuilder();

        r.append("TEMPLATE \"" + this.type + "\"; \n");

        for (Map.Entry<String, DataEntry> set: this.data.entrySet()) {
            switch (set.getValue().getDataType()) {
                case STRING -> {
                    r.append("STRING \"" + set.getKey() + "\" VALUE \"" + set.getValue().value + "\"; \n");
                }
                case INTEGER -> {
                    r.append("INTEGER \"" + set.getKey() + "\" VALUE " + set.getValue().value + "; \n");
                }
                case FLOAT -> {
                    r.append("FLOAT \"" + set.getKey() + "\" VALUE " + set.getValue().value + "; \n");
                }
            }
        }

        r.append("END \"" + this.type + "\"; \n");

        return r.toString();
    }

    public static String queryAll() {
        return "QUERY TEMPLATE;";
    }

    public void toPreset(String filename) {
        try {
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();

            if (!Files.exists(Path.of(helper + "/presets"))) {
                new File( helper + "/presets").mkdir();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("presets/" + filename + ".pang"), StandardCharsets.UTF_8));
            writer.write(toQuery());
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Error, could not write preset to file: " + e.getMessage());
        }
    }

    public static byte[] readPreset(String filename) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Path.of("presets/" + filename + ".pang"));


        } catch (IOException e) {
            System.out.println("IO Error, could not read preset from file: " + e.getMessage());
        }

        return bytes;
    }

    public String getType() {
        return this.type;
    }

    public HashMap<String, DataEntry> getData() {
        return this.data;
    }

    public String deleteQuery() {
        return "DELETE \"" + this.type + "\" FROM TEMPLATE; \n";
    }
}
