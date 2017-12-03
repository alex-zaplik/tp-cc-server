package edu.pwr.tp.server.model.elements;

public abstract class Field {
    private int x, y;
    private Pawn pawn;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public boolean deletePawn(){
        if(pawn == null)
            return false;
        pawn = null;
        return true;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Field(int x, int y){
        this(x,y,null);
    }

    public Field(int x, int y, Pawn pawn){
        this.setPosition(x,y);
        this.pawn = pawn;
    }
}
