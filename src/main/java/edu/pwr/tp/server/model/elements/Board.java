package edu.pwr.tp.server.model.elements;

import java.util.List;

public abstract class Board {

    private List<Pawn> pawns;

    public abstract Pawn getPawnAt(int x, int y);

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public Pawn getPawn(int id) {
        Pawn pawn = null;
        for (Pawn p : pawns)
            if (p.getID() == id)
                pawn = p;
        return pawn;
    }

    public void removePawn(int id) {
        for (Pawn p : pawns)
            if (p.getID() == id)
                pawns.remove(p);
    }
}
