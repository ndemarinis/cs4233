/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.gamma;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * Abstraction for GammaHanto's move rules
 * 
 * @author ndemarinis
 * @version Jan 31, 2013
 *
 */
public class GammaHantoRules implements HantoRuleSet {

	// Number of moves before we MUST place a butterfly
	private final int NUM_MOVES_PRE_BUTTERFLY = 3;
	
	HantoGameState state;
	
	/**
	 * Make a new set of GammaHanto's rules, given
	 * the game itself
	 * @param game The HantoGame we'll be checking
	 */
	public GammaHantoRules(HantoGameState state) {
		this.state = state;
	}

	/**
	 * Checks to be performed before a move is made
	 * 
	 * @param piece Piece to be placed at the given location
	 * @param from source coordinate of piece on the board, null if not on the board
	 * @param to destination coordinate for piece to move
	 * @throws HantoException if a rule has been violated, 
	 * leaving the board in an illegal state
	 */
	@Override
	public void doPreMoveChecks(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to) 
			throws HantoException {
		
		// Verify the source piece is valid, if provided.  
		if(from != null) 
		{
			if(state.getBoard().getPieceAt(from) == null) {
				throw new HantoException("Illegal move:  " +
						"source piece does not exist on board!");
			}

			if(state.getBoard().getPieceAt(from).getColor() != state.getCurrPlayer()) {
				throw new HantoException("Illegal move:  your can only move pieces" +
						"of your own color!");
			}

		}

		// Verify a destination coordinate has actually been provided
		if(to == null){
			throw new HantoException("Illegal move:  Destination coordinate must be provided!");
		}
		
		
		// If this is the first move, we need a piece at the origin
		if(state.getNumMoves() == 0 && 
				to.getX() != 0 && to.getY() != 0) {
			throw new HantoException("Illegal move:  First piece must be placed " +
					"at origin!");
		}
		

		
		// If we find any pieces at the destination, it's not a legal move.  
		if(state.getBoard().getPieceAt(to) != null){
			throw new HantoException("Illegal move:  can't place a piece " +
					"on top of an existing piece!");
		}

		// Verify this move doesn't _need_ to place the butterfly.  
		if(!state.getBoard().contains(state.getCurrPlayer(), HantoPieceType.BUTTERFLY) &&
				state.getNumMoves() >= NUM_MOVES_PRE_BUTTERFLY && piece != HantoPieceType.BUTTERFLY) {
			throw new HantoException("Illegal move:  " +
					"Butterfly must be placed by the foruth turn!");
		}

	}

	/**
	 * Checks to perform after a move has been made.  
	 * @param to Destination coordinate of newly-moved piece
	 * @throws HantoException if the a rule has been violated, 
	 * leaving the board in an illegal state
	 */
	@Override
	public void doPostMoveChecks(HantoCoordinate to) throws HantoException {
		
		boolean isValid = true;
		
		// Now that we've added the piece, check if it doesn't violate the adjacency rules
		for(HantoPiece p : state.getBoard())
		{
			// If everything is in one contiguous group, we should be able to
			// pick any piece on the board and find a path from it
			// to every other piece.  
			// If one fails, we broke the rules.  
			isValid = isValid && state.getBoard().thereExistsPathBetween(to, p);
		}
		
		// If we violated the adjacency rules
		if(!isValid) {
			throw new HantoException("Illegal move:  pieces must retain a contiguous group!");
		}
	}

	/**
	 * Evaluate whether the game needs to end based on the board configuration.  
	 * Intended to be called after each move to determine if a win has occurred.  
	 * @throws HantoException if the board is in an illegal state
	 */
	@Override
	public MoveResult evaluateWinConditions() throws HantoException {
		
		// Check win conditions (max number of moves, butterfly surrounded)
		MoveResult ret = (state.getNumMoves() != 10) ? MoveResult.OK : MoveResult.DRAW;
		
		for(HantoPiece p : state.getBoard().getPiecesOfType(HantoPieceType.BUTTERFLY))
		{
			if(state.getBoard().isSurrounded(p)) {
				ret = (p.getColor() == HantoPlayerColor.BLUE) ? 
						MoveResult.RED_WINS : MoveResult.BLUE_WINS;
			}
		}
		
		return ret;
	}

}
