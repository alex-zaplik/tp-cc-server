package edu.pwr.tp.server.model.elements;

public abstract class MoveValidator {

    public abstract boolean validate(Board b, int pawnID, int toX, int toY);
}
