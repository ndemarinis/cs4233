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
import hanto.studentndemarinis.common.AbstractHantoRuleSet;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.studentndemarinis.common.HexCoordinate;
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
public class GammaHantoRules extends AbstractHantoRuleSet implements HantoRuleSet {

	// Number of moves before we MUST place a butterfly
	private final int NUM_MOVES_PRE_BUTTERFLY = 3;
	
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
	public void doPreMoveChecks(HantoPieceType piece, HexCoordinate from, HexCoordinate to) 
			throws HantoException {
		super.doPreMoveChecks(piece, from, to);


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
	public void doPostMoveChecks(HexCoordinate to) throws HantoException {
		super.doPostMoveChecks(to);
	}

	/**
	 * Evaluate whether the game needs to end based on the board configuration.  
	 * Intended to be called after each move to determine if a win has occurred.  
	 * @throws HantoException if the board is in an illegal state
	 */
	@Override
	public MoveResult evaluateMoveResult() throws HantoException {
		
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
