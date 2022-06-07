package at.davideko.perdia.queries.storage;

import at.davideko.perdia.queries.Instance;
import at.davideko.perdia.queries.Template;

import java.util.ArrayList;

/**
 * Class for storing all existing instances in the database on the ORM-side.
 */
public class AllInstances {
    public static ArrayList<Instance> allInstances = new ArrayList<>();

    public static void add(Instance inst) {
        allInstances.add(inst);
    }

    public static ArrayList<Instance> get() {
        return allInstances;
    }
}
