package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.Pawn;

public class CCBoard extends Board {

    public static final int pawnsPerPlayer = 10;

    @Override
    public Pawn getPawnAt(int x, int y) {
        return this.getField(x,y).getPawn();
    }

    @Override
    public boolean removePawnFrom(int x, int y) {
        return this.getField(x,y).deletePawn();
    }

    @Override
    public void putPawn(int x, int y, Pawn pawn) {
        this.getField(x,y).setPawn(pawn);
    }

    public CCBoard(CCField[][] fields){
        this.fields = fields;
    }

}
