package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.elements.Pawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;
import edu.pwr.tp.server.model.factories.PawnFactory;

public class CCPawnFactory extends PawnFactory{
    @Override
    public Pawn createPawn(int playerID) {
        return new CCPawn(playerID);
    }

    public Pawn createPawn(int playerID, int colorID){
        CCPawn pawn = (CCPawn) createPawn(playerID);
        pawn.setColorID(colorID);
        return pawn;
    }

}






