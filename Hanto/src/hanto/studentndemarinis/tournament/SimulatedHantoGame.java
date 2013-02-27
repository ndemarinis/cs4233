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

import java.util.Map;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.HantoBoard;
import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

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
	public SimulatedHantoGame(InternalHantoGame game) {
		this.game = game;
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
	 * @return the representation of the Hanto Board.  
	 */
	@Override
	public HantoBoard getBoard() {
		return game.getBoard();
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
