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

    private boolean doneSetUp = false;

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
    }

    // TODO: Document
    public void sendMessage(String msg) {
        getOut().println(msg);
    }

    // TODO: Document
    // timeout = -1 == infinite wait
    // timeout = -2 == instant
    public String receiveMessage(long timeout) throws IOException {
        // TODO: return null if -1 and someone disconnected

        String msg;

        long t = System.currentTimeMillis() + timeout;
        while (timeout != -2 && !getIn().ready()) {
            if (timeout != -1 && System.currentTimeMillis() > t)
                throw new IOException("Wait timeout");
        }

        msg = in.readLine();
        if (timeout == -2 || msg != null) {
            return msg;
        } else {
            throw new IOException("Unable to receive message. The socket might be closed");
        }
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

    public boolean isDoneSetUp() {
        return doneSetUp;
    }

    public void setDoneSetUp(boolean doneSetUp) {
        this.doneSetUp = doneSetUp;
    }
}
