package edu.pwr.tp.server.model;

public abstract class GameFactory {

    protected GameModel model;
  
    public abstract void buildPawn(int players);
    public abstract void buildBoard();
    public abstract void buildPlayer(int count);
    public abstract void buildValidator();

    public GameModel getModel() {
        return model;
    }
}
