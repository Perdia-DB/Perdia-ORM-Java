package at.davideko.perdia;

import at.davideko.perdia.queries.*;
import at.davideko.perdia.tcp.Crypto;
import at.davideko.perdia.tcp.TCPClient;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);
        Crypto c = new Crypto();

        Template day = new Template("DAY");
        day.addEntry("First", DataType.STRING, "Nothing");
        day.addEntry("Second", DataType.STRING, "Nothing");
        String t = day.toQuery();

        QueryObject qobj = new QueryObject("Montag", day);
        String cq = qobj.createQueryObject();
        String q = qobj.toQuery();

        System.out.println(t);
        System.out.println(cq);
        System.out.println(q);

        //client.write(t.getBytes(StandardCharsets.UTF_8));
        //client.write(cq.getBytes(StandardCharsets.UTF_8));
        client.write(q.getBytes(StandardCharsets.UTF_8));

        byte[] b = client.read();
        String s = new String(b, StandardCharsets.UTF_8);
        System.out.println(s);

        JsonParser jp = new JsonParser(b);
        System.out.println(jp.getData());
    }
}
