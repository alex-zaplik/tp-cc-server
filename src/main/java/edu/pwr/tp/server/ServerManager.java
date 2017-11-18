package edu.pwr.tp.server;

import edu.pwr.tp.server.model.IGameModel;

public class ServerManager {

    private static volatile ServerManager instance = null;
    private ServerManager(){}
    /**
     *
     * @return Returns instance of singleton
     */
    public static synchronized ServerManager getInstance() {
        if(instance == null){
            instance = new ServerManager();
        }
        return instance;
    }

    /**
     * creates a game
     * @return nothing yet
     */
    public IGameModel createGame(){
        // TODO
        return null;
    }



}
