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
import hanto.common.HantoGame;
import hanto.studentndemarinis.HantoFactory;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoCoordinate;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This class simulates a Hanto Tournament
 * It basically just runs a game, taking manual 
 * moves for one player and automated moves from 
 * the HantoPlayer, while printing the result and 
 * simulating each move on a real game.   
 * 
 * This should provide a sensible harness (or fake object?)
 * for testing the HantoPlayer by simulating a real tournament
 * 
 * @author ndemarinis
 *
 */
public class FakeHantoTournament {

	protected HantoGame game;
	protected DeltaHantoPlayer player;
	
	protected HantoPlayerColor colorOfUUT;
	protected HantoPlayerColor playerMovingNext;
	
	protected HantoMoveRecord lastMove = null;
	
	/**
	 * Initialize a Fake Hanto Tournament
	 * @param startingPlayer the player to start
	 * @param colorOfUUT the color the HantoPlayer (AI) will take
     * @throws HantoException if something went wrong with creation
	 */
	public FakeHantoTournament(HantoPlayerColor startingPlayer, 
			HantoPlayerColor colorOfUUT) throws HantoException {
		
		this.colorOfUUT = colorOfUUT;
		this.playerMovingNext = startingPlayer;
		
		game = HantoFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		game.initialize(startingPlayer);
		
		player = new DeltaHantoPlayer(colorOfUUT, (colorOfUUT == startingPlayer));
	}
	
	/**
	 * Simulate a move as the tester
	 * 
	 * @param piece The piece to move
	 * @param to Source coordinate
	 * @param from Destination coordinate
	 * @return The result of YOUR move
	 * @throws HantoException if the move was invalid or something went horribly wrong
	 */
	public MoveResult manualMove(HantoPieceType piece, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		
		MoveResult ret;
		
		if(playerMovingNext == colorOfUUT) {
			throw new HantoPlayerException("It's not your turn!  Write better tests!");
		}
		
		ret = game.makeMove(piece, from,  to);
		lastMove = new HantoMoveRecord(piece, from, to);
		
		System.out.println("TESTER" + 
						((from == null) ? " placed " : " moved ") + piece +
						((from == null) ? (" at " + to) : (from + " -> " + to)));
		
		completeMove();
		
		return ret;
	}
	
	/**
	 * Let the player make a move
	 * @return the result of that move
	 * @throws HantoException If the move was invalid or something terrible happened
	 */
	public MoveResult playerMove() throws HantoException {
		
		HantoMoveRecord playerMove;
		MoveResult ret;
		
		if(playerMovingNext != colorOfUUT) {
			throw new HantoPlayerException("It's not your turn!  Simulate another move.");
		}
		
		// Tell the player to move
		playerMove = player.makeMove(lastMove);
		
		// Perform this move in the test game
		ret = game.makeMove(playerMove.getPiece(), playerMove.getFrom(), playerMove.getTo());
		
		// Update our state information
		completeMove();
		lastMove = playerMove;
		
		System.out.println("PLAYER" + 
				((playerMove.getFrom() == null) ? " placed " : " moved ") + playerMove.getPiece() +
				((playerMove.getFrom() == null) ? (" at " + playerMove.getTo()) : 
					(playerMove.getFrom() + " -> " + playerMove.getTo())));
		
		return ret;
	}
	
	private void completeMove() {
		playerMovingNext = (playerMovingNext == HantoPlayerColor.BLUE) ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE;
	}
}
