package at.davideko.perdia;

import at.davideko.perdia.queries.*;
import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;
import at.davideko.perdia.crypto.Crypto;
import at.davideko.perdia.queries.data.StringDataEntry;
import at.davideko.perdia.queries.parsing.InstanceParser;
import at.davideko.perdia.queries.parsing.TemplateParser;
import at.davideko.perdia.tcp.TCPClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static ArrayList<Template> allTemplates = new ArrayList<>();
    public static ArrayList<Instance> allInstances = new ArrayList<>();

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);

        /*
        Template day = new Template("DAY");
        day.addEntry("First", DataType.STRING, "Nothing");
        day.addEntry("Second", DataType.STRING, "Nothing");
        day.addEntry("Third", DataType.STRING, "Nothing");
        day.addEntry("Day", DataType.INTEGER, 1);
        day.addEntry("Seconds", DataType.FLOAT, 0.0);
        System.out.println(day.toCreationQuery());
        client.write(day.toCreationQuery().getBytes(StandardCharsets.UTF_8));

        Instance monday = new Instance("Monday", day);
        System.out.println(monday.createInstance(day));
        client.write(monday.createInstance(day).getBytes(StandardCharsets.UTF_8));

        HashMap<String, DataEntry> hm = new HashMap<>();
        DataEntry buffer = new StringDataEntry("Science");
        hm.put("First", buffer);
        buffer = new StringDataEntry("CS");
        hm.put("Second", buffer);
        System.out.println(monday.writeToQueryObject(hm));
        client.write(monday.writeToQueryObject(hm).getBytes(StandardCharsets.UTF_8));

        Instance tuesday = new Instance("Tuesday", monday);
        System.out.println(tuesday.copyInstance(monday));
        client.write(tuesday.copyInstance(monday).getBytes(StandardCharsets.UTF_8));

        System.out.println(tuesday.toQuery());
        client.write(tuesday.toQuery().getBytes(StandardCharsets.UTF_8));

        System.out.println(Template.queryAll());
        client.write(Template.queryAll().getBytes(StandardCharsets.UTF_8));

        //System.out.println(day.deleteQuery());
        //client.write(day.deleteQuery().getBytes(StandardCharsets.UTF_8));

        System.out.println(monday.deleteQuery());
        client.write(monday.deleteQuery().getBytes(StandardCharsets.UTF_8));

        monday = new Instance("Monday", tuesday);
        System.out.println(monday.copyInstance(tuesday));
        client.write(monday.copyInstance(tuesday).getBytes(StandardCharsets.UTF_8));
        */

        client.write(Instance.queryAll().getBytes(StandardCharsets.UTF_8));
        //System.out.println(new String(client.read(), StandardCharsets.UTF_8));
        InstanceParser ip = new InstanceParser(client.read());
        ArrayList<Instance> al = ip.parseMultiple();
        System.out.println(al.get(0).getName());
        System.out.println(al.get(1).getName());
        System.out.println(ip.instanceAmount());

        //System.out.println(mondayNew.name);
        //System.out.println(mondayNew.data);
        //System.out.println(mondayNew.tmp);

        /*
        client.write(Instance.queryAll().getBytes(StandardCharsets.UTF_8));
        String s = new String(client.read(), StandardCharsets.UTF_8);
        s = s.substring(1, s.length()-2);
        System.out.println(s);
        JSONArray arr = new JSONArray(s);
        System.out.println(arr.get(0));
        */

        client.write(Template.queryAll().getBytes(StandardCharsets.UTF_8));
        //System.out.println(new String(client.read(), StandardCharsets.UTF_8));
        TemplateParser tp = new TemplateParser(client.read());
        System.out.println(tp.templateAmount());
        System.out.println(tp.parseSingle().getData());

        //System.out.println(mondayNew.getName());
    }
}
