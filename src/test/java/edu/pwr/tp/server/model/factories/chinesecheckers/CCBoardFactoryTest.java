package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CCBoardFactoryTest {

    CCBoardFactory boardFactory;

    @Test
    public void createBoard() {
        CCBoard board = (CCBoard) boardFactory.createBoard();
        assertNull(board.getField(0,0));
        assertNotNull(board.getField(4,16));
        assertNotNull(board.getField(12,0));
    }

    @Before
    public void setUp(){
        boardFactory = new CCBoardFactory();
    }
}