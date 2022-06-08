package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling the creation, querying, deletion and saving of templates
 */
public class Template {
    /**
     * The name of the template
     */
    public String name;

    /**
     * Hashmap for the data that the template contains
     */
    public HashMap<String, DataEntry> data = new HashMap<>();

    /**
     * One of the constructors for the Template class. This constructor creates a new empty template with only the
     * name given.
     * @param type The type/name of the template
     */
    public Template(String name) {
        this.name = name;
    }

    /**
     * One of the constructors for the Template class. This constructor not only creates a new template with the
     * given name, but also adds all the entries in the given Hashmap as entries of the template.
     * @param name The name of the template
     * @param entries Hashmap of entries that will be added to the preset
     */
    public Template(String name, HashMap<String, DataEntry> entries) {
        this.name = name;
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
    public String toCreationQuery() {
        StringBuilder r = new StringBuilder();

        r.append("TEMPLATE \"" + this.name + "\"; \n");

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

        r.append("END \"" + this.name + "\"; \n");

        return r.toString();
    }

    /**
     * Returns a PANG query for querying the respective template in the database
     * @return String containing PANG query for querying the respective template
     */
    public String toQuery() {
        return "QUERY \"" + this.name + "\" FROM TEMPLATE;";
    }

    /**
     * Returns a PANG query for querying the given template based on its name in the database
     * @return String containing PANG query for querying the given template based on its name
     */
    public static String toQuery(String type) {
        return "QUERY \"" + type + "\" FROM TEMPLATE;";
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
     * Returns the template name as a String.
     * @return The template name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a hashmap containing all the entries of the template.
     * @return Hashmap containing all the entries
     */
    public HashMap<String, DataEntry> getData() {
        return this.data;
    }

    /**
     * Returns a PANG query for deleting the respective template in the database, automatically deleting all instances
     * utilising the deleted template at the same time.
     * @return String containing a PANG query for deleting the respective template (and its instances)
     */
    public String deleteQuery() {
        return "DELETE \"" + this.name + "\" FROM TEMPLATE; \n";
    }
}
