package at.davideko.perdia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TCPClient {
    Socket socket;

    public TCPClient(String host, int port) {
        try {
            this.socket = new Socket(host, port);

        } catch (UnknownHostException e) {
            System.out.println("Host not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    public void write(String text) {
        try {
            PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);

            writer.println(text);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    public String[] read() {
        ArrayList<String> s = new ArrayList<>();

        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream());

            BufferedReader reader = new BufferedReader(input);

            while (reader.readLine() != null) {
                s.add(reader.readLine());
                if (s.get(s.size()-1).equals("]")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }

        String[] r = new String[s.size()];
        r = s.toArray(r);

        return r;
    }
}
