package edu.pwr.tp.server.user;

import edu.pwr.tp.server.GameManager;

import java.io.IOException;

public class BasicBOT extends BOT {

	/**
	 * Class constructor
	 *
	 * @param ID The ID of the user
	 * @throws IOException Thrown if there was a failure during initialization of the PrintWriter and/or the BufferedReader
	 */
	public BasicBOT(int ID, GameManager manager) throws IOException {
		super(ID, manager);
	}

	@Override
	void makeMove() {

	}

	@Override
	void confirmMove(boolean wasValid) {

	}
}
