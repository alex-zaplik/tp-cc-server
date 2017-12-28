package edu.pwr.tp.server.model.elements;

/**
 * this class represent physical object of Pawn in the game. It remembers his owner (Player)
 * @author Jarosław Nigiel
 */
public abstract class Pawn {

    private int playerID;

    /**
     * creates a Pawn
     * @param playerID owner of the Pawn
     */
    public Pawn(int playerID) {
        this.playerID = playerID;
    }

    /**
     * standard getter
     * @deprecated
     * @return returns hashCode of Pawn
     * @see Object#hashCode()
     */
    public int getID() {
        return this.hashCode();
    }

    /**
     * standard getter
     * @return returns owner of this Pawn
     */
    public int getPlayerID() {
        return playerID;
    }
}
