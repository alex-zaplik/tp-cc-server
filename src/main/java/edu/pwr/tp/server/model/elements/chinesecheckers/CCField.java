package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Field;

/**
 * this is a Field class for Chinese Checkers game, you can have there pawns or not
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.elements.Field
 */
public class CCField extends Field {

    private int baseID;

    public int getBaseID() {
        return baseID;
    }

    /**
     * creates Field and sets his coordinates to x and y
     * @param x x coordinate
     * @param y y coordinate
     */
    public CCField(int x, int y, int baseID) {
        super(x, y);
        this.baseID=baseID;
    }
}
