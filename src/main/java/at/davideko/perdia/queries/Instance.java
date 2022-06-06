package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.DataEntry;

import java.util.HashMap;
import java.util.Map;

import static at.davideko.perdia.Main.allTemplates;

public class Instance {
    /**
     * The name of the instance
     */
    String name = null;

    /**
     * The template the instance is utilising
     */
    Template tmp = null;

    /**
     * Hashmap for the data that the instance contains
     */
    HashMap<String, DataEntry> data = new HashMap<>();

    /**
     * One of the constructors for the Instance class. This constructor creates a new instance with only the name
     * defined. An empty instance created by this constructor has to be fully initialized by using either the
     * createInstance or copyInstance methods later on.
     * @param name The name of the instance
     */
    public Instance(String name) {
        this.name = name;
    }

    public Instance(String name, Template tmp) {
        this.name = name;
        this.tmp = tmp;
    }

    public Instance(String name, Instance qo) {
        this.name = name;
        this.tmp = qo.tmp;
        this.data = qo.data;
    }

    public Instance(JsonParser jp) {
        readJSON(jp);
    }

    public String creatInstance(Template tmp) {
        this.tmp = tmp;
        return "CREATE \"" + this.name + "\" TEMPLATE \"" + this.tmp.getType() + "\"; \n";
    }

    public String copyInstance(Instance qo) {
        this.tmp = qo.getTemplate();
        this.data = qo.getData();
        return "CREATE \"" + this.name + "\" INSTANCE \"" + qo.getName() + "\"; \n";
    }

    public String writeToQueryObject(HashMap<String, DataEntry> hm) {
        StringBuilder r = new StringBuilder();

        r.append("SELECT \"" + this.name + "\"; \n");

        for (Map.Entry<String, DataEntry> addSet: hm.entrySet()) {
            for (Map.Entry<String, DataEntry> existingSet: this.tmp.data.entrySet()) {
                if (addSet.getKey().equals(existingSet.getKey())) {

                    existingSet.getValue().write(addSet.getValue().read());

                    r.append("SET \"" + existingSet.getKey() + "\" VALUE " + addSet.getValue().read() + "; \n");

                    this.data.put(existingSet.getKey(), addSet.getValue());
                }
            }
        }

        r.append("END \"" + this.name + "\"; \n");

        return r.toString();
    }

    public String toQuery() {
        return "QUERY \"" + this.name + "\" FROM INSTANCE;";
    }

    /**
     * Returns a PANG query for querying all currently existing instances in the database
     * @return String containing a PANG query for querying all existing instances
     */
    public static String queryAll() {
        return "QUERY INSTANCE;";
    }

    public void readJSON(JsonParser jp) {
        this.name = jp.getInstance();
        this.tmp = allTemplates.stream()
                .filter(allTemplates -> jp.getTemplate().equals(allTemplates.type))
                .findAny()
                .orElse(null);

        writeToQueryObject(jp.getData());
    }

    public String getName() {
        return this.name;
    }

    public Template getTemplate() {
        return this.tmp;
    }

    public HashMap<String, DataEntry> getData() {
        return this.data;
    }

    /**
     * Returns a PANG query for deleting the respective instance in the database.
     * @return String containing a PANG query for deleting the respective instance
     */
    public String deleteQuery() {
        return "DELETE \"" + this.name + "\" FROM INSTANCE; \n";
    }
}
