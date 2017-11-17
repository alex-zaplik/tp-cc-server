package edu.pwr.tp.server;

public class Pawn {
    int id, x, y;

    public Pawn(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Position: [" + x + ", " + y + "]";
    }
}