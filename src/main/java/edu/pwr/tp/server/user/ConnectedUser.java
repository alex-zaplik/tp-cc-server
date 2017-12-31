package edu.pwr.tp.server.user;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

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

        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void sendMessage(String msg) {
        getOut().println(msg);
    }

    @Override
    public void closeIn() throws IOException {
        getIn().close();
    }

    @Override
    public void closeOut() {
        getOut().close();
    }

    @Override
    public String receiveMessage(long timeout) throws IOException {
        if (timeout == -1) {
            socket.setSoTimeout(0);
            return in.readLine();
        } else if (timeout == -2) {
            try {
                socket.setSoTimeout(10);
                String msg = in.readLine();
                socket.setSoTimeout(0);

                if (msg == null) throw new IOException("Socket closed");
                else return msg;
            } catch (SocketException e) {
                throw new IOException("Client disconnected");
            } catch (Exception e) {
                return "";
            }
        } else {
            socket.setSoTimeout(0);
            long t = System.currentTimeMillis() + timeout;
            while (!getIn().ready()) {
                if (System.currentTimeMillis() > t)
                    throw new IOException("Wait timeout");
            }
            return in.readLine();
        }
    }

    /**
     * Returns the user's PrintWriter
     *
     * @return  User's PrintWriter
     */
    private PrintWriter getOut() {
        return out;
    }

    /**
     * Returns the user's BufferedReader
     *
     * @return  User's BufferedReader
     */
    private BufferedReader getIn() {
        return in;
    }

    public boolean isDoneSetUp() {
        return doneSetUp;
    }

    public void setDoneSetUp(boolean doneSetUp) {
        this.doneSetUp = doneSetUp;
    }
}
