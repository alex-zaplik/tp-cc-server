package edu.pwr.tp.server.model;

import edu.pwr.tp.server.model.elements.Pawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCBoard;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCMoveValidator;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;

import java.util.ArrayList;
import java.util.List;

public class CCFactory extends GameFactory {
    @Override
    public void buildPawn(int players) {
        int pawnsPerPlayer =  7; // TODO: Nie pamiętam ile było, a mi internet przestał działać
        List<Pawn> pawns = new ArrayList<>();

        for (int play = 0; play < players; play++) {
            for (int p = 0; p < pawnsPerPlayer; p++) {
                // TODO: Initialize
            }
        }

        model.setPawns(pawns);
    }

    @Override
    public void buildBoard() {
        model.setBoard(new CCBoard());
    }

    @Override
    public void buildPlayer(int count) {
        CCPlayer[] players = new CCPlayer[count];

        // TODO: Initialize

        model.setPlayers(players);
    }

    @Override
    public void buildValidator() {
        model.setValidator(new CCMoveValidator());
    }
}
