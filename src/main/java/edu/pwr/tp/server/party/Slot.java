package edu.pwr.tp.server.party;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import edu.pwr.tp.server.exceptions.SlotTakenException;
import edu.pwr.tp.server.user.User;

public class Slot {

    private User player = null;

    /**
     *
     * @return Returns player that is playing on this Slot
     */
    public User getPlayer() {
        return player;
    }

    /**
     * Adds User to the Slot if not taken earlier
     * @param user User you want to add to this Slot
     * @throws SlotTakenException thrown when somebody plays at that Slot
     */
    public void setPlayer(@NotNull User user) throws SlotTakenException{
        if(player != null) throw new SlotTakenException();
        else{
            this.player = user;
        }
    }

    /**
     * player leaves the Slot
     */
    public void leaveSlot(){
        this.player = null;
    }

    /**
     * Creates an instance of a Slot with an user
     * @param user Player that joins the Slot during creation. Can be null.
     */
    Slot(@Nullable User user){
        player = user;
    }

    // TODO: private Color color;

}
