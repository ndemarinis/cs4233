/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.common;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * @author ndemarinis
 * @version Jan 31, 2013
 *
 */
public abstract class AbstractHantoGame implements HantoGame {

	
	protected int numMoves = 0; // Total number of moves elapsed in the game so far
	protected HantoPlayerColor currPlayer; // Player that making the current/next move
	
	// Collection of pieces representing the board for now
	protected HantoBoard board = new HantoBoard();
	
	
	/**
	 * Abstract HantoGame providing basic implementation
	 */
	
	@Override
	public abstract void initialize(HantoPlayerColor firstPlayer) 
			throws HantoException;
	
	@Override
	public abstract MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException;

	/**
	 * Return a string representing the current state of the board,
	 * empty string if the board is empty.  
	 */
	@Override
	public String getPrintableBoard() {
		String ret = "";
		
		for(HantoPiece p : board) {
			ret += (p + "\n");
		}
		
		return ret;
	}
	
	/**
	 * @return true if a piece exists on the board
	 * @param c coordinate to check for a piece
	 * 
	 * NOTE:  this name makes sense to me.  
	 * I don't understand how the suggestions in
	 * CodePro's audit rule could make more sense here.   
	 */
	public boolean doesPieceExistAt(HantoCoordinate c) {
		return board.getPieceAt(c) != null;
	}

	/**
	 * Add a coordinate to the board
	 * @param color color of new piece
	 * @param type type of new piece
	 * @param c location of new piece
	 */
	public void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c) {
		final HantoPiece p = new HantoPiece(color, type, c);
		board.add(p);
	}

	/**
	 * @return the number of moves made in this game
	 */
	public int getNumMoves() {
		return numMoves;
	}


	/**
	 * @param numMoves the number of moves to set
	 */
	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}


	/**
	 * @return the current player up for a move
	 */
	public HantoPlayerColor getCurrPlayer() {
		return currPlayer;
	}


	/**
	 * @param currPlayer player set to be next to move
	 */
	public void setCurrPlayer(HantoPlayerColor currPlayer) {
		this.currPlayer = currPlayer;
	}

}
