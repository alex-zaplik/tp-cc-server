package edu.pwr.tp.server.model.elements.chinesecheckers;


import edu.pwr.tp.server.model.factories.chinesecheckers.CCGameModelFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class CCMoveValidatorTest {

    @Test
    public void testValidate(){

        CCMoveValidator moveValidator = new CCMoveValidator();
        CCBoard board = (CCBoard) CCGameModelFactory.getInstance().createModel(6).getBoard();
        assertTrue(moveValidator.validate(board,4,7,5,7));
        assertFalse(moveValidator.validate(board,4,4,5,4));
        assertFalse(moveValidator.validate(board,4,4,4,3));
        assertFalse(moveValidator.validate(board,4,7,5,8));

    }

}