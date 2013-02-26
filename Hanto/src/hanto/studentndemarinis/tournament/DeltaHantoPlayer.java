/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.tournament;

import hanto.common.HantoException;
import hanto.studentndemarinis.HantoFactory;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * Skeleton for eventual DeltaHantoPlayer
 * @author ndemarinis
 * @version Feb 25, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer {

	private boolean isStarting;
	private InternalHantoGame game;
	
	/**
	 * Create a new DeltaHantoPlayer, as given
	 * @param color Player of color, given by tournament
	 * @param starting true if this player is making the starting move
	 */
	public DeltaHantoPlayer(HantoPlayerColor color, boolean isStarting) throws HantoException{
		this.isStarting = isStarting;
		game = (InternalHantoGame)(HantoFactory.getInstance().
				makeHantoGame(HantoGameID.DELTA_HANTO));
	}
	
	/**
	 * Make the player's next move.
	 * @param opponentsMove this is the result of the opponent's last move, in response
	 * 	to your last move. This will be null if you are making the first move of the game.
	 * @return your move
	 */
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		HexCoordinate opponentDest;
		HexCoordinate[] opponentNeighbors;
		
		HantoMoveRecord ret;
		
		if(opponentsMove != null) // If this wasn't the starting move 
		{
			opponentDest = HexCoordinate.extractHexCoordinate(opponentsMove.getTo());
			opponentNeighbors = opponentDest.getNeighboringCoordinates();
			
			ret = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, opponentNeighbors[0]);
		} 
		else 
		{
			ret = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, 
					new HexCoordinate(0, 0));
		}
		
		return ret;
	}

}
