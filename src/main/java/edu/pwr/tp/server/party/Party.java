package edu.pwr.tp.server.party;

import edu.pwr.tp.server.CCManager;
import edu.pwr.tp.server.GameManager;
import edu.pwr.tp.server.Server;
import edu.pwr.tp.server.exceptions.FullPartyException;
import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.GameType;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
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
    /**
     * The number of bots in a game. Must be less than maxUsers
     */
    private int botCount;
    /**
     * The type of the game played in this party
     */
    private GameType type;

    /**
     * A party in not joinable after a game started
     */
    private boolean joinable = false;
    /**
     * A party stops running when an exception is thrown
     */
    private boolean running = true;

    /**
     * The manager managing the game model of the game run in this party
     */
    private GameManager manager;
    /**
     * Reference to the server. Used for informing the server that this party stopped
     */
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

        users = new User[maxUsers];
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

    /**
     * Initialization loop of the party that calls other methods
     */
    private void initLoop() {
        addBots();

        waitForStart();

        joinable = false;

        List<Integer> userIDs = initUserIDs();
        initGameModel(userIDs);

        if (running) {
            defragUsers(users);
            sendPartyStartInfo(userIDs.size());
            waitForUsers();
        }
    }

    /**
     * Adds a needed number of Bots to the party
     */
    private void addBots() {
        for (int b = 0; b < botCount; b++) {
            try {
                addUser(new BasicBOT((-b - 1)));
            } catch (Exception e) {
                endParty();
            }
        }
    }

    /**
     * Waits for someone to press the Start button
     */
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

    /**
     * Adds all userIDs to a list and returns it
     *
     * @return  The list that was created
     */
    private List<Integer> initUserIDs() {
        List<Integer> userIDs = new ArrayList<>();

        for (User user : users) {
            if (user == null)
                continue;

            userIDs.add(user.getID());
        }

        return userIDs;
    }

    /**
     * Initializes the game model based on the game type and the number of users
     *
     * @param userIDs   List generated by initUserIDs
     */
    private void initGameModel(List<Integer> userIDs) {
        try {
            switch (type) {
                case CHINESE_CHECKERS:
                    manager = new CCManager(userIDs.size());
                    break;
            }

            for (User user : users)
                if (user != null && !(user instanceof ConnectedUser))
                    ((BOT) user).setManager(manager);

            manager.init(userIDs);
        } catch (InvalidArgumentsException e) {
            // This should never happen
            running = false;
            endParty();
        }
    }

    /**
     * Removed null-holes from the users array: {user1, null, user2, null, null} -> {user1, user2, null, null, null}
     */
    private void defragUsers(User[] users) {
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

    /**
     * Informs all users that the party started
     *
     * @param pcount    Number of players in a party
     */
    private void sendPartyStartInfo(int pcount) {
        for (int u = 0; u < users.length; u++) {
            if (users[u] == null)
                continue;

            if (!(users[u] instanceof ConnectedUser)) {
                if (users[u] instanceof BasicBOT) {
                    ((BasicBOT) users[u]).setIndex(u);
                }
            }

            users[u].sendMessage(Server.builder.put("s_game", "CC").put("i_pcount", pcount).put("i_pindex", u).get());
        }
    }

    /**
     * Waits for all users to confirm that they have initialized their view
     */
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

    /**
     * Loop handling the game
     */
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

    /**
     * Handles a turn of a single user
     *
     * @param u             The ID of the user
     * @throws IOException  Thrown on communication error, eg. the users disconnected
     */
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
                            if(((CCBoard) getModel().getBoard()).getJumpingPawn()!=null) again = true;

                            sendMove(users[u].getID(), again,
                                    (int) response.get("i_fx"), (int) response.get("i_fy"), (int) response.get("i_tx"), (int) response.get("i_ty"));
                        }

                        break;
                    case 1:
                        // Skipping a move
                        ((CCBoard) getModel().getBoard()).setJumpingPawn(null);
                        break;
                }
            } else {
                again = true;
            }
        }
    }

    /**
     * Sends information about a move that has just been performed
     *
     * @param u     The user that performed the move (we just want to confirm his move)
     * @param fx    From x
     * @param fy    From y
     * @param tx    To x
     * @param ty    To y
     */
    private void sendMove(int u, boolean jump, int fx, int fy, int tx, int ty) {
        for (User user : users) {
            if (user == null) continue;

            if (user.getID() == u)
				user.sendMessage(Server.builder.put("b_valid", true).put("b_jump", jump).get());

            user.sendMessage(Server.builder.put("i_action", 0).put("i_fx", fx).put("i_fy", fy).put("i_tx", tx).put("i_ty", ty).get());
        }
    }

    /**
     * Ends the party, disconnects everyone and tells the server to remove this party from it's list
     */
    private void endParty() {
        System.out.println("Stopping " + getName() + "...");

        for (User user : users) {
            if (user instanceof ConnectedUser) disconnect((ConnectedUser) user);
            else removeUser(user);
        }
        running = false;
        server.removeParty(this);
    }

    /**
     * Disconnects a single user
     *
     * @param u     The user that is to be disconnected
     */
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
     * Returns the game model of this party
     *
     * @return  The game model of this party
     */
    private GameModel getModel() {
        return manager.getModel();
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
