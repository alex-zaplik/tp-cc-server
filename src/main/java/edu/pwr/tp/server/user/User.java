package edu.pwr.tp.server.user;

import java.io.IOException;

/**
 * This is a player/spectator class that will be connected to the Party
 *
 * @author Aleksander Lasecki
 */
public abstract class User {

    /**
     * The user ID
     */
    private int ID;

    /**
     * Class constructor
     *
     * @param ID            The ID of the user
     * @throws IOException  Thrown if there was a failure during initialization of the PrintWriter and/or the BufferedReader
     */
    User(int ID) throws IOException {
        this.ID = ID;
    }

    /**
     * Returns the user's ID
     *
     * @return  User's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Closing the input of this user
     *
     * @throws IOException  Can be thrown if closing was not successful
     */
    public abstract void closeIn() throws IOException;

    /**
     * Closing the output of this user
     */
    public abstract void closeOut();

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
    public abstract String receiveMessage(long timeout) throws IOException;

    /**
     * Sends a message to the user's output
     *
     * @param msg   The message to be sent
     */
    public abstract void sendMessage(String msg);
}
