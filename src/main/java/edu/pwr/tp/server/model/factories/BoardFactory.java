package edu.pwr.tp.server.model.factories;

import edu.pwr.tp.server.model.elements.Board;

/**
 * Factories that inherit this class should create a Board
 * @author Jarosław Nigiel
 */
public abstract class BoardFactory {
    /**
     * this method should create a Board
     * @return should return Board created
     */
    public abstract Board createBoard();
}
