package edu.pwr.tp.server.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A class representing a users that is connected with the server
 *
 *
 * @author Aleksander Lasecki
 * @see edu.pwr.tp.server.Server
 */
public class ConnectedUser extends User {

    /**
     * PrintWriter used for sending messages to the user
     */
    private PrintWriter out;
    /**
     * BufferedReader used to receive messages from the user
     */
    private BufferedReader in;

    /**
     * Socket used for communicating with the client
     */
    private Socket socket;

    /**
     * Class constructor
     *
     * @param socket        The socket used for communication between the user and the server
     * @param ID            The ID of the user
     * @throws IOException  Thrown if there was a failure during initialization of the PrintWriter and/or the BufferedReader
     */
    public ConnectedUser(Socket socket, int ID) throws IOException {
        super(ID);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
    }

    /**
     * Returns the user's PrintWriter
     *
     * @return  User's PrintWriter
     */
    public PrintWriter getOut() {
        return out;
    }

    /**
     * Returns the user's BufferedReader
     *
     * @return  User's BufferedReader
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * Returns the user's Socket
     *
     * @return  User's Socket
     */
    public Socket getSocket() {
        return socket;
    }
}
