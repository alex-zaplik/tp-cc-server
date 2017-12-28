package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.CreatingPartyFailedException;
import edu.pwr.tp.server.exceptions.FullPartyException;
import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.message.builder.IMessageBuilder;
import edu.pwr.tp.server.message.builder.JSONMessageBuilder;
import edu.pwr.tp.server.message.parser.IMessageParser;
import edu.pwr.tp.server.message.parser.JSONMessageParser;
import edu.pwr.tp.server.model.GameType;
import edu.pwr.tp.server.party.Party;
import edu.pwr.tp.server.user.ConnectedUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Protocol description
/**
 * A class representing the server functionality
 *
 * <p>This is where the protocol description will be</p>
 *
 * @author Aleksander Lasecki
 */
public class Server {

    /**
     * The server socket
     */
    private ServerSocket sSocket;
    /**
     * List of all running parties
     */
    private List<Party> parties;

    /**
     * Used to parse received messages
     */
    public static final IMessageParser parser = new JSONMessageParser();
    /**
     * Used to build messages that are to be sent throw a socket
     */
    public static final IMessageBuilder builder = new JSONMessageBuilder();

    /**
     * The ID of the last user that connected to the server
     */
    private volatile int lastID = 0;

    private volatile boolean waitForUsers = true;

    /**
     * Waits for a new user
     */
    private Runnable emptySocket = new Runnable() {
        @Override
        public void run() {
            if (sSocket != null && parties != null) {
                while (waitForUsers) {
                    ConnectedUser user;
                    try {
                        user = new ConnectedUser(sSocket.accept(), getID());
                        new Thread(() -> {
                            try {
                                System.out.println("User " + user.getID() + " connected");
                                setUpConnection(user);
                            } catch (Exception e) {
                                System.out.println("Disconnecting " + user.getID() + "...");

                                try {
                                    user.getIn().close();
                                } catch (IOException e1) {
                                    System.err.println("Unable to disconnect client...");
                                }
                                user.getOut().close();
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    /**
     * Returns a new and unique ID for a new user
     *
     * @return  The ID that was generated
     */
    private synchronized int getID() {
        return lastID++;
    }

    /**
     * Initializes the connection to a user (Joining a party)
     *
     * @param user          The user we want to initialize
     * @throws IOException  Thrown by the BufferedReader
     */
    private void setUpConnection(ConnectedUser user) throws IOException {
        sendPartyList(user);
        String response = user.getIn().readLine();

        if (response != null) {
            Map<String, Object> responseMap = parser.parse(response);

            if (responseMap.containsKey("i_action")) {
                int action = (int) responseMap.get("i_action");

                switch (action) {
                    case 0:
                        try {
                            Party p = createParty(responseMap);

                            if (p == null)
                                throw new CreatingPartyFailedException();

                            p.addUser(user);

                            user.sendMessage(builder.put("s_joined", p.getName()).get());

                            // TODO: Maybe do sth with this catch
                        } catch (CreatingPartyFailedException | FullPartyException | InvalidArgumentsException ignored) {}
                        break;
                    case 1:
                        try {
                            joinParty(user, responseMap);
                        } catch (FullPartyException ignored) {
                        }
                        break;
                    default:
                        throw new IOException("Unsupported action");
                }
            }

            return;
        }

        else throw new NullPointerException();
    }

    /**
     * Sends the list of all running parties to a user
     *
     * @param user  The user that the list will be sent to
     */
    private void sendPartyList(ConnectedUser user) {
        if (parties.size() == 0) {
            user.getOut().println(
                    builder.put("s_msg", "No parties available")
                            .get()
            );
        }

        int size = 0;
        for (int i = 0; i < parties.size(); i++) {
            if (parties.get(i).isJoinable()) size++;
        }

        for (int i = parties.size() - 1; i >= 0; i--) {
            if (!parties.get(i).isJoinable())
                continue;

            user.getOut().println(
                    builder.put("i_size", size)
                            .put("s_name", parties.get(i).getName())
                            .put("i_max", parties.get(i).getMaxUsers())
                            .put("i_left", parties.get(i).getFreeSlots())
                            .get()
            );
        }
    }

    /**
     * Creates a new party based on user preferences
     *
     * @param settings                          Map holding the preferences of the party that the user wants to create
     * @return                                  The reference to the party that was created
     * @throws CreatingPartyFailedException     Thrown if a failure accrued while attempting to create a new party
     */
    private synchronized Party createParty(Map<String, Object> settings) throws CreatingPartyFailedException, InvalidArgumentsException {
        Party party;
        String name = (String) settings.get("s_name");

        int max = (int) settings.get("i_max");

        for (Party p : parties)
            if (p.getName().equals(name))
                throw new CreatingPartyFailedException();

        party = new Party(max, name, GameType.CHINESE_CHECKERS);
        parties.add(party);
        new Thread(party, "Thread-" + party.getName()).start();

        return party;
    }

    /**
     * Add a connected user to an existing party
     *
     * @param user                  The user to be added
     * @param settings              Map holding the name of the party that the user wants to join
     * @throws FullPartyException   Thrown if a failure accrued while attempting to join a full party
     */
    private synchronized void joinParty(ConnectedUser user, Map<String, Object> settings) throws FullPartyException {
        Party party = null;
        String name = (String) settings.get("s_name");

        for (Party p : parties)
            if (p.getName().equals(name))
                party = p;

        if (party != null) {
            party.addUser(user);

            user.sendMessage(builder.put("s_joined", party.getName()).get());
        } else {
            throw new FullPartyException();
        }
    }

    /**
     * Initializing the server
     */
    void init() {
        parties = new ArrayList<>();

        try {
            sSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for users
        new Thread(emptySocket).start();
    }

    /**
     * Stops the server
     */
    public void stop() throws IOException, InterruptedException {
        waitForUsers = false;
        sSocket.close();
    }

    /**
     * Returns the list of all parties
     *
     * @return  :ist of all parties
     */
    public List<Party> getParties() {
        return parties;
    }
}
