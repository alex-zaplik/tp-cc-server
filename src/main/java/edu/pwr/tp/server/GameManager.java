package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumantsException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.factories.GameModelFactory;

public abstract class GameManager {

    protected GameModel model;
    protected int currentPlayer;

    public GameManager(GameModelFactory factory, int players) throws InvalidArgumantsException {
        model = factory.createModel(players);
    }

    public abstract void initGame();
    public abstract boolean handleMove(int fromX, int fromY, int toX, int toY);
    public abstract void nextPlayer();
}
