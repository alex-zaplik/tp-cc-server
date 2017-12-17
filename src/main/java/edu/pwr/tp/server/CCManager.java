package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumantsException;
import edu.pwr.tp.server.model.factories.GameModelFactory;

public class CCManager extends GameManager {

    public CCManager(GameModelFactory factory, int players) throws InvalidArgumantsException {
        super(factory, players);

        if (players > 6 || players < 2) {
            throw new InvalidArgumantsException();
        }
    }

    @Override
    public void initGame() {
        // TODO
    }

    @Override
    public boolean handleMove(int fromX, int fromY, int toX, int toY) {
        return model.movePawn(fromX, fromY, toX, toY);
    }

    @Override
    public void nextPlayer() {
        // TODO
    }
}
