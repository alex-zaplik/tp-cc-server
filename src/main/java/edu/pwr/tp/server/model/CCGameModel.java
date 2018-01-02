package edu.pwr.tp.server.model;

import edu.pwr.tp.server.exceptions.FieldBusyException;
import edu.pwr.tp.server.model.elements.Pawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCField;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;

public class CCGameModel extends GameModel {

    public CCGameModel(){
        super();
    }
    @Override
    public boolean movePawn(int playerID, int fromX, int fromY, int toX, int toY)
        {
            if (validateMove(fromX, fromY, toX, toY)) {
                Pawn pawn = getPawnAt(fromX,fromY);
                board.removePawnFrom(fromX, fromY);
                try {
                    board.putPawn(toX, toY, pawn);
                    if((((CCPawn) pawn).getColorID()+3)%6==((CCField)board.getField(toX,toY)).getBaseID()) ((CCPawn) pawn).setInBase(true);
                } catch (FieldBusyException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            return false;
        }

}
