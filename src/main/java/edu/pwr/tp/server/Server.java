package edu.pwr.tp.server;

import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.party.Options;
import edu.pwr.tp.server.party.Party;

import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private static volatile Server instance;

    private List<Party> parties = new ArrayList<>();

    public static Server getServer() {
        if (instance == null) {
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server();
                }
            }
        }

        return instance;
    }

    void createParty(Options options) {
        //GameModel model = options.getFactory().getModel();
    }

    @Override
    public void run() {

    }
}
