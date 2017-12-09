package edu.pwr.tp.server.party;

import edu.pwr.tp.server.model.GameFactory;

// TODO: Apply this class in the protocol

public class Options {
    private GameFactory factory;
    private int maxTime;
    private int maxTurnTime;
    private int playerCount;
    private int pawnsPerPlayer;

    public Options(GameFactory factory, int maxTime, int maxTurnTime, int playerCount, int pawnsPerPlayer) {
        this.factory = factory;
        this.maxTime = maxTime;
        this.maxTurnTime = maxTurnTime;
        this.playerCount = playerCount;
        this.pawnsPerPlayer = pawnsPerPlayer;
    }

    public GameFactory getFactory() {
        return factory;
    }

    public void setFactory(GameFactory factory) {
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

    public int getPawnsPerPlayer() {
        return pawnsPerPlayer;
    }

    public void setPawnsPerPlayer(int pawnsPerPlayer) {
        this.pawnsPerPlayer = pawnsPerPlayer;
    }
}
