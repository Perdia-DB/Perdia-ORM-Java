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
 * Class for parsing template queries in JSON format from the database to Template objects
 */
public class TemplateParser {
    /**
     * Byte array containing all the single characters of the original queried JSON object encoded in UTF-8
     */
    private final JSONArray templateArray;

    /**
     * Constructor in which the parsing takes place
     * @param bytes Byte array containing the single characters of the JSON object to be parsed encoded in UTF-8
     */
    public TemplateParser(byte[] bytes) {
        JSONArray arr = new JSONArray(new String(bytes, StandardCharsets.UTF_8));
        this.templateArray = arr.getJSONArray(0);
    }

    public Template parseSingle() {
        JSONObject obj = templateArray.getJSONObject(0);

        return parse(obj);
    }

    public Template parseSingle(int index) {
        JSONObject obj = templateArray.getJSONObject(index);

        return parse(obj);
    }

    public ArrayList<Template> parseMultiple() {
        ArrayList<Template> r = new ArrayList<>();

        for (int i = 0; i < templateArray.length(); i++) {
            JSONObject obj = templateArray.getJSONObject(i);

            r.add(parse(obj));
        }

        return r;
    }

    private Template parse(JSONObject obj) {
        JSONObject inst = obj.getJSONObject("Template");
        Template query = new Template("temp");

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

    public int templateAmount() {
        return this.templateArray.length();
    }
}

