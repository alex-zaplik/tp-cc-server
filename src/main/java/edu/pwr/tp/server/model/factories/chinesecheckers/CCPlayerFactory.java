package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.elements.Player;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;
import edu.pwr.tp.server.model.factories.PlayerFactory;

public class CCPlayerFactory extends PlayerFactory {
    @Override
    public Player createPlayer() {
        return new CCPlayer();
    }
}
