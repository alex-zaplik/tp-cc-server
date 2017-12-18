package edu.pwr.tp.server.model;

import edu.pwr.tp.server.exceptions.FieldBusyException;
import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.Pawn;
import edu.pwr.tp.server.model.elements.Player;
import edu.pwr.tp.server.model.elements.MoveValidator;

/**
 * this class is a Model of the Game. It consists of Board, with Pawns at starting positions, Players that represent color of the Pawns at the Board, and validator that checks if the move is legal
 * @author Jarosław Nigiel
 * @see Board
 * @see Player
 * @see MoveValidator
 */
public class GameModel {

    private Board board;
    private Player[] players;
    private MoveValidator validator;

    /**
     * that's the command order to move a Pawn
     * @param fromX x coordinate from witch pawn will be moved
     * @param fromY y coordinate from witch pawn will be moved
     * @param toX x coordinate to witch pawn will be moved
     * @param toY y coordinate to witch pawn will be moved
     */
    public void movePawn(int fromX, int fromY, int toX, int toY) { //TODO: right color of player validation
        if (validateMove(fromX, fromY, toX, toY)) {
            Pawn pawn = getPawnAt(fromX,fromY);
            board.removePawnFrom(fromX, fromY);
            try {
                board.putPawn(toX, toY, pawn);
            } catch (FieldBusyException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * checks if move is legal
     * @param fromX x coordinate from witch pawn will be moved
     * @param fromY y coordinate from witch pawn will be moved
     * @param toX x coordinate to witch pawn will be moved
     * @param toY y coordinate to witch pawn will be moved
     * @return returns true if move is legal
     */
    private boolean validateMove(int fromX, int fromY, int toX, int toY) {
        return validator.validate(board, fromX, fromY, toX, toY);
    }

    /**
     * removes a Pawn by hashCode
     * @deprecated
     * @param id id of a Pawn to remove
     */
    public void removePawn(int id) {
        board.removePawnByID(id);
    }

    /**
     * classical setter
     * @param board Board to set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * classical setter
     * @param players array of Players
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * classical setter
     * @param validator MoveValidator to set
     */
    public void setValidator(MoveValidator validator) {
        this.validator = validator;
    }

    /**
     * gets Pawn by ID
     * this is deprecated
     * @see #getPawnAt(int, int)
     * @deprecated
     * @param id hashCode of the Pawn
     * @return returns Pawn by hashCode
     *
     */
    public Pawn getPawn(int id) {
        return board.getPawnByID(id);
    }

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return returns a Pawn from a given position
     * @see Board#getPawnAt(int, int)
     */
    public Pawn getPawnAt(int x, int y) {
        return board.getPawnAt(x, y);
    }

    /**
     *
     * @param id hashCode of Player
     * @return returns a Player by his hashCode
     */
    public Player getPlayer(int id) {
        Player player = null;
        for (Player p : players)
            if (p.getID() == id)
                player = p;
        return player;
    }

    /**
     * standard getter
     * @return returns an array of Players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * standard getter
     * @return returns Board of that Model
     */
    public Board getBoard() {
        return board;
    }
}
