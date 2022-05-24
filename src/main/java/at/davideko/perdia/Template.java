package at.davideko.perdia;

import java.util.HashMap;
import java.util.Map;

public class Template {
    public String type;
    HashMap<String, String> data = new HashMap<String, String>();

    public Template(String type) {
        this.type = type;
    }

    public Template(String type, HashMap<String, String> entries) {
        this.type = type;
        this.data = entries;
    }

    public void addEntry(String name) {
        data.put(name, "");
    }

    public void addEntry(String name, String starting) {
        data.put(name, starting);
    }

    public String toQuery() {
        StringBuilder r = new StringBuilder();

        r.append("TYPE \"" + this.type + "\"; \n");

        for (Map.Entry<String, String> set: this.data.entrySet()) {
            if (set.getValue().equals("")) {
                r.append("NAME \"" + set.getKey() + "\" TYPE STRING");
                // TODO: fix the data Hashmap to support int, float and string aswell as finishing all of this
            } else {

            }
        }

        return r.toString();
    }
}
