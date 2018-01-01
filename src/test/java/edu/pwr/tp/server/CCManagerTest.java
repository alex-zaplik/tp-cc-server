package edu.pwr.tp.server;

import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.factories.chinesecheckers.CCGameModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CCManagerTest {

	private GameManager manager;

	@Before
	public void initManager() throws InvalidArgumentsException {
		manager = new CCManager(4);

		List<Integer> userIDs = new ArrayList<>();
		userIDs.add(0);
		userIDs.add(1);
		userIDs.add(2);
		userIDs.add(3);

		manager.init(userIDs);
	}

	@Test
	public void testInvalidMove() {
		assertFalse(manager.makeMove(0, 0, 0, 0 , 0));
	}

	@After
	public void nullManager() {
		manager = null;
	}
}
