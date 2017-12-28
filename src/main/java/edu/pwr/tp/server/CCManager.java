package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.factories.GameModelFactory;

import java.util.List;

public class CCManager extends GameManager {

    public CCManager(GameModelFactory factory, int players) throws InvalidArgumentsException {
        super(factory, players);

        if (players > 6 || players < 2) {
            throw new InvalidArgumentsException();
        }
    }

    public void init(List<Integer> userIDs) {
        // TODO: model.getPlayers().length != userIDs.size()

        model = new GameModel();

        // TODO: Init the game

        for (int p = 0; p < model.getPlayers().length; p++) {
            userToPlayer.put(userIDs.get(p), model.getPlayers()[p].getID());
        }
    }

    public boolean makeMove(int userID, int fx, int fy, int tx, int ty) {
        System.out.println("GameManager: " + userID + " is making a move");
        return model.movePawn(userToPlayer.get(userID), fx, fy, tx, ty);
    }

    public int getPlayerCount() {
        return model.getPlayers().length;
    }
}
