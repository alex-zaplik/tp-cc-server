package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Field;

public class CCField extends Field{
    public CCField(int x, int y){
        super(x,y);
    }
    public CCField(int x, int y, CCPawn pawn){
        super(x,y,pawn);
    }
}
