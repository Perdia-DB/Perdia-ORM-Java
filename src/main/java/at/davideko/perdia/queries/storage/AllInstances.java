package at.davideko.perdia.queries.storage;

import at.davideko.perdia.queries.Instance;
import at.davideko.perdia.queries.Template;
import at.davideko.perdia.queries.parsing.InstanceParser;
import at.davideko.perdia.queries.parsing.TemplateParser;
import at.davideko.perdia.tcp.TCPClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class for storing all existing instances in the database on the ORM-side.
 */
public class AllInstances {
    public static ArrayList<Instance> allInstances = new ArrayList<>();

    public static void add(Instance inst) {
        allInstances.add(inst);
    }

    public static void fetch(TCPClient client) {
        client.write(Instance.queryAll().getBytes(StandardCharsets.UTF_8));

        InstanceParser ip = new InstanceParser(client.read());
        allInstances = ip.parseMultiple();
    }

    public static ArrayList<Instance> get() {
        return allInstances;
    }
}
