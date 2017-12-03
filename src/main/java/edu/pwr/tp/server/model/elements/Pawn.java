package edu.pwr.tp.server.model.elements;

public abstract class Pawn {

    private int playerID;

    public Pawn(int playerID) {
        this.playerID = playerID;
    }

    /**
     * @deprecated
     * @return
     */
    public int getID() {
        return this.hashCode();
    }

    public int getPlayerID() {
        return playerID;
    }
}
