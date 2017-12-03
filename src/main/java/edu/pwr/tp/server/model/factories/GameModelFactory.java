package edu.pwr.tp.server.model.factories;

import edu.pwr.tp.server.model.GameModel;

public abstract class GameModelFactory {

    /*protected GameModel model;

    public abstract void buildPawn(int players);
    public abstract void buildBoard();
    public abstract void buildPlayer(int count);
    public abstract void buildValidator();

    public GameModel getModel() {
        return model;
    }*/
    protected BoardFactory boardFactory;
    protected PawnFactory pawnFactory;
    protected PlayerFactory playerFactory;

    protected GameModelFactory(){}

    public static GameModelFactory getInstance(){
        return null;
    }

    public abstract GameModel createModel(int players);
}
