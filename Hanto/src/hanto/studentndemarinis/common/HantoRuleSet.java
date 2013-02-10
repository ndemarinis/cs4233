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
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

/**
 * This class specifies the sets of rules that apply to a
 * Hanto Game.  
 * 
 * @author ndemarinis
 * @version Jan 31, 2013
 */
public interface HantoRuleSet {

	/**
	 * Perform any checks that can happen before a move is made, throws a HantoException if
	 * the move is invalid.  
	 * 
	 * @param piece The piece to add at the new location
	 * @param from Source location of said piece, null if piece is not on the board
	 * @param to Destination coordinate of piece after the move
	 * @throws HantoException if proposed move violates a rule.  
	 */
	void doPreMoveChecks(HantoPieceType piece, HexCoordinate from, HexCoordinate to) 
			throws HantoException;
	
	/**
	 * Make a move, regardless of whether or not it is valid.
	 * Any piece currently at the source and destination locations
	 * are REMOVED when this method is called  
	 * 
	 * @param type Piece type to place at the destination
	 * @param from Source coordinate of the piece, null if piece is not on the board
	 * @param to Destination coordinate of the piece
	 */
	void actuallyMakeMove(HantoPieceType type, HexCoordinate from, HexCoordinate to) 
			throws HantoException;
	
	/**
	 * Perform any checks based on the location of a newly-moved piece, 
	 * throws HantoException of the move is invalid.  
	 * @param to Destination coordinate of the piece after the move
	 * @throws HantoException if the proposed move violates a rule
	 */
	void doPostMoveChecks(HexCoordinate to) throws HantoException;
	
	/**
	 * Check conditions to determine if the game needs to end.  This is used for
	 * returning the result of a recent move
	 * @return MoveResult with based on the current board's conditions
	 * @throws HantoException on an invalid board configuration
	 */
	MoveResult evaluateMoveResult() throws HantoException;
}
