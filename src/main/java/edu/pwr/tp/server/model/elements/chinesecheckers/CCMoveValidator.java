package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.MoveValidator;

/**
 * this is MoveValidator class for Chinese Checkers game
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.elements.MoveValidator
 */
public class CCMoveValidator extends MoveValidator {
    /**
     * checks if move is legal at given parameters
     * @param b Board witch will be validated
     * @param fromX the x coordinate from witch Pawn wants to be moved
     * @param fromY the y coordinate from witch Pawn wants to be moved
     * @param toX the x coordinate where Pawn wants to be moved
     * @param toY the y coordinate where Pawn wants to be moved
     * @return returns true if move is legal
     */
    @Override
    public boolean validate(Board b, int fromX, int fromY, int toX, int toY) {
        if(b.getField(fromX,fromY)==null) return false;
        if(b.getField(toX,toY)==null) return false;
        if(b.getPawnAt(toX, toY) != null) return false;
        if(b.getPawnAt(fromX, fromY) == null) return false;
        if (((CCPawn)b.getPawnAt(fromX, fromY)).isInBase()&&(((CCPawn)b.getPawnAt(fromX, fromY)).getColorID()!=(((CCField) b.getField(toX,toY)).getBaseID()+3)%6)) return false;
        if(((CCBoard) b).getJumpingPawn()!=null&&b.getPawnAt(fromX,fromY)!=((CCBoard) b).getJumpingPawn()) return false;
        int diffX = (fromX-toX);
        int diffY = (fromY-toY);
        if(diffX>2||diffX<-2||diffY>2||diffY<-2) return false;
        if((Math.abs(diffX)==2||diffX==0)&&(Math.abs(diffY)==2||diffY==0)){
            if(b.getPawnAt((fromX+toX)/2,(fromY+toY)/2)==null) return false;
            else ((CCBoard) b).setJumpingPawn((CCPawn) b.getPawnAt(fromX,fromY));
        }
        else{
            if (((CCBoard) b).getJumpingPawn()!=null) return false;
            if((Math.abs(diffX)==2&&Math.abs(diffY)==1)||(Math.abs(diffX)==1&&Math.abs(diffY)==2)) return false;
        }
        if(diffX*diffY>0) return false; // this situation is where the Pawn wants to move through wrong diagonal
        return true;
    }
}
