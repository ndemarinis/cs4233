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

	int numMoves = 0; // Total number of moves elapsed in the game so far
	HantoPlayerColor currPlayer; // Player that making the current/next move
	boolean gameOver = false; // Whether or not the game has ended
	
	// Collection of pieces representing the board for now
	HantoBoard board;
	
	// Maintain the player's hands here (as separate objects for now)
	HantoPlayer redPlayer, bluePlayer;
	
	/**
	 * Construct a state object for a HantoGame
	 * 
	 */
	public HantoGameState(HantoPlayerColor startingPlayer, 
			HantoPlayer redPlayer, HantoPlayer bluePlayer) {
		this.currPlayer = startingPlayer;
		board = new HantoBoard();
		
		this.redPlayer = redPlayer;
		this.bluePlayer = bluePlayer;
	}
	
	/**
	 * Construct a state object for a HantoGame
	 */
	public HantoGameState(HantoPlayerColor startingPlayer) {
		this(startingPlayer, null, null);
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
	 * Get the hand information for a given player
	 * @param p The desired player
	 * @return Hand information for that player
	 */
	public HantoPlayer getPlayersHand(HantoPlayerColor p) {
		return (p == HantoPlayerColor.RED) ? redPlayer : bluePlayer; 
	}

	/**
	 * @return the board
	 */
	public HantoBoard getBoard() {
		return board;
	}
	
	/**
	 * @return true if the game has ended, false otherwise
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * @param gameOver Whether or not the game is over
	 */
	public void setGameOver(boolean gameOver)
	{
		this.gameOver = gameOver;
	}

}
