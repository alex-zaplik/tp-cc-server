package edu.pwr.tp.server.model.factories.chinesecheckers;

import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPlayer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CCGameModelFactoryTest {

    @Test
    public void testCreateModel() {
        GameModel gameModel = CCGameModelFactory.getInstance().createModel(6);
        CCPlayer player = (CCPlayer) gameModel.getPlayers()[0];
        assertEquals(player.getID(),gameModel.getPawnAt(5,5).getPlayerID());
        assertNotEquals(player.getID(),gameModel.getPawnAt(0,12).getPlayerID());
        assertNull(gameModel.getPawnAt(6,6));
    }
}