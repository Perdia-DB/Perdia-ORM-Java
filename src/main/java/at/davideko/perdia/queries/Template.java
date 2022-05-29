package at.davideko.perdia.queries;

import at.davideko.perdia.tcp.TCPClient;

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

    public void addEntry(String name, DataType dt) {
        DataEntry entry = new DataEntry(dt);
        data.put(name, entry);
    }

    public void addEntry(String name, DataType dt, Object starting) {
        DataEntry entry = new DataEntry(dt);
        //DataEntry entry = new DataEntry(starting);
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
                case UNDEFINED -> {
                    System.out.println("uh oh");
                }
            }
        }

        r.append("END; \n");

        return r.toString();
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

    public String toString() {
        return this.type;
    }
}
