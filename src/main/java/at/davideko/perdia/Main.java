package at.davideko.perdia;

import at.davideko.perdia.queries.storage.AllInstances;
import at.davideko.perdia.queries.storage.AllTemplates;
import at.davideko.perdia.tcp.TCPClient;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);

        AllTemplates.fetch(client);
        AllInstances.fetch(client);
    }
}
