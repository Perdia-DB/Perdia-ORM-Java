package at.davideko.perdia;

import at.davideko.perdia.queries.DataEntry;
import at.davideko.perdia.queries.DataType;
import at.davideko.perdia.queries.QueryObject;
import at.davideko.perdia.queries.Template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);
        Crypto c = new Crypto();

        Template template = new Template("DAY");
        template.addEntry("First", DataType.STRING, "Nothing");
        template.addEntry("Second", DataType.STRING, "Nothing");
        System.out.println(template.toString());
        template.toPreset("preset_day");

        /*
        try {
            byte[] raw = Files.readAllBytes(Path.of("query.txt"));
            byte[] qb = c.encrypt(raw);
            client.write(qb);

            byte[] b = client.read();
            b = c.decrypt(b);
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
