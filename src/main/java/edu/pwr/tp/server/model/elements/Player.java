package edu.pwr.tp.server.model.elements;

/**
 * this Class represents a Player.
 * @author Jarosław Nigiel
 */
public abstract class Player {
    /**
     * @return returns hashCode of the Player
     * @see Object#hashCode()
     */
    public int getID() {
        return this.hashCode();
    }
}
