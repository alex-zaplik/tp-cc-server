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
                if(x<=12&&y<=12&&x+y>=12){
                    if(x<=3&&y>=9) fields[x][y] = new CCField(x,y,1);
                    else if (x>=9&&y<=3) fields [x][y] = new CCField(x,y,3);
                    else if (x+y>=21&&x>=9&&y>=9) fields[x][y] = new CCField(x,y,5);
                    else fields[x][y] = new CCField(x, y, -1);
                }
                else if(x>=4&&y>=4&&x+y<=20){
                    if(x<=7&&y>=13) fields[x][y] = new CCField(x,y,0);
                    else if (x+y<=11&&x<=7&&y<=7) fields[x][y] = new CCField(x,y,2);
                    else if (x>=13&&y<=7) fields[x][y] = new CCField(x,y,4);
                    else fields[x][y] = new CCField(x,y, -1);
                }
                else fields[x][y] = null;
            }
        return new CCBoard(fields);
    }
}
