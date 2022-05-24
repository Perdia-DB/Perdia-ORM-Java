package at.davideko.perdia;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class JsonParser {
    protected ObjectMapper mapper = new ObjectMapper();
    protected byte[] b;
    Query[] query = null;

    public JsonParser(byte[] bytes) {
        this.b = bytes;

        try {
            query = mapper.readValue(b, Query[].class);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    public static class Query {
        @JsonProperty("template")
        public String name;

        @JsonProperty("instance")
        public String instance;

        @JsonProperty("data")
        public QueryData data;
    }

    public static class QueryData {
        @JsonProperty("First") public String first;
        @JsonProperty("Second") public String second;

        HashMap<String, String> data = new HashMap<String, String>();
    }
}