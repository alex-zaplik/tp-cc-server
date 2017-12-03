package edu.pwr.tp.server.model.elements;

public abstract class Player {
    public int getID() {
        return this.hashCode();
    }
}
