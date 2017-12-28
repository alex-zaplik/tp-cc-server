package edu.pwr.tp.server.model;

import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;
import edu.pwr.tp.server.model.factories.chinesecheckers.CCGameModelFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {

    GameModel model = null;

    @Before
    public void setUp(){
        model = CCGameModelFactory.getInstance().createModel(6);
    }

    @Test
    public void testPawnOperations(){
        CCPawn pawn = new CCPawn(model.getPlayers()[0].getID());
        model.getBoard().putPawn(4,9, pawn);
        assertEquals(pawn, model.getPawnAt(4,9));
        assertTrue(model.getBoard().removePawnFrom(4,9));
        assertNull(model.getPawnAt(4,9));
    }

    @Test
    public void testValidator1(){
        CCPawn pawn = new CCPawn(model.getPlayers()[0].getID());
        model.getBoard().putPawn(4,9, pawn);
        model.movePawn(0, 4,9,5,8);
        assertNull(model.getPawnAt(4,9));
        assertEquals(model.getPawnAt(5,8),pawn);
    }

}