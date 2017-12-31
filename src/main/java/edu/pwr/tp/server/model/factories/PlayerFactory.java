package edu.pwr.tp.server.model.factories;

import edu.pwr.tp.server.model.elements.Player;

/**
 * Factories that inherit that class should create a Player
 * @author Jarosław Nigiel
 * @see Player
 */
public abstract class PlayerFactory {
    /**
     * should create a Player
     * @return should return a Player created
     */
    public abstract Player createPlayer();
}
