package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.DataEntry;

import java.util.HashMap;
import java.util.Map;

import static at.davideko.perdia.Main.allTemplates;

public class QueryObject {
    String instance = null;
    Template tmp = null;
    HashMap<String, DataEntry> data = new HashMap<>();

    public QueryObject(String instance) {
        this.instance = instance;
    }

    public QueryObject(String instance, Template tmp) {
        this.instance = instance;
        this.tmp = tmp;
    }

    public QueryObject(String instance, QueryObject qo) {
        this.instance = instance;
        this.tmp = qo.tmp;
        this.data = qo.data;
    }

    public QueryObject(JsonParser jp) {
        readJSON(jp);
    }

    public String createQueryObject(Template tmp) {
        this.tmp = tmp;
        return "CREATE \"" + this.instance + "\" TEMPLATE \"" + this.tmp.getType() + "\"; \n";
    }

    public String copyQueryObject(QueryObject qo) {
        this.tmp = qo.getTemplate();
        this.data = qo.getData();
        return "CREATE \"" + this.instance + "\" INSTANCE \"" + qo.getInstance() + "\"; \n";
    }

    public String writeToQueryObject(HashMap<String, DataEntry> hm) {
        StringBuilder r = new StringBuilder();

        r.append("SELECT \"" + this.instance + "\"; \n");

        for (Map.Entry<String, DataEntry> addSet: hm.entrySet()) {
            for (Map.Entry<String, DataEntry> existingSet: this.tmp.data.entrySet()) {
                if (addSet.getKey().equals(existingSet.getKey())) {

                    existingSet.getValue().write(addSet.getValue().read());

                    r.append("SET \"" + existingSet.getKey() + "\" VALUE " + addSet.getValue().read() + "; \n");

                    this.data.put(existingSet.getKey(), addSet.getValue());
                }
            }
        }

        r.append("END \"" + this.instance + "\"; \n");

        return r.toString();
    }

    public String toQuery() {
        return "QUERY \"" + this.instance + "\" FROM INSTANCE;";
    }

    public static String queryAll() {
        return "QUERY INSTANCE;";
    }

    public void readJSON(JsonParser jp) {
        this.instance = jp.getInstance();
        this.tmp = allTemplates.stream()
                .filter(allTemplates -> jp.getTemplate().equals(allTemplates.type))
                .findAny()
                .orElse(null);

        writeToQueryObject(jp.getData());
    }

    public String getInstance() {
        return this.instance;
    }

    public Template getTemplate() {
        return this.tmp;
    }

    public HashMap<String, DataEntry> getData() {
        return this.data;
    }

    public String deleteQuery() {
        return "DELETE \"" + this.instance + "\" FROM INSTANCE; \n";
    }
}
