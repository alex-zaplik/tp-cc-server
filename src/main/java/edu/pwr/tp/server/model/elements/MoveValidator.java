package edu.pwr.tp.server.model.elements;

/**
 * this class has job to validate any move in a game and check if this move is legal
 * @author Jarosław Nigiel
 */
public abstract class MoveValidator {

    /**
     * it should check for any illegal moves for a specific game
     * @param b board on witch move will be checked
     * @param fromX x coordinate from witch pawn wants to be moved
     * @param fromY y coordinate from witch pawn wants to be moved
     * @param toX x coordinate to witch pawn wants to move
     * @param toY y coordinate to witch pawn wants to move
     * @return should return return legality of the move
     */
    public abstract boolean validate(Board b, int fromX, int fromY, int toX, int toY);
}
