package at.davideko.perdia.tcp;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TCPClient {
    Socket socket;
    Crypto c = new Crypto();
    private final String host;
    private final int port;

    public TCPClient(String host, int port) {
        //try {
            //this.socket = new Socket(host, port);
            this.host = host;
            this.port = port;

        /*} catch (UnknownHostException e) {
            System.out.println("Host not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            throw new Error("The socket does not exist");
        }
        */
    }

    public void write(byte[] text) {
        try {
            // I don't know if this is how it's supposed to be done, but right know it's the only way I can get this
            // thing to work. Don't blame me, blame the database
            this.socket = new Socket(host, port);
            OutputStream os = socket.getOutputStream();

            text = c.encrypt(text);
            os.write(text);

            //os.close();

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    public byte[] read() {
        byte[] bytes = new byte[0];

        try {
            InputStream in = socket.getInputStream();

            bytes = in.readAllBytes();
            bytes = c.decrypt(bytes);

            //in.close();

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        }

        return bytes;
    }
}
