package edu.pwr.tp.server.model.elements;

public abstract class Board {

    protected Field[][] fields;

    public Field getField(int x, int y) {
        return fields[x][y];
    }

    public abstract Pawn getPawnAt(int x, int y);
    public abstract boolean removePawnFrom(int x, int y);
    public abstract void putPawn(int x, int y, Pawn pawn);

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public Pawn getPawnByID(int id) {
        Pawn pawn = null;
        for (Field[] f : fields)
            for (Field p : f)
                if (p.getPawn() != null)
                    if (p.getPawn().getID() == id) {
                        pawn = p.getPawn();
                    }
        return pawn;
    }

    public boolean removePawnByID(int id) {
        for (Field[] f : fields)
            for (Field p : f)
                if (p.getPawn() != null)
                    if (p.getPawn().getID() == id) {
                        p.deletePawn();
                        return true;
                    }
        return false;
    }
}
