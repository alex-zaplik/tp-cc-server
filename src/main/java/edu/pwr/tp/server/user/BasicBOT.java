package edu.pwr.tp.server.user;

import edu.pwr.tp.server.model.elements.Field;
import edu.pwr.tp.server.model.elements.Pawn;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCField;
import edu.pwr.tp.server.model.elements.chinesecheckers.CCPawn;

import java.io.IOException;

/**
 * A basic implementation of a bot
 */
public class BasicBOT extends BOT {

	private Directories directory1;
	private Directories directory2;

	public void setDirectories(Directories d1, Directories d2){
		this.directory1=d1;
		this.directory2=d2;
	}
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
		// TODO: test the algoritm
		for (int x=0; x<17; x++)
			for (int y=0; y<17; y++){
				CCPawn pawn = (CCPawn) manager.getModel().getPawnAt(x,y);
				CCField target1 = (CCField) manager.getModel().getBoard().getField(x+directory1.x,y+directory1.y);
				CCField target2 = (CCField) manager.getModel().getBoard().getField(x+directory2.x,y+directory2.y);
				if(pawn!=null&&pawn.getPlayerID()==manager.getPlayerID(getID())){
					if(target1!=null&&(target1.getBaseID()==-1||target1.getBaseID()==(pawn.getColorID()+3)%6||target1.getBaseID()==pawn.getColorID())){
						if(manager.getModel().validateMove(x,y,target1.getX(),target1.getY())){
							sendMove(x,y,target1.getX(),target1.getY());
							return;
						}
					}
					if (target2!=null&&(target2.getBaseID()==-1||target2.getBaseID()==(pawn.getColorID()+3)%6||target2.getBaseID()==pawn.getColorID())){
						if(manager.getModel().validateMove(x,y,target2.getX(),target2.getY())){
							sendMove(x,y,target2.getX(),target2.getY());
							return;
						}
					}
				}
			}
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
