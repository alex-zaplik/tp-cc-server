package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.exceptions.FieldBusyException;
import edu.pwr.tp.server.model.CCGameModel;
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
        CCGameModel model = new CCGameModel();
        CCBoard board = (CCBoard) boardFactory.createBoard();
        CCPlayer[] ccPlayers = new CCPlayer[players];
        for (int i=0; i<players; i++){
            ccPlayers[i] = (CCPlayer) playerFactory.createPlayer();
        }
        if(players==2||players==3||players==6){ //1st color
            for(int x=4; x<=7; x++)
                for(int y=13; y<=16; y++){
                    if(x+y<=20){
                        try {
                            board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[0].getID(),0));
                        } catch (FieldBusyException ex){
                            ex.printStackTrace();
                        }
                    }
                }
        }
        if(players==4||players==6){ //2nd color
            for(int x=0; x<=3; x++)
                for (int y=9; y<=12; y++){
                    if(x+y>=12){
                        try {
                            if(players==4) board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[0].getID(), 1));
                            else board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[1].getID(),1));
                        } catch (FieldBusyException ex){
                            ex.printStackTrace();
                        }
                    }
                }
        }
        if(players!=2){ //3rd color
            for(int x=4; x<=7; x++)
                for (int y=4; y<=7; y++){
                    if(x+y<=11){
                        try {
                            if(players!=6) board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[1].getID(),2));
                            else board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[2].getID(),2));
                        } catch (FieldBusyException ex){
                            ex.printStackTrace();
                        }
                    }
                }
        }
        if(players==2||players==6){ //4th color
            for(int x=9; x<=12; x++)
                for(int y=0; y<=3; y++)
                    if(x+y>=12){
                        try {
                            if(players==2) board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[1].getID(),3));
                            else board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[3].getID(),3));
                        } catch (FieldBusyException ex){
                            ex.printStackTrace();
                        }
                    }
        }
        if(players!=2){ //5th color
            for(int x=13; x<=16; x++)
                for(int y=4; y<=7; y++){
                    if(x+y<=20){
                        try {
                            if(players!=6) board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[2].getID(),4));
                            else board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[4].getID(),4));
                        } catch (FieldBusyException ex){
                            ex.printStackTrace();
                        }
                    }
                }
        }
        if(players==4||players==6){ //6th color
            for(int x=9; x<=12; x++)
                for(int y=9; y<=12; y++){
                    if(x+y>=21){
                        try {
                            if(players==4) board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[3].getID(),5));
                            else board.putPawn(x, y, ((CCPawnFactory) pawnFactory).createPawn(ccPlayers[5].getID(),5));
                        } catch (FieldBusyException ex){
                            ex.printStackTrace();
                        }
                    }
                }
        }
        model.setBoard(board);
        model.setPlayers(ccPlayers);
        model.setValidator(new CCMoveValidator());
        return model;
    }
}
