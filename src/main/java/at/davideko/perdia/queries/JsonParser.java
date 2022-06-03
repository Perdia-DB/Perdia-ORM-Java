package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DoubleDataEntry;
import at.davideko.perdia.queries.data.LongDataEntry;
import at.davideko.perdia.queries.data.StringDataEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

public class JsonParser {
    protected byte[] b;
    public Query query = new Query();

    public JsonParser(byte[] bytes) {
        this.b = bytes;

        String s = new String(b, StandardCharsets.UTF_8);
        JSONArray arr = new JSONArray(s);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            this.query.template = obj.getString("template");
            this.query.instance = obj.getString("instance");

            JSONObject data = obj.getJSONObject("data");
            Iterator keys = data.keys();
            while(keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                Object currentDynamicValue = data.get(currentDynamicKey);

                DataEntry buffer = null;
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

    public static class Query {
        public String template;

        public String instance;

        public HashMap<String, DataEntry> data = new HashMap<>();
    }

     public String getTemplate() {
        return this.query.template;
     }

     public String getInstance() {
        return this.query.instance;
     }

     public HashMap<String, DataEntry> getData() {
        return this.query.data;
     }
}

