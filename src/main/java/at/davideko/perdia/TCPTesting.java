package at.davideko.perdia;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPTesting {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6969);

            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println("Hello!");

            BufferedReader reader = new BufferedReader(input);

            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }

        } catch (UnknownHostException e) {
            System.out.println("Host not found: " + e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            //e.printStackTrace();
        }
    }
}
