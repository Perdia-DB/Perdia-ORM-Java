package at.davideko.perdia;

import at.davideko.perdia.queries.*;
import at.davideko.perdia.tcp.Crypto;
import at.davideko.perdia.tcp.TCPClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);
        Crypto c = new Crypto();

        Template numbers = new Template("NUMBERS");
        numbers.addEntry("First", DataType.INTEGER, 1L);
        numbers.addEntry("Second", DataType.INTEGER, 1L);
        System.out.println(numbers.toQuery());
        client.write(numbers.toQuery().getBytes(StandardCharsets.UTF_8));

        QueryObject qo = new QueryObject("Addition", numbers);
        HashMap<String, DataEntry> hm = new HashMap<>();
        DataEntry buffer = new DataEntry(DataType.INTEGER, 345734355687L);
        hm.put("First", buffer);
        buffer = new DataEntry(DataType.INTEGER, 223235768676L);
        hm.put("Second", buffer);
        System.out.println(qo.createQueryObject());
        client.write(qo.createQueryObject().getBytes(StandardCharsets.UTF_8));
        System.out.println(qo.writeToQueryObject(hm));
        client.write(qo.writeToQueryObject(hm).getBytes(StandardCharsets.UTF_8));
        System.out.println(qo.toQuery());
        client.write(qo.toQuery().getBytes(StandardCharsets.UTF_8));

        byte[] b = client.read();
        String s = new String(b, StandardCharsets.UTF_8);
        System.out.println(s);

        JsonParser jp = new JsonParser(b);
        System.out.println(jp.getData());
        System.out.println(jp.getData().get("First").read() + jp.getData().get("Second").read());
    }
}
