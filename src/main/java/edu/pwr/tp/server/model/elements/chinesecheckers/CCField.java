package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Field;

/**
 * this is a Field class for Chinese Checkers game, you can have there pawns or not
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.elements.Field
 */
public class CCField extends Field {

    /**
     * creates Field and sets his coordinates to x and y
     * @param x x coordinate
     * @param y y coordinate
     */
    public CCField(int x, int y) {
        super(x, y);
    }

    /**
     * creates Field and sets his coordinates to x and y, moreover puts a pawn on created Field
     * @param x x coordinate
     * @param y y coordinate
     * @param pawn pawn that will be put on the new Field
     */
    public CCField(int x, int y, CCPawn pawn) {
        super(x, y, pawn);
    }
}
