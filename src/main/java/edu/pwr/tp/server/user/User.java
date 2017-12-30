package edu.pwr.tp.server.user;

import java.io.IOException;

/**
 * This is a player/spectator class that will be connected to the Party
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

    public abstract void closeIn() throws IOException;
    public abstract void closeOut();

    // timeout = -1 == infinite wait
    // timeout = -2 == instant
    public abstract String receiveMessage(long timeout) throws IOException;
    public abstract void sendMessage(String msg);
}
