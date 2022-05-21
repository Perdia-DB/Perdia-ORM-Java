package at.davideko.perdia;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
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
            OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8);
            PrintWriter writer = new PrintWriter(osw, true);

            writer.println(text);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    public String[] read() {
        byte[] bytes = new byte[4096];
        ArrayList<String> s = new ArrayList<>();

        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(input);

            //InputStream in = socket.getInputStream();
            //bytes = in.readAllBytes();



            /*
            byte[] processed = buffer.toByteArray();

            System.out.println(processed[0]);
             */

            while (reader.readLine() != null) {
                s.add(reader.readLine());
                if (s.get(s.size()-1).charAt(s.get(s.size()-1).length()-1) == ']') {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        }

        String[] r = new String[s.size()];
        r = s.toArray(r);

        return r;
    }
}
