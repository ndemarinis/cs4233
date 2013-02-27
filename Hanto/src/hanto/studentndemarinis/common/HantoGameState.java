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

import java.util.Map;

import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * Encapsulation of stat information for a HantoGame.  
 * 
 * @author ndemarinis
 * @version Feb 7, 2013
 *
 */
public class HantoGameState {

	int numMoves; // Total number of moves elapsed in the game so far
	HantoPlayerColor currPlayer; // Player that making the current/next move
	boolean gameOver; // Whether or not the game has ended
	
	// Whether or not the current player resigned
	// This is kind of gross, but it's simple.  I like TDD.  =)
	HantoPlayerColor resignee = null;
	
	// Collection of pieces representing the board for now
	HantoBoard board;
	
	// Maintain the player's hands here (as separate objects for now)
	HantoPlayerHand redPlayer, bluePlayer;
	
	/**
	 * Construct a state object for a HantoGame
	 * 
	 * @param startingPlayer Color of player to start
	 * @param startingHand Map of Piece->Count indicating how many of 
	 * each piece the player has available for play.  
	 */
	public HantoGameState(HantoPlayerColor startingPlayer, 
			Map<HantoPieceType, Integer> startingHand) {
		currPlayer = startingPlayer;
		board = new HantoBoard();
		
		redPlayer = new HantoPlayerHand(startingHand);
		bluePlayer = new HantoPlayerHand(startingHand);
	}
	
	/**
	 * Construct a state object for a HantoGame
	 * 
	 * @param startingPlayer Color of player to start
	 */
	public HantoGameState(HantoPlayerColor startingPlayer) {
		this(startingPlayer, null);
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
	public HantoPlayerHand getPlayersHand(HantoPlayerColor p) {
		return (p == HantoPlayerColor.RED) ? redPlayer : bluePlayer; 
	}
	
	/**
	 * Set the hand information for a given player
	 * @param p The desired player
	 * @param hand The hand to give the player
	 */
	public void setPlayersHand(HantoPlayerColor p, Map<HantoPieceType, Integer> hand) {
		if(p == HantoPlayerColor.RED) {
			redPlayer.setHand(hand);
		} else {
			bluePlayer.setHand(hand);
		}
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

	/**
	 * @return the player that resigned
	 */
	public HantoPlayerColor getResignee() {
		return resignee;
	}

	/**
	 * @param resignee player that resigned
	 */
	public void setResignee(HantoPlayerColor resignee) {
		this.resignee = resignee;
	}

}
