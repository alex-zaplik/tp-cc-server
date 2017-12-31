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

    /**
     * True if the user has finished setting up their view
     */
    private boolean doneSetUp = false;

    /**
     * The socket used for communication with the user
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

        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message to the user's output
     *
     * @param msg   The message to be sent
     */
    @Override
    public void sendMessage(String msg) {
        getOut().println(msg);
    }

    /**
     * Closing the input of this user
     *
     * @throws IOException  Can be thrown if closing was not successful
     */
    @Override
    public void closeIn() throws IOException {
        getIn().close();
    }

    /**
     * Closing the output of this user
     */
    @Override
    public void closeOut() {
        getOut().close();
    }

    /**
     * Retrieves a String from the users input
     * Timeout settings:    timeout >= 0    -> This method will wait for timeout milliseconds
     *                      timeout == -1   -> This method will wait indefinitely
     *                      timeout == -2   -> This method will return a String from the input or and empty String if no input was available
     *
     * @param timeout       The timeout setting
     * @return              The String that was received
     * @throws IOException  Thrown by communication errors
     */
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

    /**
     * Returns the doneSetUp flag
     *
     * @return  The doneSetUp flag
     */
    public boolean isDoneSetUp() {
        return doneSetUp;
    }

    /**
     * Sets the doneSetUp flag
     *
     * @param doneSetUp The new value
     */
    public void setDoneSetUp(boolean doneSetUp) {
        this.doneSetUp = doneSetUp;
    }
}
