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

import static at.davideko.perdia.Main.allTemplates;

/**
 * Class for parsing instance queries in JSON format from the database to Instance objects
 */
public class InstanceParser {
    /**
     * JSON array containing all the instance JSON objects to be parsed
     */
    private final JSONArray instanceArray;

    /**
     * Constructor in which the byte array turns in to a JSON array which then gets processes further
     * @param bytes Byte array containing the single characters of the JSON objects to be parsed encoded in UTF-8
     */
    public InstanceParser(byte[] bytes) {
        JSONArray arr = new JSONArray(new String(bytes, StandardCharsets.UTF_8));
        this.instanceArray = arr.getJSONArray(0);
    }

    /**
     *
     * @return
     */
    public Instance parseSingle() {
        JSONObject obj = instanceArray.getJSONObject(0);

        return parse(obj);
    }

    /**
     *
     * @param index
     * @return
     */
    public Instance parseSingle(int index) {
        JSONObject obj = instanceArray.getJSONObject(index);

        return parse(obj);
    }

    public ArrayList<Instance> parseMultiple() {
        ArrayList<Instance> r = new ArrayList<>();

        for (int i = 0; i < instanceArray.length(); i++) {
            JSONObject obj = instanceArray.getJSONObject(i);

            r.add(parse(obj));
        }

        return r;
    }

    private Instance parse(JSONObject obj) {
        JSONObject inst = obj.getJSONObject("Instance");
        Instance query = new Instance("temp");

        query.tmp = allTemplates.stream()
                .filter(allTemplates -> inst.getString("template").equals(allTemplates.getName()))
                .findAny()
                .orElse(null);

        query.name = inst.getString("name");

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
            query.data.put(currentDynamicKey, buffer);
        }

        return query;
    }

    public int instanceAmount() {
        return this.instanceArray.length();
    }
}

