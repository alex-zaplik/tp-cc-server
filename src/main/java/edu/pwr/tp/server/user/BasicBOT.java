package edu.pwr.tp.server.user;

import java.io.IOException;

/**
 * A basic implementation of a bot
 */
public class BasicBOT extends BOT {

	/**
	 * Class constructor
	 *
	 * @param ID The ID of the user
	 * @throws IOException Thrown if there was a failure during initialization of the PrintWriter and/or the BufferedReader
	 */
	public BasicBOT(int ID) throws IOException {
		super(ID);
	}

	/**
	 * Makes a move
	 */
	@Override
	void makeMove() {
		// TODO: Put the algorithm here
		skipMove();
	}

	/**
	 * Callback called by the server (with a message) confirming validity of the las move
	 *
	 * @param wasValid  True if the move was valid
	 */
	@Override
	void confirmMove(boolean wasValid) {

	}
}
