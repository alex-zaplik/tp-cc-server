package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.MoveValidator;

public class CCMoveValidator extends MoveValidator {
    @Override
    public boolean validate(Board b, int pawnID, int toX, int toY) {
        return false;
    }
}
