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
import hanto.studentndemarinis.common.HantoBoard;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoPlayerHand;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoCoordinate;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is a realization of HantoGame used for simulation purposes
 * @author ndemarinis
 * @version Feb 25, 2013
 */
public class SimulatedHantoGame implements InternalHantoGame {

	private InternalHantoGame game;
	
	/**
	 * Create a simulated Hanto Game
	 * @param game The game we're simulating
	 */
	public SimulatedHantoGame(HantoGameID gameID) throws HantoException {
		this.game = (InternalHantoGame)(HantoFactory.getInstance().makeHantoGame(gameID));
	}

	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException {
		game.initialize(firstPlayer);

	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		return game.makeMove(pieceType, from, to);
	}
	
	public MoveResult makeMove(HantoMoveRecord record) throws HantoException {
		return game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
	}
	

	/* ********** HELPER METHODS ****************/

	/**
	 * Just get me a valid empty coordinate.  This is gross, but just do it.  
	 * 
	 * @return An empty coordinate on the board with at least one adjacent piece
	 * @throws HantoException if somethign went wrong
	 */
	public HexCoordinate getRandomValidEmptyCoordinate() throws HantoException {
		
		List<HexCoordinate> possibleMoves = new ArrayList<HexCoordinate>();
		HantoPlayerColor oppositePlayer = (game.getCurrPlayer() == HantoPlayerColor.RED) ? 
				HantoPlayerColor.BLUE : HantoPlayerColor.RED;
		
		for(HexCoordinate c : game.getBoard().getAllEmptyNeighborCoordinates()) {
			if(game.getNumMoves() <= 1 || !game.getBoard().hasNeighborsOfColor(c, oppositePlayer)) {
				possibleMoves.add(c);
			}
		}
		
		return possibleMoves.get((int)(Math.random() * possibleMoves.size()));
	}
	
	/* ********** GETTERS/SETTERS ****************/
	
	@Override
	public String getPrintableBoard() {
		return game.getPrintableBoard();
	}

	@Override
	public boolean doesPieceExistAt(HantoCoordinate c) {
		return game.doesPieceExistAt(c);
	}

	@Override
	public void addToBoard(HantoPlayerColor color, HantoPieceType type,
			HantoCoordinate c) {
		game.addToBoard(color, type, c);
	}
	
	public Map<HantoPieceType, Integer> getStartingHand()
	{
		return game.getStartingHand();
	}
	
	/**
	 * @param p Color of a HantoPlayer
	 * @return The hand for that player, containing the numbers of each piece available
	 */
	public HantoPlayerHand getPlayersHand(HantoPlayerColor p) {
		return game.getPlayersHand(p);
	}

	/**
	 * @return the representation of the Hanto Board.  
	 */
	@Override
	public HantoBoard getBoard() {
		return game.getBoard();
	}
	
	/**
	 * @return the Hanto Game state
	 */
	public HantoGameState getState() {
		return game.getState();
	}
	
	@Override
	public int getNumMoves() {
		return game.getNumMoves();
	}

	@Override
	public void setNumMoves(int numMoves) {
		game.setNumMoves(numMoves);
	}

	@Override
	public HantoPlayerColor getCurrPlayer() {
		return game.getCurrPlayer();
	}

}
