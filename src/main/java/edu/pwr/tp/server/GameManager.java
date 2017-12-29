package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.factories.GameModelFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameManager {

    Map<Integer, Integer> userToPlayer = new HashMap<>();

    protected GameModel model;

    public GameManager(GameModelFactory factory, int players) throws InvalidArgumentsException {
        if (players < 2)
            throw new InvalidArgumentsException();

        model = factory.createModel(players);
    }

    public abstract void init(List<Integer> userIDs) throws InvalidArgumentsException;
    public abstract boolean makeMove(int userID, int fx, int fy, int tx, int ty);

    public int getPlayerCount() {
        return model.getPlayers().length;
    }
}
