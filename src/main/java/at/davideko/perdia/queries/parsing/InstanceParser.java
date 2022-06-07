package at.davideko.perdia.queries.parsing;

import at.davideko.perdia.queries.Instance;
import at.davideko.perdia.queries.Template;
import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DoubleDataEntry;
import at.davideko.perdia.queries.data.LongDataEntry;
import at.davideko.perdia.queries.data.StringDataEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

import static at.davideko.perdia.Main.allTemplates;

/**
 * Class for parsing instance queries in JSON format from the database to Instance objects
 */
public class InstanceParser {
    /**
     * Byte array containing all the single characters of the original queried JSON object encoded in UTF-8
     */
    protected byte[] b;

    /**
     * Instance object the parsed information will be written to
     */
    public Instance query = new Instance("temp");

    /**
     * Constructor in which the parsing takes place
     * @param bytes Byte array containing the single characters of the JSON object to be parsed encoded in UTF-8
     */
    public InstanceParser(byte[] bytes) {
        this.b = bytes;

        String s = new String(b, StandardCharsets.UTF_8);
        JSONArray arr = new JSONArray(s);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            this.query.tmp = allTemplates.stream()
                    .filter(allTemplates -> obj.getString("template").equals(allTemplates.type))
                    .findAny()
                    .orElse(null);

            this.query.name = obj.getString("instance");

            JSONObject data = obj.getJSONObject("data");
            Iterator<String> keys = data.keys();
            while(keys.hasNext()) {
                String currentDynamicKey = keys.next();
                Object currentDynamicValue = data.get(currentDynamicKey);

                DataEntry buffer;
                if (currentDynamicValue instanceof String) {
                    buffer = new StringDataEntry();
                } else if (currentDynamicValue instanceof Long || currentDynamicValue instanceof Integer) {
                    buffer = new LongDataEntry();
                } else if (currentDynamicValue instanceof Double || currentDynamicValue instanceof Float) {
                    buffer = new DoubleDataEntry();
                } else {
                    throw new AssertionError("Value not accepted");
                }

                buffer.write(currentDynamicValue);
                this.query.data.put(currentDynamicKey, buffer);
            }
        }
    }

    /**
     * Returns the parsed Instance object
     * @return The parsed Instance object
     */
    public Instance getInstance() {
        return this.query;
    }
}

