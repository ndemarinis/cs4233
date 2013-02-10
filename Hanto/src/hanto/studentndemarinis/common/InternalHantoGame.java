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

import hanto.common.HantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

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
	public boolean doesPieceExistAt(HantoCoordinate c);
	
	/**
	 * Add a coordinate to the board
	 * 
	 * TODO:  Move this to the test harness
	 * 
	 * @param color color of new piece
	 * @param type type of new piece
	 * @param c location of new piece
	 */
	public void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c);
	
	/**
	 * @return the number of moves made in this game
	 */
	public int getNumMoves();
	
	/**
	 * @param numMoves the number of moves to set
	 */
	public void setNumMoves(int numMoves);
	
	/**
	 * @return the current player up for a move
	 */
	public HantoPlayerColor getCurrPlayer();
	
}
