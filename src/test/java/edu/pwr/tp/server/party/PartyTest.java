package edu.pwr.tp.server.party;

import edu.pwr.tp.server.exceptions.InvalidArgumentsException;
import edu.pwr.tp.server.model.GameModel;
import edu.pwr.tp.server.model.GameType;
import edu.pwr.tp.server.user.BasicBOT;
import edu.pwr.tp.server.user.ConnectedUser;
import edu.pwr.tp.server.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class PartyTest {

	private Party party;
	private int maxUsers = 6;
	private int botCount = 2;
	private String name = "Test";
	private GameType type = GameType.CHINESE_CHECKERS;

	private <R, P> R callPrivate(String methodName, Object param, Class<P> paramType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		if (param == null) {
			Method method = party.getClass().getDeclaredMethod(methodName);
			method.setAccessible(true);
			return (R) method.invoke(party);
		} else {
			Method method = party.getClass().getDeclaredMethod(methodName, paramType);
			method.setAccessible(true);
			return (R) method.invoke(party, paramType.cast(param));
		}
	}

	private <P> void callPrivateVoid(String methodName, Object param, Class<P> paramType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		if (param == null) {
			Method method = party.getClass().getDeclaredMethod(methodName);
			method.setAccessible(true);
			method.invoke(party);
		} else {
			Method method = party.getClass().getDeclaredMethod(methodName, paramType);
			method.setAccessible(true);
			method.invoke(party, paramType.cast(param));
		}
	}

	@Before
	public void initParty() throws InvalidArgumentsException {
		party = new Party(maxUsers, botCount, name, type, null);
	}

	@Test
	public void testInit() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		assertEquals(maxUsers, party.getMaxUsers());
		callPrivateVoid("addBots", null, null);
		assertEquals(maxUsers - botCount, party.getFreeSlots());
	}

	@Test
	public void testInitUserIDs() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		callPrivateVoid("addBots", null, null);
		List<Integer> list = callPrivate("initUserIDs", null, List.class);
		assertEquals(botCount, list.size());
	}

	@Test
	public void testInitGameModel() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		callPrivateVoid("addBots", null, null);
		List<Integer> list = callPrivate("initUserIDs", null, List.class);

		callPrivateVoid("initGameModel", list, List.class);
		assertEquals(botCount, ((GameModel) callPrivate("getModel", null, null)).getPlayers().length);
	}

	@Test
	public void testDefragUsers() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		User[] users = {new BasicBOT(0), null, new BasicBOT(1), null, null, new BasicBOT(2), null, null, null};
		callPrivateVoid("defragUsers", users, User[].class);

		boolean firstNull = false;
		for (User u : users) {
			if (!firstNull && u == null)
				firstNull = true;

			if (firstNull)
				assertEquals(null, u);
		}
	}

	@After
	public void nullParty() {
		party = null;
	}
}
