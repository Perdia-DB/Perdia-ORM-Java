package at.davideko.perdia.tcp;

import at.davideko.perdia.crypto.Crypto;

import java.io.*;
import java.net.Socket;

/**
 * Class for connecting to as well as writing and receiving data from the server socket Perdia-DB is running on
 */
public class TCPClient {
    Socket socket;
    Crypto c = new Crypto();
    private final String host;
    private final int port;

    /**
     * Constructor for the TCPClient class
     *
     * @param host The hostname or IP-address of the server
     * @param port The port on the server the client is supposed to connect to
     */
    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sends the given data to the Perdia-DB server
     * @param text Byte array containing the single characters of the query text encoded in UTF-8
     * @exception IOException If the given server socket does not exist, is offline or refusing connection, an
     * IOException is thrown and caught
     */
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

    /**
     * Receives data sent by the Perdia-DB server
     * @return Byte array containing the single characters of the query text encoded in UTF-8
     * @exception IOException If the given server socket does not exist, is offline or refusing connection, an
     * IOException is thrown and caught
     */
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
