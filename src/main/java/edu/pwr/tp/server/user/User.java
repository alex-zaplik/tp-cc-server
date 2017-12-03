package edu.pwr.tp.server.user;

import edu.pwr.tp.server.exceptions.CantPlayWithYourselfException;
import edu.pwr.tp.server.exceptions.SlotTakenException;
import edu.pwr.tp.server.party.Party;

/**
 * This is a player/spectator class that will be connected to the Party
 */
public abstract class User {


    protected int userID;
    /**
     * is true when user is a player
     * is false when user is only spectator
     */
    protected boolean isPlayer = false;
    protected int slotID = -1;

    protected Party party;

    /**
     *
     * @return A party that User is connected to
     */
    public Party getParty() {
        return party;
    }

    /**
     *
     * @return ID of User
     */
    public int getUserID(){
        return userID;
    }

    /**
     *
     * @param idx index of Slot in party Slots
     * @throws SlotTakenException thrown when Slot is already Taken
     * @throws CantPlayWithYourselfException thrown when already is a player
     * @throws IllegalArgumentException thrown when idx is wrong
     */
    public void joinSlot(int idx) throws SlotTakenException, CantPlayWithYourselfException, IllegalArgumentException{
        if(isPlayer)
            throw new CantPlayWithYourselfException();

        // TODO: Fix after merge
        if(idx >= party.getGameModel().getMaxSlots())
            throw new IllegalArgumentException();

        party.getSlots().get(idx).setPlayer(this);
        slotID = idx;
        isPlayer = true;
    }

    /**
     * leaves the Slot
     */
    public void leaveSlot(){
        if(isPlayer){
            party.getSlots().get(slotID).leaveSlot();
        }
        isPlayer=false;
    }


}
