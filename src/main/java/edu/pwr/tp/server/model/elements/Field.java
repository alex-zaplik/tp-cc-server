package edu.pwr.tp.server.model.elements;

import edu.pwr.tp.server.exceptions.FieldBusyException;

/**
 * this class represents physical Field of the Board. It has its own coordinates and has information if a Pawn stays at that Field or not
 * @author Jarosław Nigiel
 */
public abstract class Field {
    private int x, y;
    private Pawn pawn;

    /**
     * classical getter
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * classical getter
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * classical getter
     * @return Pawn that stays at this Field or null
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * classical setter
     * @param pawn pawn to set
     * @throws FieldBusyException thrown if Field is already taken by another Pawn
     */
    public void setPawn(Pawn pawn) throws FieldBusyException {
        if(this.pawn!=null) throw new FieldBusyException();
        this.pawn = pawn;
    }

    /**
     * deletes a Pawn from this Field
     * @return returns true if Pawn was succesfully removed
     */
    public boolean deletePawn(){
        if(pawn == null)
            return false;
        pawn = null;
        return true;
    }

    /**
     * classical setter
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * creates a Field with given coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public Field(int x, int y){
        this(x,y,null);
    }

    /**
     * creates a Field with given coordinates and sets a Pawn
     * @param x x coordinate
     * @param y y coordinate
     * @param pawn pawn that will be put here
     */
    public Field(int x, int y, Pawn pawn){
        this.setPosition(x,y);
        this.pawn = pawn;
    }
}
