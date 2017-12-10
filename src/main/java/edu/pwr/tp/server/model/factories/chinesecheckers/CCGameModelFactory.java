package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCMoveValidator;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;
import edu.pwr.tp.server.model.factories.GameModelFactory;

import java.util.ArrayList;

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
            switch (i){
                case 0:
                    for(int j=4; j<=7; j++)
                        for(int k=4; k<=7; k++)
                            if(j+k<=11)
                                board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                    break;
                case 1:
                    for(int j=13; j<=16; j++)
                        for(int k=4; k<=7;k++)
                            if(j+k<=20)
                                board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                    break;
                case 2:
                    for(int j=4; j<=7; j++)
                        for(int k=13; k<=16;k++)
                            if(j+k<=20)
                                board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                    break;
                case 3:
                    for(int j=0; j<=3; j++)
                        for(int k=9; k<=12; k++)
                            if(j+k>=12)
                                board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                    break;
                case 4:
                    for(int j=9; j<=12; j++)
                        for(int k=9; k<=12; k++)
                            if(j+k>=21)
                                board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                    break;
                case 5:
                    for(int j=9; j<=12; j++)
                        for(int k=0; k<=3; k++)
                            if(j+k>=12)
                                board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                    break;
            }
        }
        model.setBoard(board);
        model.setPlayers(ccPlayers);
        model.setValidator(new CCMoveValidator());
        return model;
    }
}
