package at.davideko.perdia.queries.storage;

import at.davideko.perdia.queries.Template;
import at.davideko.perdia.queries.parsing.TemplateParser;
import at.davideko.perdia.tcp.TCPClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class for storing all existing templates in the database on the ORM-side.
 */
public class AllTemplates {
    /**
     * ArrayList containing all the existing Template objects.
     */
    private static ArrayList<Template> allTemplates = new ArrayList<>();

    /**
     * Method for adding a Template object to the ArrayList containing all the Template objects.
     * @param tmp Template object to be added to the allTemplates ArrayList
     */
    public static void add(Template tmp) {
        allTemplates.add(tmp);
    }

    /**
     * Used for fetching all the existing templates in the database and writing them to the allTemplates ArrayList
     * as Template objects.
     * @param client TCPClient connected to the server running Perdia-DB
     */
    public static void fetch(TCPClient client) {
        client.write(Template.queryAll().getBytes(StandardCharsets.UTF_8));

        TemplateParser tp = new TemplateParser(client.read());
        allTemplates = tp.parseMultiple();
    }

    /**
     * Method for getting the ArrayList containing all the existing Template objects.
     * @return ArrayList containing all the existing Template objects
     */
    public static ArrayList<Template> get() {
        return allTemplates;
    }
}
