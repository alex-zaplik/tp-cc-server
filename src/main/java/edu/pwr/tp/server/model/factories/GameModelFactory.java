package edu.pwr.tp.server.model.factories;

import edu.pwr.tp.server.model.GameModel;

/**
 * Factories that inherit this class should create GameModel that is ready to play, its a singleton
 * @author Jarosłąw Nigiel
 * @see GameModel
 */
public abstract class GameModelFactory {

    protected BoardFactory boardFactory;
    protected PawnFactory pawnFactory;
    protected PlayerFactory playerFactory;

    protected GameModelFactory(){}

    /**
     * does nothing. Abstract classes don't have instances
     * @return returns null
     */
    public static GameModelFactory getInstance(){
        return null;
    }

    /**
     * should create GameModel
     * @param players number of players
     * @return should return created GameModel
     */
    public abstract GameModel createModel(int players);
}
