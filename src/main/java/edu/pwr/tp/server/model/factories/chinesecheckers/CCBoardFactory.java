package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCField;
import edu.pwr.tp.server.model.factories.BoardFactory;

/**
 * this Factory creates a Board with empty fields, for a Chinese Checkers game
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.factories.BoardFactory
 */
public class CCBoardFactory extends BoardFactory{
    /**
     * creates a CCBoard
     * @return returns Board created
     */
    @Override
    public Board createBoard() {
        CCField[][] fields = new CCField[17][17];
        for(int x=0; x<17; x++)
            for(int y=0; y<17; y++){
                if(x<=12&&y<=12&&x+y>=12) fields[x][y] = new CCField(x, y);
                else if(x>=4&&y>=4&&x+y<=20) fields[x][y] = new CCField(x,y);
                else fields[x][y] = null;
            }
        return new CCBoard(fields);
    }
}
