package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.elements.Player;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;
import edu.pwr.tp.server.model.factories.PlayerFactory;

/**
 * this Factory creates a Player
 * @author Jarosław Nigiel
 */
public class CCPlayerFactory extends PlayerFactory {

    /**
     *
     * @return returns a Player created by this Factory
     */
    @Override
    public Player createPlayer() {
        return new CCPlayer();
    }
}
