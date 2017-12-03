package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCMoveValidator;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;
import edu.pwr.tp.server.model.factories.GameModelFactory;

public class CCGameModelFactory extends GameModelFactory {

    private CCGameModelFactory(){
        boardFactory = new CCBoardFactory();
        pawnFactory = new CCPawnFactory();
        playerFactory = new CCPlayerFactory();
    }

    private static volatile CCGameModelFactory instance;

    public static GameModelFactory getInstance(){
        if(instance==null){
            synchronized (CCGameModelFactory.class){
                if(instance==null){
                    instance = new CCGameModelFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public GameModel createModel(int players) {
        GameModel model = new GameModel();
        CCBoard board = (CCBoard) boardFactory.createBoard();
        CCPlayer[] ccPlayers = new CCPlayer[players];
        for(int i = 0; i<players; i++){
            ccPlayers[i] = (CCPlayer) playerFactory.createPlayer();
            CCPawn[] pawns = new CCPawn[CCBoard.pawnsPerPlayer];
            for(int j=0; j<CCBoard.pawnsPerPlayer; j++){
                pawns[j] = (CCPawn) pawnFactory.createPawn(ccPlayers[i].hashCode());
                //TODO : place pawns on the board
            }
        }
        model.setBoard(board);
        model.setPlayers(ccPlayers);
        model.setValidator(new CCMoveValidator());
        return model;
    }
}
