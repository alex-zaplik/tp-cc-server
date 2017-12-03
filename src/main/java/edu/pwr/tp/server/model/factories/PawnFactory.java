package edu.pwr.tp.server.model.factories;

import edu.pwr.tp.server.model.elements.Pawn;

public abstract class PawnFactory {
    public abstract Pawn createPawn(int playerID);
}
