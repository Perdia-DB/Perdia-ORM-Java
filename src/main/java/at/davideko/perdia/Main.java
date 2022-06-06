package at.davideko.perdia;

import at.davideko.perdia.queries.*;
import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;
import at.davideko.perdia.crypto.Crypto;
import at.davideko.perdia.queries.data.StringDataEntry;
import at.davideko.perdia.tcp.TCPClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static ArrayList<Template> allTemplates = new ArrayList<>();
    public static ArrayList<Instance> allInstances = new ArrayList<>();

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);
        Crypto c = new Crypto();

        Template day = new Template("DAY");
        day.addEntry("First", DataType.STRING, "Nothing");
        day.addEntry("Second", DataType.STRING, "Nothing");
        day.addEntry("Third", DataType.STRING, "Nothing");
        day.addEntry("Day", DataType.INTEGER, 1);
        day.addEntry("Seconds", DataType.FLOAT, 0.0);
        System.out.println(day.toQuery());
        client.write(day.toQuery().getBytes(StandardCharsets.UTF_8));

        Instance monday = new Instance("Monday", day);
        System.out.println(monday.createQueryObject(day));
        client.write(monday.createQueryObject(day).getBytes(StandardCharsets.UTF_8));

        HashMap<String, DataEntry> hm = new HashMap<>();
        DataEntry buffer = new StringDataEntry("Science");
        hm.put("First", buffer);
        buffer = new StringDataEntry("CS");
        hm.put("Second", buffer);
        System.out.println(monday.writeToQueryObject(hm));
        client.write(monday.writeToQueryObject(hm).getBytes(StandardCharsets.UTF_8));

        Instance tuesday = new Instance("Tuesday", monday);
        System.out.println(tuesday.copyQueryObject(monday));
        client.write(tuesday.copyQueryObject(monday).getBytes(StandardCharsets.UTF_8));

        System.out.println(tuesday.toQuery());
        client.write(tuesday.toQuery().getBytes(StandardCharsets.UTF_8));

        System.out.println(Template.queryAll());
        client.write(Template.queryAll().getBytes(StandardCharsets.UTF_8));

        System.out.println(day.deleteQuery());
        client.write(day.deleteQuery().getBytes(StandardCharsets.UTF_8));

        System.out.println(monday.deleteQuery());
        client.write(monday.deleteQuery().getBytes(StandardCharsets.UTF_8));

        monday = new Instance("Monday", tuesday);
        System.out.println(monday.copyQueryObject(tuesday));
        client.write(monday.copyQueryObject(tuesday).getBytes(StandardCharsets.UTF_8));
    }
}
