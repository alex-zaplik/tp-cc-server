package edu.pwr.tp.server.user;

import edu.pwr.tp.server.GameManager;
import edu.pwr.tp.server.Server;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public abstract class BOT extends User {

    private GameManager manager;

    private Queue<String> toServer = new ArrayDeque<>();
    private Queue<String> toClient = new ArrayDeque<>();

    /**
     * Class constructor
     *
     * @param ID The ID of the user
     * @throws IOException Thrown if there was a failure during initialization of the PrintWriter and/or the BufferedReader
     */
    BOT(int ID) throws IOException {
        super(ID);
    }

    public void setManager(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void closeIn() throws IOException {
        toServer.clear();
    }

    @Override
    public void closeOut() {
        toClient.clear();
    }

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

    void sendMove(int fx, int fy, int tx, int ty) {
        toServer.add(Server.builder.put("i_action", 0).put("i_fx", fx).put("i_fy", fy).put("i_tx", tx).put("i_ty", ty).get());
    }

    void skipMove() {
        toServer.add(Server.builder.put("i_action", 1).get());
    }

    @Override
    public void sendMessage(String msg) {
        toClient.add(msg);
        handleInput();
    }

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

    abstract void makeMove();
    abstract void confirmMove(boolean wasValid);
}
