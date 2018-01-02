package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.CCGameModel;
import edu.pwr.tp.server.model.elements.Player;
import edu.pwr.tp.server.model.factories.chinesecheckers.CCGameModelFactory;

import java.util.List;

/**
 * A class managing a {@link CCGameModel}, a link between the server and a model
 *
 * @author Aleksander Lasecki
 */
public class CCManager extends GameManager {

    /**
     * Main constructor
     *
     * @param players                       The number of players
     * @throws InvalidArgumentsException    Thrown if the number of players is incorrect
     */
    public CCManager(int players) throws InvalidArgumentsException {
        super(CCGameModelFactory.getInstance(), players);

        if (players > 6 || players == 5) {
            throw new InvalidArgumentsException();
        }
    }

    /**
     * Map initialization
     *
     * @param userIDs                       User IDs (server-side)
     */
    public void init(List<Integer> userIDs) {
        for (int p = 0; p < model.getPlayers().length; p++) {
            userToPlayer.put(userIDs.get(p), model.getPlayers()[p].getID());
        }
    }

    /**
     * Performs a move on the model
     *
     * @param userID    ID of the user attempting a move
     * @param fx        From X
     * @param fy        From Y
     * @param tx        To X
     * @param ty        To Y
     * @return          True if the move was valid, false otherwise
     */
    public boolean makeMove(int userID, int fx, int fy, int tx, int ty) {
        System.out.println("GameManager: " + userID + " is making a move");
        return model.movePawn(userToPlayer.get(userID), fx, fy, tx, ty);
    }

    /**
     * Checks if the user specified by the ID is a winner
     *
     * @param userID    The ID of the user we want to check
     * @return          True if the user is a winner, false otherwise
     */
    @Override
    public boolean isWinner(int userID) {
        int playerID = userToPlayer.get(userID);
        return model.playerWon(playerID);
    }

    /**
     * Checks if anyone is a winner
     *
     * @return  True if someone is a winner, false otherwise
     */
    @Override
    public boolean someoneWon() {
        for (Player player: model.getPlayers())
            if(model.playerWon(player.getID())) return true;
        return false;
    }
}
