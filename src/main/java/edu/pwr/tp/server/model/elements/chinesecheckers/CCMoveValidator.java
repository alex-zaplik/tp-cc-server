package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.MoveValidator;

public class CCMoveValidator extends MoveValidator {
    @Override
    public boolean validate(Board b, int fromX, int fromY, int toX, int toY) {
        if(b.getPawnAt(toX, toY) != null) return false;
        if(b.getPawnAt(fromX, fromY) == null) return false;
        int diffX = (fromX-toX);
        int diffY = (fromY-toY);
        if(diffX>1||diffX<-1||diffY>1||diffY<-1) return false; //TODO: legalize jumping
        if(diffX+diffY==2||diffX+diffY==-2) return false; // this situation is where the Pawn wants to move through wrong diagonal
        return true;
    }
}
