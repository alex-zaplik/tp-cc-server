package edu.pwr.tp.server.party;

import edu.pwr.tp.server.model.factories.GameModelFactory;

public class Options {
    private GameModelFactory factory;
    private int maxTime;
    private int maxTurnTime;
    private int playerCount;

    public Options(GameModelFactory factory, int maxTime, int maxTurnTime, int playerCount) {
        this.factory = factory;
        this.maxTime = maxTime;
        this.maxTurnTime = maxTurnTime;
        this.playerCount = playerCount;
    }

    public GameModelFactory getFactory() {
        return factory;
    }

    public void setFactory(GameModelFactory factory) {
        this.factory = factory;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxTurnTime() {
        return maxTurnTime;
    }

    public void setMaxTurnTime(int maxTurnTime) {
        this.maxTurnTime = maxTurnTime;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
