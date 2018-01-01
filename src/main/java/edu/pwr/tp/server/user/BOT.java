package edu.pwr.tp.server.user;

import edu.pwr.tp.server.GameManager;
import edu.pwr.tp.server.Server;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

/**
 * A class that handles a bot as a fake user
 *
 * @author Aleksander Lasecki
 */
public abstract class BOT extends User {

    /**
     * The manager of the game that the bot is a part of
     */
    private GameManager manager;

    /**
     * Queue of messages that are to be sent to the server
     */
    private Queue<String> toServer = new ArrayDeque<>();
    /**
     * Queue of messages that are to be received
     */
    private Queue<String> toClient = new ArrayDeque<>();

    /**
     * The bot's in-game index. Invalid if == -1
     */
    private int index = -1;

    /**
     * Class constructor
     *
     * @param ID The ID of the user
     * @throws IOException Thrown if there was a failure during initialization of the PrintWriter and/or the BufferedReader
     */
    BOT(int ID) throws IOException {
        super(ID);
    }

    /**
     * Sets the manager variable
     *
     * @param manager   The manager variable
     */
    public void setManager(GameManager manager) {
        this.manager = manager;
    }

    /**
     * Sets the in-game index
     *
     * @param index The index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Closing the input of this user
     *
     * @throws IOException  Can be thrown if closing was not successful
     */
    @Override
    public void closeIn() throws IOException {
        toServer.clear();
    }

    /**
     * Closing the output of this user
     */
    @Override
    public void closeOut() {
        toClient.clear();
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
            while (toServer.peek() == null);
            return toServer.poll();
        } else if (timeout == -2) {
            if (toServer.peek() != null) {
                return toServer.poll();
            } else {
                return "";
            }
        } else {
            long t = System.currentTimeMillis() + timeout;
            while (toServer.peek() == null) {
                if (System.currentTimeMillis() > t)
                    throw new IOException("Wait timeout");
            }
            return toServer.poll();
        }
    }

    /**
     * Sends a move to the server
     *
     * @param fx    From X
     * @param fy    From Y
     * @param tx    To X
     * @param ty    To Y
     */
    void sendMove(int fx, int fy, int tx, int ty) {
        toServer.add(Server.builder.put("i_action", 0).put("i_fx", fx).put("i_fy", fy).put("i_tx", tx).put("i_ty", ty).get());
    }

    /**
     * Sends a skip message
     */
    void skipMove() {
        toServer.add(Server.builder.put("i_action", 1).get());
    }

    /**
     * Sends a message to the user's output
     *
     * @param msg   The message to be sent
     */
    @Override
    public void sendMessage(String msg) {
        toClient.add(msg);
        handleInput();
    }

    /**
     * Handles messages received from the server
     */
    private void handleInput() {
        while (toClient.peek() != null) {
            String msg = toClient.poll();

            if (msg.length() > 0) {
                Map<String, Object> response = Server.parser.parse(msg);

                if (response.containsKey("s_move")) {
                    makeMove();
                } else if (response.containsKey("b_valid")) {
                    confirmMove((boolean) response.get("b_valid"));
                }
            }
        }
    }

    /**
     * Makes a move
     */
    abstract void makeMove();

    /**
     * Callback called by the server (with a message) confirming validity of the las move
     *
     * @param wasValid  True if the move was valid
     */
    abstract void confirmMove(boolean wasValid);
}
