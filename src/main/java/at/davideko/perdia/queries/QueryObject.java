package at.davideko.perdia.queries;

import java.util.HashMap;
import java.util.Map;

public class QueryObject {
    String name = null;
    Template tmp = null;

    public QueryObject(String name, Template tmp) {
        this.name = name;
        this.tmp = tmp;
    }

    public String createQueryObject() {
        return "CREATE \"" + this.name + "\" TYPE \"" + this.tmp + "\"; \n";
    }

    public String writeToQueryObject(HashMap<String, DataEntry> hm) {
        StringBuilder r = new StringBuilder();

        r.append("QUERY \"" + this.name + "\" THEN; \n");

        for (Map.Entry<String, DataEntry> addSet: hm.entrySet()) {
            for (Map.Entry<String, DataEntry> existingSet: this.tmp.data.entrySet()) {
                if (addSet.getKey().equals(existingSet.getKey())) {

                    existingSet.getValue().write(addSet.getValue().read());

                    r.append("SET \"" + existingSet.getKey() + "\" VALUE \"" + addSet.getValue() + "\"; \n");
                }
            }
        }

        r.append("END; \n");
        return r.toString();
    }

    public String toQuery() {
        return "QUERY \"" + this.name + "\";";
    }
}
