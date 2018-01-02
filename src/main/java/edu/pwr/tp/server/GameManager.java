package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.factories.GameModelFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class managing a {@link GameModel}, a link between the server and a model
 *
 * @author Aleksander Lasecki
 */
public abstract class GameManager {

    /**
     * A map that maps user IDs (server-side) to player IDs (model-side)
     */
    Map<Integer, Integer> userToPlayer = new HashMap<>();

    public int getPlayerID(int userID){
        return userToPlayer.get(userID);
    }

    /**
     * The model managed by this manager
     */
    protected GameModel model;

    /**
     * Main constructor
     *
     * @param factory                       Factory for the model
     * @param players                       The number of players
     * @throws InvalidArgumentsException    Thrown if the number of players is incorrect
     */
    public GameManager(GameModelFactory factory, int players) throws InvalidArgumentsException {
        if (players < 2)
            throw new InvalidArgumentsException();

        model = factory.createModel(players);
    }

    /**
     * Map initialization
     *
     * @param userIDs                       User IDs (server-side)
     */
    public abstract void init(List<Integer> userIDs);

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
    public abstract boolean makeMove(int userID, int fx, int fy, int tx, int ty);

    /**
     * Checks if the user specified by the ID is a winner
     *
     * @param userID    The ID of the user we want to check
     * @return          True if the user is a winner, false otherwise
     */
    public abstract boolean isWinner(int userID);
    /**
     * Checks if anyone is a winner
     *
     * @return  True if someone is a winner, false otherwise
     */
    public abstract boolean someoneWon();

    /**
     * Returns the number of players in a party
     *
     * @return  The number of players in a party
     */
    public int getPlayerCount() {
        return model.getPlayers().length;
    }
    /**
     * Returns the model managed by this manager
     *
     * @return  The model managed by this manager
     */
    public GameModel getModel() {
        return model;
    }
}
