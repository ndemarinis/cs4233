/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.delta;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.AbstractHantoRuleSet;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

/**
 * Ruleset for Delta Hanto
 * @author ndemarinis
 *
 */
public class DeltaHantoRuleset extends AbstractHantoRuleSet implements
		HantoRuleSet {

	/**
	 * Create a ruleset for Delta Hanto
	 * @param state the game's state object
	 */
	public DeltaHantoRuleset(HantoGameState state) {
		this.state = state;
	}
	
	/**
	 * Perform checks to be made before a move
	 * @param piece Piece to move
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @throws HantoException if any conditions have been violated
	 */
	@Override
	public void doPreMoveChecks(HantoPieceType piece, 
			HexCoordinate from, HexCoordinate to) throws HantoException {
		
		verifyGameIsNotOver();
		verifySourceAndDestinationCoords(from, to);
		verifyButterflyHasBeenPlacedByFourthTurn(piece);
		verifyMoveIsLegal(from, to);
		verifyPlayerCanMovePieces(from, to);
		verifyPieceCanMoveToDest(piece, from, to);
	}

	/**
	 * Determine result of a move based on specification; 
	 * sets gameOver state if game has ended.  
	 * 
	 * @throws HantoException if board state is invalid
	 */
	@Override
	public MoveResult evaluateMoveResult() throws HantoException {
		MoveResult ret = winIfButterflyIsSurrounded();
		
		determineIfGameHasEnded(ret);
		
		return ret;
	}
	
	/* ******************** RULE METHODS START HERE **************************/
	
	/**
	 * Verify that a move that requires moving a piece is legal.  
	 * This ensures that only butterflies and crabs can move one hex.  
	 * @param piece Piece being moved
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @throws HantoException if this condition has been violated
	 */
	protected void verifyPieceCanMoveToDest(HantoPieceType piece, HexCoordinate from, HexCoordinate to) 
			throws HantoException
	{
		if(from != null && (piece == HantoPieceType.BUTTERFLY || piece == HantoPieceType.CRAB) &&
				!from.isAdjacentTo(to)) {
			throw new HantoException("Illegal move:  " +
					"Pieces of type " + piece + " can only move one hex!");
		}
		
		if(from != null && (piece == HantoPieceType.BUTTERFLY || piece == HantoPieceType.CRAB) && 
				!state.getBoard().canSlideTo(from, to)) {
			throw new HantoException("Illegal move:  " +
					"not enough space to slide to destination hex!");
		}
	}
	
	/**
	 * Verify that the player is allowed to move pieces.  
	 * In this case, they are allowed to do so if they have 
	 * placed their butterfly.  
	 * @param from source coordinate
	 * @throws HantoException if this condition has been violated
	 */
	protected void verifyPlayerCanMovePieces(HexCoordinate from, HexCoordinate to) 
			throws HantoException
	{
		if(from != null && !state.getBoard().contains(state.getCurrPlayer(), 
				HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Illegal move:  " +
					"Players cannot move until their butterfly has been placed!");
		}
	}

}
