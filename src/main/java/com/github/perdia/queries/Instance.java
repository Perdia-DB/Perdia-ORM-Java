package com.github.perdia.queries;

import com.github.perdia.queries.data.DataEntry;
import com.github.perdia.queries.data.DataType;
import com.github.perdia.queries.storage.AllInstances;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling the creation, editing, querying, deletion and saving of instances
 */
public class Instance {
    /**
     * The name of the instance
     */
    private String name;

    /**
     * The template the instance is utilising
     */
    private Template tmp = null;

    /**
     * Hashmap for the data that the instance contains
     */
    private HashMap<String, DataEntry> data = new HashMap<>();

    /**
     * One of the constructors for the Instance class. This constructor creates a new instance with only the name
     * defined. An empty instance created by this constructor has to be fully initialized by using either the
     * createInstance or copyInstance methods later on.
     * @param name The name of the instance
     */
    public Instance(String name) {
        this.name = name;

        AllInstances.add(this);
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
        this.data = tmp.getData();

        AllInstances.add(this);
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

        AllInstances.add(this);
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
     * the Instance object with the value from the given hashmap. If the given keys don't exist in the data entries of
     * the Instance, nothing changes. It returns a PANG query for setting all the values that have been changed in the
     * Instance object by this method to what they actually are.
     * @param hm Hashmap the values are supposed to be copied form
     * @return String containing PANG query setting all the changed values
     */
    public String setData(HashMap<String, DataEntry> hm) {
        StringBuilder r = new StringBuilder();

        r.append("SELECT \"" + this.name + "\"; \n");

        for (Map.Entry<String, DataEntry> addSet: hm.entrySet()) {
            for (Map.Entry<String, DataEntry> existingSet: this.tmp.getData().entrySet()) {
                if (addSet.getKey().equals(existingSet.getKey())) {

                    existingSet.getValue().write(addSet.getValue().read());

                    if (addSet.getValue().getDataType() == DataType.STRING) {
                        r.append("SET \"" + existingSet.getKey() + "\" VALUE \"" + addSet.getValue().read() + "\"; \n");
                    } else {
                        r.append("SET \"" + existingSet.getKey() + "\" VALUE " + addSet.getValue().read() + "; \n");
                    }

                    this.data.put(existingSet.getKey(), addSet.getValue());
                }
            }
        }

        r.append("END \"" + this.name + "\"; \n");

        return r.toString();
    }

    /**
     * Method used for writing a single value to an entry of an instance. The data entries get checked for the
     * corresponding key, replacing the value of the entry currently stored in the Instance object with the value from
     * the given DataEntry. If the given key doesn't exist in the data entries of the Instance, nothing changes. It
     * returns a PANG query for setting the value that has been changed in the Instance object by this method to what
     * it actually is.
     * @param key Key that is getting matched
     * @param de DataEntry to be written to the data entry with the corresponding key
     * @return String containing PANG query setting the changed value
     */
    public String setData(String key, DataEntry de) {
        StringBuilder r = new StringBuilder();

        r.append("SELECT \"" + this.name + "\"; \n");

        for (Map.Entry<String, DataEntry> existingSet: this.tmp.getData().entrySet()) {
            if (key.equals(existingSet.getKey())) {
                existingSet.getValue().write(de.read());

                if (de.getDataType() == DataType.STRING) {
                    r.append("SET \"" + existingSet.getKey() + "\" VALUE \"" + de.read() + "\"; \n");
                } else {
                    r.append("SET \"" + existingSet.getKey() + "\" VALUE " + de.read() + "; \n");
                }

                this.data.put(existingSet.getKey(), de);
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
        return "QUERY \"" + this.name + "\" FROM INSTANCE; \n";
    }

    /**
     * Returns a PANG query for querying the given instance based on its name in the database
     * @param name Name of the instance to be queried from the database
     * @return String containing PANG query for querying the given instance based on its name
     */
    public static String toQuery(String name) {
        return "QUERY \"" + name + "\" FROM INSTANCE; \n";
    }

    /**
     * Returns a PANG query for querying all currently existing instances in the database
     * @return String containing a PANG query for querying all existing instances
     */
    public static String queryAll() {
        return "QUERY INSTANCE; \n";
    }

    /**
     * Returns the name of the respective instance.
     * @return The name of the instance
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the respective instance.
     * @param name The name of the instance to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the template of the respective instance.
     * @return The template of the instance
     */
    public Template getTemplate() {
        return this.tmp;
    }

    /**
     * Sets the template of the respective instance. WARNING: This deletes all the currently stored data, as it's
     * dependent on what entries the template has.
     * @param tmp The template of the instance to be set
     */
    public void setTemplate(Template tmp) {
        this.tmp = tmp;
        this.data = new HashMap<>();
    }

    /**
     * Returns the data entries of the respective instance all stored in a hashmap.
     * @return Hashmap containing all the data entries of the instance
     */
    public HashMap<String, DataEntry> getData() {
        return this.data;
    }

    /**
     * Returns the data entry with the given key of the respective instance.
     * @param key Key of the DataEntry in the Instance object to be returned
     * @return DataEntry corresponding to the key in the data Hashmap
     */
    public DataEntry getDataEntry(String key) {
        return this.data.get(key);
    }

    /**
     * Returns a PANG query for deleting the respective instance in the database.
     * @return String containing a PANG query for deleting the respective instance
     */
    public String deleteQuery() {
        return "DELETE \"" + this.name + "\" FROM INSTANCE; \n";
    }
}
