package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Pawn;

/**
 * this is Pawn class for Chinese Checkers game
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.elements.Pawn
 */
public class CCPawn extends Pawn {

    private int colorID;

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    /**
     * creates new Pawn
     * @param playerID owner of the Pawn
     */
    public CCPawn(int playerID) {
        super(playerID);
    }
}
