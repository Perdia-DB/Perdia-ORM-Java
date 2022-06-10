package at.davideko.perdia.queries.storage;

import at.davideko.perdia.queries.Instance;
import at.davideko.perdia.queries.parsing.InstanceParser;
import at.davideko.perdia.tcp.TCPClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class for storing all existing instances in the database on the ORM-side.
 */
public class AllInstances {
    /**
     * ArrayList containing all the existing Instance objects.
     */
    private static ArrayList<Instance> allInstances = new ArrayList<>();

    /**
     * Method for adding an Instance object to the ArrayList containing all the Instance objects.
     * @param inst Instance object to be added to the allInstances ArrayList
     */
    public static void add(Instance inst) {
        allInstances.add(inst);
    }

    /**
     * Used for fetching all the existing instances in the database and writing them to the allInstances ArrayList
     * as Instance objects.
     * @param client TCPClient connected to the server running Perdia-DB
     */
    public static void fetch(TCPClient client) {
        client.write(Instance.queryAll().getBytes(StandardCharsets.UTF_8));

        InstanceParser ip = new InstanceParser(client.read());
        allInstances = ip.parseMultiple();
    }

    /**
     * Method for getting the ArrayList containing all the existing Instance objects.
     * @return ArrayList containing all the existing Instance objects
     */
    public static ArrayList<Instance> get() {
        return allInstances;
    }
}
