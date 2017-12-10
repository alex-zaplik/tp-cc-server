package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.CreatingPartyFailedException;
import edu.pwr.tp.server.exceptions.FullPartyException;
import edu.pwr.tp.server.message.builder.IMessageBuilder;
import edu.pwr.tp.server.message.builder.JSONMessageBuilder;
import edu.pwr.tp.server.message.parser.IMessageParser;
import edu.pwr.tp.server.message.parser.JSONMessageParser;
import edu.pwr.tp.server.party.Party;
import edu.pwr.tp.server.user.ConnectedUser;

import java.io.IOException;
import java.net.ServerSocket;
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
    private IMessageParser parser;
    /**
     * Used to build messages that are to be sent throw a socket
     */
    private IMessageBuilder builder;

    /**
     * The ID of the last user that connected to the server
     */
    private volatile int lastID = 0;

    /**
     * Waits for a new user
     */
    private Runnable emptySocket = new Runnable() {
        @Override
        public void run() {
            if (sSocket != null && parties != null) {
                while (true) {
                    ConnectedUser user;
                    try {
                        user = new ConnectedUser(sSocket.accept(), getID());
                        new Thread(() -> {
                            try {
                                setUpConnection(user);
                            } catch (IOException e) {
                                e.printStackTrace();
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
            int action = (int) responseMap.get("i_action");

            switch (action) {
                case 0:
                    try {
                        Party p = createParty(responseMap);

                        if (p == null)
                            throw new CreatingPartyFailedException();

                        p.addUser(user);
                    } catch (CreatingPartyFailedException | FullPartyException e) {
                        // TODO: Try again
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        joinParty(user, responseMap);
                    } catch (FullPartyException e) {
                        // TODO: Try again
                        e.printStackTrace();
                    }
                    break;
                default:
                    throw new IOException("Unsupported action");
            }
        }
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

        for (int i = parties.size() - 1; i >= 0; i--) {

            user.getOut().println(
                    builder.put("i_size", parties.size())
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
    private synchronized Party createParty(Map<String, Object> settings) throws CreatingPartyFailedException {
        Party party;
        String name = (String) settings.get("s_name");

        int max = (int) settings.get("i_max");

        for (Party p : parties)
            if (p.getName().equals(name))
                throw new CreatingPartyFailedException();

        party = new Party(max, name);
        parties.add(party);
        new Thread(party).start();

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
        } else {
            throw new FullPartyException();
        }
    }

    /**
     * Initializing the server
     */
    void init() {
        parser = new JSONMessageParser();
        builder = new JSONMessageBuilder();

        parties = new ArrayList<>();

        // TODO: Remove testing parties
        Party p1 = new Party(10,"Test1");
        parties.add(p1);
        new Thread(p1).start();
        Party p2 = new Party(15,"Test2");
        parties.add(p2);
        new Thread(p2).start();

        try {
            sSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for users
        new Thread(emptySocket).start();
    }
}
