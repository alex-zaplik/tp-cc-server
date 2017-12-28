package edu.pwr.tp.server.model.factories;

import edu.pwr.tp.server.model.elements.Pawn;

/**
 * Factories that inherit that class should create Pawns for their game
 * @author Jarosław Nigiel
 * @see Pawn
 */
public abstract class PawnFactory {
    /**
     * should create Pawn
     * @param playerID hashCode of Pawn's owner
     * @return should return created Pawn;
     */
    public abstract Pawn createPawn(int playerID);
}
