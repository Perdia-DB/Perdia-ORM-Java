package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.parsing.InstanceParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling the creation, editing, querying, deletion and saving of instances
 */
public class Instance {
    /**
     * The name of the instance
     */
    public String name = null;

    /**
     * The template the instance is utilising
     */
    public Template tmp = null;

    /**
     * Hashmap for the data that the instance contains
     */
    public HashMap<String, DataEntry> data = new HashMap<>();

    /**
     * One of the constructors for the Instance class. This constructor creates a new instance with only the name
     * defined. An empty instance created by this constructor has to be fully initialized by using either the
     * createInstance or copyInstance methods later on.
     * @param name The name of the instance
     */
    public Instance(String name) {
        this.name = name;
    }

    /**
     * One of the constructors for the Instance class. This constructor creates a new instance with a predefined
     * template. The instance now has all the entries the template has and is ready to be read from or written to.
     * This constructor essentially does the same as creating an empty instance with the other constructor which only
     * requires a name and then also runs the createInstance method afterwards as well.
     * @param name The name of the instance
     * @param tmp The template the instance is utilising
     */
    public Instance(String name, Template tmp) {
        this.name = name;
        this.tmp = tmp;
    }

    /**
     * One of the constructors for the Instance class. This constructor creates a new instance based on an already
     * existing instance, copying it. The instance now has all the entries with their corresponding values and is ready
     * to be read from or written to. This constructor essentially does the same as creating an empty instance with the
     * other constructor which only requires a name and then also runs the copyInstance method afterwards as well.
     * @param name The name of the instance
     * @param qo The instance to be copied from
     */
    public Instance(String name, Instance qo) {
        this.name = name;
        this.tmp = qo.tmp;
        this.data = qo.data;
    }

    /**
     * Creates an instance in the database using a template. It returns a PANG query for creating an
     * instance using a template.
     * @param tmp The template to be used for the instance
     * @return String containing PANG query for creating an instance using a template
     */
    public String createInstance(Template tmp) {
        this.tmp = tmp;
        return "CREATE \"" + this.name + "\" TEMPLATE \"" + this.tmp.getName() + "\"; \n";
    }

    /**
     * Creates an instance in the database using an already existing instance. It returns a PANG query for creating
     * an instance using another instance, essentially copying it.
     * @param qo The instance to be copied from
     * @return String containing PANG query for creating an instance using another instance
     */
    public String copyInstance(Instance qo) {
        this.tmp = qo.getTemplate();
        this.data = qo.getData();
        return "CREATE \"" + this.name + "\" INSTANCE \"" + qo.getName() + "\"; \n";
    }

    /**
     * Method used for writing values to the entries of an instance using a hashmap. The hashmap gets checked for
     * corresponding entries and the value assigned to them, replacing the value of the entry currently stored in
     * the Instance object with the value from the given hashmap. It returns a PANG query for setting all the
     * values that have been changed in the Instance object by this method to what they actually are.
     * @param hm Hashmap the values are supposed to be copied form
     * @return String containing PANG query setting all the changed values
     */
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

    /**
     * Returns a PANG query for querying the respective instance in the database
     * @return String containing PANG query for querying the respective instance
     */
    public String toQuery() {
        return "QUERY \"" + this.name + "\" FROM INSTANCE;";
    }

    /**
     * Returns a PANG query for querying the given instance based on its name in the database
     * @return String containing PANG query for querying the given instance based on its name
     */
    public static String toQuery(String name) {
        return "QUERY \"" + name + "\" FROM INSTANCE;";
    }

    /**
     * Returns a PANG query for querying all currently existing instances in the database
     * @return String containing a PANG query for querying all existing instances
     */
    public static String queryAll() {
        return "QUERY INSTANCE;";
    }

    /**
     * Returns the name of the respective instance.
     * @return The name of the instance
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the template of the respective instance.
     * @return The template of the instance
     */
    public Template getTemplate() {
        return this.tmp;
    }

    /**
     * Returns the data entries of the respective instance all stored in a hashmap.
     * @return Hashmap containing all the data entries of the instance
     */
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
