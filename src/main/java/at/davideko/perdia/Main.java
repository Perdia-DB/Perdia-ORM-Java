package at.davideko.perdia;

import at.davideko.perdia.queries.storage.AllInstances;
import at.davideko.perdia.queries.storage.AllTemplates;
import at.davideko.perdia.tcp.TCPClient;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

	    TCPClient client = new TCPClient(dotenv.get("IP"), Integer.parseInt(dotenv.get("PORT")));

        AllTemplates.fetch(client);
        AllInstances.fetch(client);
    }
}
