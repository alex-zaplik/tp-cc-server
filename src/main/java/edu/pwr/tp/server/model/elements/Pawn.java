package edu.pwr.tp.server.model.elements;

public abstract class Pawn {

    private int ID;
    private int playerID;
    private int x, y;

    public Pawn(int ID, int playerID, int x, int y) {
        this.ID = ID;
        this.playerID = playerID;
        setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getID() {
        return ID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
