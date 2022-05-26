package at.davideko.perdia;

import at.davideko.perdia.queries.DataEntry;
import at.davideko.perdia.queries.DataType;
import at.davideko.perdia.queries.QueryObject;
import at.davideko.perdia.queries.Template;
import at.davideko.perdia.tcp.Crypto;
import at.davideko.perdia.tcp.TCPClient;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);
        Crypto c = new Crypto();

        Template template = new Template("DAY");
        template.addEntry("First", DataType.STRING, "Nothing");
        template.addEntry("Second", DataType.STRING, "Nothing");
        System.out.println(template.toQuery());
        template.toPreset("preset_day");

        HashMap<String, DataEntry> hm = new HashMap<>();
        DataEntry buffer = new DataEntry(DataType.STRING);
        buffer.write("Science");
        hm.put("First", buffer);
        buffer = new DataEntry(DataType.STRING);
        buffer.write("CS");
        hm.put("Second", buffer);
        QueryObject qo = new QueryObject("Monday", template);
        System.out.println(qo.createQueryObject());
        System.out.println(qo.writeToQueryObject(hm));
        System.out.println(qo.toQuery());

        /*
        try {
            byte[] raw = Files.readAllBytes(Path.of("query.txt"));
            client.write(qb);

            byte[] b = client.read();
            String s = new String(b, StandardCharsets.UTF_8);
            System.out.println(s);

            JsonParser jp = new JsonParser(b);
            System.out.println(jp.query[0].name);
            System.out.println(jp.query[0].data.first);


        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }
}
