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
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.common.movement.HantoMoveType;
import hanto.studentndemarinis.common.movement.MoveFactory;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

/**
 * Abstraction for GammaHanto's move rules
 * 
 * @author ndemarinis
 * @version Jan 31, 2013
 *
 */
public class GammaHantoRuleset extends AbstractHantoRuleSet implements HantoRuleSet {

	// Max number of moves before ending in a draw
	private final int MAX_MOVES = 10 * 2; // Two turns/move * 10 turns max, as specified
	
	/**
	 * Make a new set of GammaHanto's rules, given
	 * the game itself
	 * @param state The HantoGame we'll be checking
	 */
	public GammaHantoRuleset(HantoGameState state) {
		this.state = state;
		
		setupMoveStrategies();
	}

	protected void setupMoveStrategies() {
		moveStrategies.put(HantoPieceType.BUTTERFLY, 
				MoveFactory.getInstance().getMoveStrategy(HantoMoveType.WALK, 1));
		moveStrategies.put(HantoPieceType.SPARROW, 
				MoveFactory.getInstance().getMoveStrategy(HantoMoveType.NO_MOVE, 0));
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
			throws HantoException 
	{
		super.doPreMoveChecks(piece, from, to);
		verifyButterflyHasBeenPlacedByFourthTurn(piece);
		
		verifyPieceCanMove(piece, from, to);
	}
	
	/**
	 * Evaluate whether the game needs to end based on the board configuration.  
	 * Intended to be called after each move to determine if a win has occurred.  
	 * @throws HantoException if the board is in an illegal state
	 */
	@Override
	public MoveResult evaluateMoveResult() 
	{	
		MoveResult ret = winIfButterflyIsSurrounded();
		
		// If the previous condition didn't end the game
		if(ret == MoveResult.OK) {
			ret = endInDrawAfter10Moves();
		}
		
		// Set the gameOver flag based on the result
		determineIfGameHasEnded(ret);
		
		return ret;
	}
	
	/* ******************** RULE METHODS START HERE **************************/
	
	/**
	 * Give result ending the game in a draw after 10 moves.  
	 * @return OK if game has been running for less than 10 moves, 
	 * DRAW otherwise.  
	 */
	protected MoveResult endInDrawAfter10Moves()
	{
		return (state.getNumMoves() != MAX_MOVES) ?
				MoveResult.OK : MoveResult.DRAW;
	}

}
