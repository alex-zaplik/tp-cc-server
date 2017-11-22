package edu.pwr.tp.server.model;

import edu.pwr.tp.server.model.elements.Board;
import edu.pwr.tp.server.model.elements.Pawn;
import edu.pwr.tp.server.model.elements.Player;
import edu.pwr.tp.server.model.elements.MoveValidator;

import java.util.List;

public class GameModel {

    private Board board;
    private Player[] players;
    private MoveValidator validator;

    public void movePawn(int id, int toX, int toY) {
        if (validateMove(id, toX, toY)) {
            getPawn(id).setPosition(toX, toY);
        }
    }

    public boolean validateMove(int id, int toX, int toY) {
        return validator.validate(board, id, toX, toY);
    }

    public void removePawn(int id) {
        board.removePawn(id);
    }

    public void setPawns(List<Pawn> pawns) {
        board.setPawns(pawns);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setValidator(MoveValidator validator) {
        this.validator = validator;
    }

    public Pawn getPawn(int id) {
        return board.getPawn(id);
    }

    public Pawn getPawnAt(int x, int y) {
        return board.getPawnAt(x, y);
    }

    public Player getPlayer(int id) {
        Player player = null;
        for (Player p : players)
            if (p.getID() == id)
                player = p;
        return player;
    }

    public Board getBoard() {
        return board;
    }
}
