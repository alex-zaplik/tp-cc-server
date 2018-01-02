package edu.pwr.tp.server.model.elements.chinesecheckers;

import edu.pwr.tp.server.exceptions.FieldBusyException;
import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.Field;
import edu.pwr.tp.server.model.elements.Pawn;

/**
 * This is Board class for a Chinese Checkers game.
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.elements.Board
 */
public class CCBoard extends Board {

    private CCPawn jumpingPawn = null;

    public CCPawn getJumpingPawn() {
        return jumpingPawn;
    }

    public void setJumpingPawn(CCPawn jumpingPawn) {
        this.jumpingPawn = jumpingPawn;
    }

    /**
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return returns a Pawn from the Field with coordinates x and y
     */
    @Override
    public Pawn getPawnAt(int x, int y) {
        return this.getField(x,y).getPawn();
    }

    /**
     * removes Pawn from the Board
     * @param x the x coordinate
     * @param y the y coordinate
     * @return returns true when Pawn was successfully removed
     */
    @Override
    public boolean removePawnFrom(int x, int y) {
        return this.getField(x,y).deletePawn();
    }

    /**
     * puts a Pawn into x,y Field
     * @param x the x coordinate
     * @param y the y coordinate
     * @param pawn Pawn that will be put to the x,y Field
     * @throws FieldBusyException
     * @see FieldBusyException
     */
    @Override
    public void putPawn(int x, int y, Pawn pawn) throws FieldBusyException {
        try {
            this.getField(x,y).setPawn(pawn);
        }
        catch (FieldBusyException ex){
            throw ex;
        }
    }

    @Override
    public boolean playerWon(int color){
        int count=0;
        for(Field[] fields1: fields)
            for (Field field: fields1){
                if(field!=null&&((CCField) field).getBaseID()!=-1&&((CCField) field).getBaseID()==(color+3)%6){
                    if (field.getPawn()==null||((CCPawn) field.getPawn()).getColorID()!=color) return false;
                    else count++;
                }
            }

        if(count==10) return true;
        else return false;
    }

    /**
     * creates CCBoard from given fields
     * @param fields fields that will consist of on the Board
     */
    public CCBoard(CCField[][] fields){
        this.fields = fields;
    }

}
