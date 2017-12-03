package edu.pwr.tp.server.party;

import edu.pwr.tp.server.ServerManager;
import edu.pwr.tp.server.model.GameModel;

import java.util.ArrayList;

public class Party implements Runnable {

    private GameModel gameModel;
    private ArrayList<Slot> slots;

    /**
     * creates a Party. Party newly created has all slots empty
     */
    public Party(){
        gameModel = ServerManager.getInstance().createGame();
        slots = new ArrayList<>();

        // TODO: Fix after merge
        for(int i=0; i<gameModel.getMaxSlots(); i++)
            slots.add(new Slot(null));
    }

    /**
     * These are the slots that are available to play. Who is not connected to the slot is only spectator
     * @return slots of this party
     */
    public ArrayList<Slot> getSlots() {
        return slots;
    }

    /**
     *
     * @return Model of a game that is played in this Party
     */

    public GameModel getGameModel() {
        return gameModel;
    }

    @Override
    public void run() {

    }

    /* TODO: fill this after implementing a BOT

    public void createBot(int index) throws SlotTakenException{
    }

     */

}
