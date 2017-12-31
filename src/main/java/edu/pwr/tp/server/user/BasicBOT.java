package edu.pwr.tp.server.user;

import java.io.IOException;

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

	@Override
	void makeMove() {
		skipMove();
	}

	@Override
	void confirmMove(boolean wasValid) {

	}
}
