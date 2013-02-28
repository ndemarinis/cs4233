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
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import java.util.Map;

/**
 * Interface for HantoGame with additional methods,
 * mainly used for testing.  
 * 
 * @author ndemarinis
 * @version Feb 9, 2013
 */
public interface InternalHantoGame extends HantoGame {

	/**
	 * @return true if a piece exists on the board
	 * @param c coordinate to check for a piece
	 * 
	 * TODO:  Move this to the test harness?
	 * 
	 * NOTE:  this name makes sense to me.  
	 * I don't understand how the suggestions in
	 * CodePro's audit rule could make more sense here.   
	 */
	boolean doesPieceExistAt(HantoCoordinate c);
	
	/**
	 * Add a coordinate to the board
	 * 
	 * TODO:  Move this to the test harness
	 * 
	 * @param color color of new piece
	 * @param type type of new piece
	 * @param c location of new piece
	 */
	void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c);
	
	/**
	 * Make a move based on a record provided by the tournament
	 * 
	 * NOTE:  I am assuming that it is okay for my game implementation
	 * to have this dependency on HantoMoveRecord, which is from the tournament.  
	 * I have basically only added this for convenience, and have justified it
	 * because I see the player and tournament as all parts of our internal 
	 * implementations.  
	 * 
	 * @param move Record for the move to make
	 * @return Result of the move
	 * @throws HantoException if the move was invalid
	 */
	MoveResult makeMove(HantoMoveRecord move) throws HantoException;
	
	/**
	 * 
	 * @return the player's starting hand
	 */
	Map<HantoPieceType, Integer> getStartingHand();
	
	/**
	 * @param p Color of a HantoPlayer
	 * @return The hand for that player, containing the numbers of each piece available
	 */
	HantoPlayerHand getPlayersHand(HantoPlayerColor p);
	
	/**
	 * @return The Hanto Board.  
	 */
	HantoBoard getBoard();
	
	/**
	 * @return the Hanto Game state
	 */
	HantoGameState getState();
	
	/**
	 * @return the number of moves made in this game
	 */
	int getNumMoves();
	
	/**
	 * @param numMoves the number of moves to set
	 */
	void setNumMoves(int numMoves);
	
	/**
	 * @return the current player up for a move
	 */
	HantoPlayerColor getCurrPlayer();
	
}
