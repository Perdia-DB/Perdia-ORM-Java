package at.davideko.perdia;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("127.0.0.1", 3000);

        client.write("test");


        byte[] test = client.read();
        System.out.println(test[0]);

    }
}
