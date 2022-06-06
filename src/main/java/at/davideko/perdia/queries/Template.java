package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling the creation, querying and saving of templates
 */
public class Template {
    /**
     * The type/name of the template
     */
    public String type;

    /**
     * Hashmap for the data that the template contains
     */
    HashMap<String, DataEntry> data = new HashMap<>();

    /**
     * One of the constructors for the Template class. This constructor creates a new empty template with only the
     * name given.
     * @param type The type/name of the template
     */
    public Template(String type) {
        this.type = type;
    }

    /**
     * One of the constructors for the Template class. This constructor not only creates a new template with the
     * given name, but also adds all the entries in the given Hashmap as entries of the template.
     * @param type The type/name of the template
     * @param entries Hashmap of entries that will be added to the preset
     */
    public Template(String type, HashMap<String, DataEntry> entries) {
        this.type = type;
        this.data = entries;
    }

    /**
     * Adds an entry with the given name, datatype and starting value to the template.
     * @param name Name of the entry
     * @param dt Datatype of the entry
     * @param starting Starting value of the entry
     */
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

    /**
     * Turns the current Template object in to a PANG query which creates the template in the database
     * @return String containing a PANG query for creating the template
     */
    public String toQuery() {
        StringBuilder r = new StringBuilder();

        r.append("TEMPLATE \"" + this.type + "\"; \n");

        for (Map.Entry<String, DataEntry> set: this.data.entrySet()) {
            switch (set.getValue().getDataType()) {
                case STRING -> {
                    r.append("STRING \"" + set.getKey() + "\" VALUE \"" + set.getValue().read() + "\"; \n");
                }
                case INTEGER -> {
                    r.append("INTEGER \"" + set.getKey() + "\" VALUE " + set.getValue().read() + "; \n");
                }
                case FLOAT -> {
                    r.append("FLOAT \"" + set.getKey() + "\" VALUE " + set.getValue().read() + "; \n");
                }
            }
        }

        r.append("END \"" + this.type + "\"; \n");

        return r.toString();
    }

    /**
     * Returns a PANG query for querying all currently existing templates in the database
     * @return String containing a PANG query for querying all existing templates
     */
    public static String queryAll() {
        return "QUERY TEMPLATE;";
    }

    /**
     * Saves a .pang File to the /presets folder which contains the current Template object as a PANG query encoded
     * in UTF-8. This preset file can then be read by the readPreset method.
     * @param filename Name of the preset file to be saved to (not including the .pang file ending)
     */
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

    /**
     * Reads a .pang File from the /presets folder and returns the single characters of the template as a PANG query
     * encoded in UTF-8
     * @param filename Name of the preset file to be read from (not including the .pang file ending)
     * @return Byte array containing the single characters of the query text encoded in UTF-8
     */
    public static byte[] readPreset(String filename) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Path.of("presets/" + filename + ".pang"));


        } catch (IOException e) {
            System.out.println("IO Error, could not read preset from file: " + e.getMessage());
        }

        return bytes;
    }

    /**
     * Returns the template type/name as a String.
     * @return The template type/name
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns a hashmap containing all the entries of the template.
     * @return Hashmap containing all the entries
     */
    public HashMap<String, DataEntry> getData() {
        return this.data;
    }

    /**
     * Returns a PANG query for deleting the respective Template object in the database
     * @return String containing a PANG query for deleting the respective template
     */
    public String deleteQuery() {
        return "DELETE \"" + this.type + "\" FROM TEMPLATE; \n";
    }
}
