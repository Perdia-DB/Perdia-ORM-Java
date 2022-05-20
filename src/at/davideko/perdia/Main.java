package at.davideko.perdia;

public class Main {

    public static void main(String[] args) {
	    TCPClient client = new TCPClient("localhost", 3000);

        client.write("test");

        String[] test = client.read();
        for (String s : test) {
            System.out.println(s);
        }
    }
}
