package at.davideko.perdia.queries.parsing;

import at.davideko.perdia.queries.Instance;
import at.davideko.perdia.queries.Template;
import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DoubleDataEntry;
import at.davideko.perdia.queries.data.LongDataEntry;
import at.davideko.perdia.queries.data.StringDataEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static at.davideko.perdia.queries.storage.AllTemplates.allTemplates;

/**
 * Class for parsing instance queries in JSON format from the database to Instance objects
 */
public class InstanceParser {
    /**
     * JSON array containing all the instance JSON objects to be parsed
     */
    private final JSONArray instanceArray;

    /**
     * Constructor in which the byte array turns in to a JSON array which then gets processed further
     * @param bytes Byte array containing the single characters of the JSON objects to be parsed encoded in UTF-8
     */
    public InstanceParser(byte[] bytes) {
        JSONArray arr = new JSONArray(new String(bytes, StandardCharsets.UTF_8));
        this.instanceArray = arr.getJSONArray(0);
    }

    /**
     * Parses the first JSON object present in the JSON array to an Instance object
     * @return The parsed Instance object
     */
    public Instance parseSingle() {
        JSONObject obj = instanceArray.getJSONObject(0);

        return parse(obj);
    }

    /**
     * Parses the JSON object at the given index in the JSON array to an Instance object
     * @param index The index of the JSON object to be parsed
     * @return The parsed Instance object
     */
    public Instance parseSingle(int index) {
        JSONObject obj = instanceArray.getJSONObject(index);

        return parse(obj);
    }

    /**
     * Parses all the JSON objects in the JSON array to Instance objects in an ArrayList
     * @return ArrayList containing all the parsed Instance objects
     */
    public ArrayList<Instance> parseMultiple() {
        ArrayList<Instance> r = new ArrayList<>();

        for (int i = 0; i < instanceArray.length(); i++) {
            JSONObject obj = instanceArray.getJSONObject(i);

            r.add(parse(obj));
        }

        return r;
    }

    /**
     * Internal method which actually does all the parsing, all the other methods just determine what and how much
     * gets parsed
     * @param obj The JSON object to be parsed to an Instance object
     * @return The parsed Instance object
     */
    private Instance parse(JSONObject obj) {
        JSONObject inst = obj.getJSONObject("Instance");
        Instance query = new Instance("temp");

        // Matches the name of the template to all existing Template objects to get the corresponding one
        query.setTemplate(allTemplates.stream()
                .filter(allTemplates -> inst.getString("template").equals(allTemplates.getName()))
                .findAny()
                .orElse(null));

        query.setName(inst.getString("name"));

        JSONObject data = inst.getJSONObject("data");
        Iterator<String> keys = data.keys();
        while(keys.hasNext()) {
            String currentDynamicKey = keys.next();
            Object currentDynamicValue = data.get(currentDynamicKey);

            DataEntry buffer;
            if (currentDynamicValue instanceof String) {
                buffer = new StringDataEntry();
            } else if (currentDynamicValue instanceof Long || currentDynamicValue instanceof Integer || currentDynamicValue instanceof BigInteger) {
                buffer = new LongDataEntry();
            } else if (currentDynamicValue instanceof Double || currentDynamicValue instanceof Float || currentDynamicValue instanceof BigDecimal) {
                buffer = new DoubleDataEntry();
            } else {
                throw new AssertionError("Value not accepted");
            }

            buffer.write(currentDynamicValue);
            query.setData(currentDynamicKey, buffer);
        }

        return query;
    }

    /**
     * Returns the amount of JSON objects in the JSON array
     * @return Amount of JSON objects in the JSON array
     */
    public int instanceAmount() {
        return this.instanceArray.length();
    }

    /**
     * Returns the JSON array as a String (with indentation)
     * @return JSON array as a String
     */
    public String toString() {
        return this.instanceArray.toString(3);
    }
}

