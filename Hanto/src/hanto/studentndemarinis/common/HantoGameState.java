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

import hanto.util.HantoPlayerColor;

/**
 * @author ndemarinis
 *
 */
public class HantoGameState {

	private int numMoves = 0; // Total number of moves elapsed in the game so far
	private HantoPlayerColor currPlayer; // Player that making the current/next move
	
	// Collection of pieces representing the board for now
	private HantoBoard board = new HantoBoard();
	
	/**
	 * Contains state information for a HantoGame
	 */
	public HantoGameState(HantoPlayerColor startingPlayer) {
		this.currPlayer = startingPlayer;
	}
	
	/**
	 * @return the numMoves
	 */
	public int getNumMoves() {
		return numMoves;
	}

	/**
	 * @param numMoves the numMoves to set
	 */
	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	/**
	 * @return the currPlayer
	 */
	public HantoPlayerColor getCurrPlayer() {
		return currPlayer;
	}

	/**
	 * @param currPlayer the currPlayer to set
	 */
	public void setCurrPlayer(HantoPlayerColor currPlayer) {
		this.currPlayer = currPlayer;
	}

	/**
	 * @return the board
	 */
	public HantoBoard getBoard() {
		return board;
	}

}
