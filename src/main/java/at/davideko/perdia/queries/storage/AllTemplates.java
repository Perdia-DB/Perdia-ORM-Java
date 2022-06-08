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
    public static ArrayList<Template> allTemplates = new ArrayList<>();

    public static void add(Template tmp) {
        allTemplates.add(tmp);
    }

    public static void fetch(TCPClient client) {
        client.write(Template.queryAll().getBytes(StandardCharsets.UTF_8));

        TemplateParser tp = new TemplateParser(client.read());
        allTemplates = tp.parseMultiple();
    }

    public static ArrayList<Template> get() {
        return allTemplates;
    }
}
