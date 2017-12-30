package edu.pwr.tp.server.party;

import edu.pwr.tp.server.CCManager;
import edu.pwr.tp.server.GameManager;
import edu.pwr.tp.server.Server;
import edu.pwr.tp.server.exceptions.FullPartyException;
import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.GameType;
import edu.pwr.tp.server.model.factories.chinesecheckers.CCGameModelFactory;
import edu.pwr.tp.server.user.BOT;
import edu.pwr.tp.server.user.BasicBOT;
import edu.pwr.tp.server.user.ConnectedUser;
import edu.pwr.tp.server.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class representing a Party, that is, a group of users that can communicate/play with each other
 *
 * @author Aleksander Lasecki
 */
public class Party implements Runnable {

    /**
     * Maximum number of users connected to the Party
     */
    private final int maxUsers;
    /**
     * Array containing all connected users (null if a slot is empty)
     */
    private User[] users;
    /**
     * Number of free slots in the Party
     */
    private int freeSlots;
    /**
     * Name of the Party
     */
    private String name;

    private int botCount;

    private GameType type;

    private boolean joinable = false;
    private boolean running = true;

    private GameManager manager;
    private Server server;

    /**
     * Class constructor
     *
     * @param maxUsers  Maximum number of users connected to the Party
     * @param name      Name of the Party
     */
    public Party(int maxUsers, int botCount, String name, GameType type, Server server) throws InvalidArgumentsException {
        this.maxUsers = maxUsers;
        this.botCount = botCount;
        this.name = name;
        this.type = type;
        this.server = server;

        users = new ConnectedUser[maxUsers];
        freeSlots = maxUsers;
        joinable = true;
    }

    /**
     * Adds the specified user to the Party
     *
     * @param user                  The user to be added
     * @throws FullPartyException   Thrown if the Party was already full
     */
    public synchronized void addUser(User user) throws FullPartyException {
        if (freeSlots > 0) {
            for (int i = 0; i < maxUsers; i++) {
                if (users[i] == null) {
                    users[i] = user;
                    freeSlots--;

                    System.out.println("User with ID=" + user.getID() + " has joined " + name + ((user instanceof BOT) ? " (bot)" : ""));

                    return;
                }
            }
        }

        throw new FullPartyException();
    }

    /**
     * Removes the specified user from the party
     *
     * @param user  The user to be removed
     */
    synchronized void removeUser(User user) {
        if (user == null)
            return;

        for (int i = 0; i < maxUsers; i++) {
            if (users[i] != null && users[i].equals(user)) {
                users[i] = null;
                freeSlots++;
                break;
            }
        }
    }

    public boolean isJoinable() {
        return joinable;
    }

    /**
     * Returns the maximum number of users connected to the Party
     *
     * @return  Maximum number of users connected to the Party
     */
    public int getMaxUsers() {
        return maxUsers;
    }

    /**
     * Returns the number of free slots in the Party
     *
     * @return  Number of free slots in the Party
     */
    public int getFreeSlots() {
        return freeSlots;
    }

    /**
     * Returns the name of the Party
     *
     * @return  Name of the Party
     */
    public String getName() {
        return name;
    }

    private void initLoop() {
        for (int b = 0; b < botCount; b++) {
            try {
                addUser(new BasicBOT((-b - 1), manager));
            } catch (Exception e) {
                endParty();
            }
        }

        waitForStart();

        joinable = false;

        List<Integer> userIDs = initDictionary();
        initGameModel(userIDs);

        if (running) {
            defragUsers();
            sendPartyStartInfo(userIDs.size());
            waitForUsers();
        }
    }

    private void waitForStart() {
        boolean gameStarted = false;
        while (!gameStarted) {
            for (User user : users) {
                if (user == null || !(user instanceof  ConnectedUser))
                    continue;

                try {
                    String msg = user.receiveMessage(-2);

                    if (msg.length() > 0) {
                        Map<String, Object> response = Server.parser.parse(msg);
                        if (response != null && response.containsKey("s_start")) {
                            gameStarted = true;
                            break;
                        }

                        if (response == null)
                            throw new IOException();
                    }
                } catch (Exception e) {
                    disconnect((ConnectedUser) user);
                }
            }
        }
    }

    private List<Integer> initDictionary() {
        List<Integer> userIDs = new ArrayList<>();

        for (int u = 0; u < users.length; u++) {
            if (users[u] == null)
                continue;

            userIDs.add(users[u].getID());
        }

        return userIDs;
    }

    private void initGameModel(List<Integer> userIDs) {
        try {
            switch (type) {
                case CHINESE_CHECKERS:
                    manager = new CCManager(CCGameModelFactory.getInstance(), userIDs.size());
                    break;
            }

            manager.init(userIDs);
        } catch (InvalidArgumentsException e) {
            // This should never happen
            endParty();
        }
    }

    // TODO: Unit test this
    private void defragUsers() {
        int f_i = 0;
        int b_i = users.length - 1;
        while (f_i < b_i) {
            if (users[f_i] == null) {
                while (f_i < b_i && users[b_i] == null) b_i--;

                if (f_i < b_i) {
                    users[f_i] = users[b_i];
                    users[b_i] = null;
                }
            }

            f_i++;
        }
    }

    private void sendPartyStartInfo(int pcount) {
        for (int u = 0; u < users.length; u++) {
            if (users[u] == null || !(users[u] instanceof ConnectedUser))
                continue;

            users[u].sendMessage(Server.builder.put("s_game", "CC").put("i_pcount", pcount).put("i_pindex", u).get());
        }
    }

    private void waitForUsers() {
        boolean allDone = false;
        while (!allDone) {

            allDone = true;

            for (User user : users) {
                if (user == null || !(user instanceof ConnectedUser))
                    continue;

                ConnectedUser cUser = (ConnectedUser) user;

                try {
                    String msg = cUser.receiveMessage(-1);

                    if (msg.length() > 0) {
                        Map<String, Object> response = Server.parser.parse(msg);

                        if (response == null)
                            throw new IOException();

                        cUser.setDoneSetUp(response.containsKey("b_done") && (boolean) response.get("b_done"));
                        if (!cUser.isDoneSetUp()) allDone = false;
                    } else {
                        allDone = false;
                    }
                } catch (IOException e) {
                    endParty();
                }
            }
        }
    }

    private void gameLoop() {
        // TODO: Check if someone won in this condition here
        while (!manager.someoneWon()) {
            for (int u = 0; u < users.length; u++) {
                if (users[u] == null)
                    continue;

                try {
                    // TODO: Clear the buffer here in case someone sent something when they weren't supposed to
                    handleTurn(u);
                } catch (IOException e) {
                    endParty();
                }
            }
        }

        for (User u : users) {
            if (u == null || !(u instanceof ConnectedUser))
                continue;

            u.sendMessage(Server.builder.put("b_won", manager.isWinner(u.getID())).get());
        }
    }

    private void handleTurn(int u) throws IOException {
        boolean again = true;

        while (again) {
            again = false;
            users[u].sendMessage(Server.builder
                     .put("s_move", "Your move")
                     .get());

            // users[u].getIn().reset();
            Map<String, Object> response = Server.parser.parse(users[u].receiveMessage(-1));
            // users[u].getIn().mark(0);

            if (response.containsKey("i_action")) {
                int action = (int) response.get("i_action");

                switch (action) {
                    case 0:
                        boolean done = manager.makeMove(users[u].getID(),
                                (int) response.get("i_fx"), (int) response.get("i_fy"), (int) response.get("i_tx"), (int) response.get("i_ty"));

                        if (!done) {
							users[u].sendMessage(Server.builder.put("b_valid", false).get());
							again = true;
						}
                        else {
                            sendMove(users[u].getID(),
                                    (int) response.get("i_fx"), (int) response.get("i_fy"), (int) response.get("i_tx"), (int) response.get("i_ty"));
                        }

                        // TODO: Set again to true here if another jump is available
                        break;
                    case 1:
                        // Skipping a move
                        break;
                }
            } else {
                again = true;
            }
        }
    }

    private void sendMove(int u, int fx, int fy, int tx, int ty) {
        for (User user : users) {
            if (user == null) continue;

            if (user.getID() == u)
				user.sendMessage(Server.builder.put("b_valid", true).get());

            user.sendMessage(Server.builder.put("i_action", 0).put("i_fx", fx).put("i_fy", fy).put("i_tx", tx).put("i_ty", ty).get());
        }
    }

    private void endParty() {
        System.out.println("Stopping " + getName() + "...");

        for (User user : users) {
            if (user instanceof ConnectedUser) disconnect((ConnectedUser) user);
            else removeUser(user);
        }
        running = false;
        server.removeParty(this);
    }

    private void disconnect(ConnectedUser u) {
        if (u == null) return;

        u.sendMessage(Server.builder.put("s_disc", "Disconnected").get());

        System.out.println("Disconnecting " + u.getID() + "...");
        removeUser(u);

        try {
            u.closeIn();
        } catch (IOException e1) {
            System.err.println("Unable to close client's socket...");
        }

        u.closeOut();
    }

    /**
     * Simple chat implementation
     */
    @Override
    public void run() {
        initLoop();
        if (running) gameLoop();
        if (running) endParty();
    }
}
