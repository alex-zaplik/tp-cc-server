package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.exceptions.FieldBusyException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCMoveValidator;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;
import edu.pwr.tp.server.model.factories.GameModelFactory;

import java.util.ArrayList;

/**
 * this SingletonFactory creates GameModel for Chinese Checkers, ready to play
 * @author Jarosław Nigiel
 * @see edu.pwr.tp.server.model.factories.GameModelFactory
 * @see GameModel
 */
public class CCGameModelFactory extends GameModelFactory {

    private CCGameModelFactory(){
        boardFactory = new CCBoardFactory();
        pawnFactory = new CCPawnFactory();
        playerFactory = new CCPlayerFactory();
    }

    private static volatile CCGameModelFactory instance;

    /**
     *
     * @return returns Instance of this Singleton
     */
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

    /**
     * creates GameModel, ready to play
     * @param players number of players that will be in that game
     * @return returns created GameModel
     */
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
                            if(j+k<=11) {
                                try {
                                    board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                                } catch (FieldBusyException e) {
                                    e.printStackTrace();
                                }
                            }
                    break;
                case 1:
                    for(int j=13; j<=16; j++)
                        for(int k=4; k<=7;k++)
                            if(j+k<=20) {
                                try {
                                    board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                                } catch (FieldBusyException e) {
                                    e.printStackTrace();
                                }
                            }
                    break;
                case 2:
                    for(int j=4; j<=7; j++)
                        for(int k=13; k<=16;k++)
                            if(j+k<=20) {
                                try {
                                    board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                                } catch (FieldBusyException e) {
                                    e.printStackTrace();
                                }
                            }
                    break;
                case 3:
                    for(int j=0; j<=3; j++)
                        for(int k=9; k<=12; k++)
                            if(j+k>=12) {
                                try {
                                    board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                                } catch (FieldBusyException e) {
                                    e.printStackTrace();
                                }
                            }
                    break;
                case 4:
                    for(int j=9; j<=12; j++)
                        for(int k=9; k<=12; k++)
                            if(j+k>=21) {
                                try {
                                    board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                                } catch (FieldBusyException e) {
                                    e.printStackTrace();
                                }
                            }
                    break;
                case 5:
                    for(int j=9; j<=12; j++)
                        for(int k=0; k<=3; k++)
                            if(j+k>=12) {
                                try {
                                    board.putPawn(j,k,new CCPawn(ccPlayers[i].hashCode()));
                                } catch (FieldBusyException e) {
                                    e.printStackTrace();
                                }
                            }
                    break;
            }
        }
        model.setBoard(board);
        model.setPlayers(ccPlayers);
        model.setValidator(new CCMoveValidator());
        return model;
    }
}
