package edu.pwr.tp.server.model.elements;

import edu.pwr.tp.server.exceptions.FieldBusyException;

/**
 * this is Board class. It consists of fields and have some methods to access these fields.
 * @author Jarosław Nigiel
 */
public abstract class Board {

    protected Field[][] fields;

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return returns a field at given coordinates
     */
    public Field getField(int x, int y) {
        try{
            return fields[x][y];
        } catch (ArrayIndexOutOfBoundsException ex){
            return null;
        }
    }

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return should return Pawn if exists from a field of given coordinates
     */
    public abstract Pawn getPawnAt(int x, int y);

    /**
     * should remove Pawn from given coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @return should return true if a Pawn was successfully removed
     */
    public abstract boolean removePawnFrom(int x, int y);

    /**
     * should put Pawn to given coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @param pawn pawn to put
     */
    public abstract void putPawn(int x, int y, Pawn pawn) throws FieldBusyException;

    /**
     * classical setter
     * @param fields fields to set
     */
    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    /**
     *
     * @deprecated
     * @param id id of pawn
     * @return returns a pawn of given ID or null if that Pawn does not exist in a Board
     */
    public Pawn getPawnByID(int id) {
        Pawn pawn = null;
        for (Field[] f : fields)
            for (Field p : f)
                if (p.getPawn() != null)
                    if (p.getPawn().getID() == id) {
                        pawn = p.getPawn();
                    }
        return pawn;
    }

    /**
     * removes a Pawn from a Board by given id
     * @deprecated
     * @param id id of pawn
     * @return returns true if pawn was successfully removed
     */
    public boolean removePawnByID(int id) {
        for (Field[] f : fields)
            for (Field p : f)
                if (p.getPawn() != null)
                    if (p.getPawn().getID() == id) {
                        p.deletePawn();
                        return true;
                    }
        return false;
    }

    public abstract boolean playerWon(int color);
}
