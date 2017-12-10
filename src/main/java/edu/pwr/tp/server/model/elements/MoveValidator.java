package edu.pwr.tp.server.model.elements;

public abstract class MoveValidator {

    public abstract boolean validate(Board b, int fromX, int fromY, int toX, int toY);
}
