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

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.movement.HantoMoveStrategy;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * Provides common rule implementation for
 * a HantoRuleSet
 * @author ndemarinis
 * @version 4 February 2013
 *
 */
public abstract class AbstractHantoRuleSet implements HantoRuleSet {

	protected HantoGameState state;
	
	protected static Map<HantoPieceType, HantoMoveStrategy> moveStrategies = 
			new HashMap<HantoPieceType, HantoMoveStrategy>();
	
	private static final int NUM_MOVES_PRE_BUTTERFLY = 3;
	private static final int NUM_MOVES_BEFORE_CARE_ABOUT_COLOR_ADJACENCY = 1;
	
	/**
	 * Perform checks that must take place before a move.  
	 * See HantoRuleSet for details
	 */
	@Override
	public void doPreMoveChecks(HantoPieceType piece, 
			HexCoordinate from, HexCoordinate to) throws HantoException 
	{
		verifyGameIsNotOver();
		verifySourceAndDestinationCoords(from, to);
		verifyMoveIsLegal(piece, from, to);
		verifyPlacementIsNotNextToAnotherColor(from, to);
	}
	
	/**
	 * Perform a move for real
	 * See HantoRuleSet for details.  
	 */
	@Override
	public void actuallyMakeMove(HantoPieceType type, HexCoordinate from, HexCoordinate to) 
			throws HantoException
	{
		if(to != null) {
			// If this move involved placing a new piece, remove it from the player's hand
			if(from == null) {
				state.getPlayersHand(state.getCurrPlayer()).removeFromHand(type);
			}

			// Remove the old piece from the board (if we haven't failed yet)
			if(from != null) {
				state.getBoard().remove(from);
			}

			// Finally, add the new piece to the board.  
			state.getBoard().addPieceAt(new HantoPiece(state.getCurrPlayer(), type, to), to);
		}
	}
	
	/**
	 * Perform any checks that must take place after a move
	 * @param to Destination coordinate
	 * @throws HantoException if any of these rules have been violated
	 */
	@Override
	public void doPostMoveChecks(HexCoordinate to) throws HantoException {
		if(to != null) {
			verifyBoardIsContiguous();
		}
	}

	/**
	 * Determine the result of a move based on the game's rules.  
	 * Must be overridden by concrete realization.  
	 */
	public abstract MoveResult evaluateMoveResult();

	
	/**
	 * Create move strategies for each piece in the ruleset
	 * Must be overridden by the concrete realization.  
	 */
	protected abstract void setupMoveStrategies();
	
	/**
	 * Safely check if a piece can move using the 
	 * defined move strategies 
	 * 
	 * @param piece The piece to move
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @throws HantoException if move was invalids
	 */
	protected void verifyPieceCanMove(HantoPieceType piece, 
			HexCoordinate from, HexCoordinate to) throws HantoException
	{
		HantoMoveStrategy strat = null;
		
		strat = moveStrategies.get(piece);
		
		if(strat == null) {
			throw new HantoException("Illegal move:  " +
					"Movement is not supported for this piece type in this version of Hanto!");
		}
		
		if(from != null) // If this is a move
		{
			strat.tryMoveTo(state, from, to);
		}
	}
	
	
	/**
	 * Verify the game is not over
	 * @throws HantoException if the game is over
	 */
	protected void verifyGameIsNotOver() throws HantoException
	{
		if(state.isGameOver()) {
			throw new HantoException("Illegal move:  game has already ended!");
		}
	}
	
	/**
	 * Verify the source and destination coordinates exist.  
	 * If a source is provided, it must exist on the board; 
	 * a destination coordinate must exist for a valid move.  
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @throws HantoException if either of these conditions have been violated
	 */
	protected void verifySourceAndDestinationCoords(HexCoordinate from, HexCoordinate to) 
			throws HantoException
	{
		// If provided, a source piece must exist on the board 
		if(from != null) 
		{
			if(state.board.getPieceAt(from) == null) {
				throw new HantoException("Illegal move:  " +
						"source piece does not exist on board!");
			}
		}

		// The move must have a destination coordinate
		if(to == null){
			throw new HantoException("Illegal move:  Destination coordinate must be provided!");
		}
	}
	
	/**
	 * Verify a move is legal, meaning that the first piece must be at the origin, 
	 * players can only move pieces of their own color, and that the destination
	 * coordinate must be empty
	 * @param piece Type of piece being moved
	 * @param from Source coordinate of move to verify
	 * @param to Destination coordinate of move to verify
	 * @throws HantoException if any of these conditions have been violated
	 */
	protected void verifyMoveIsLegal(HantoPieceType piece, 
			HexCoordinate from, HexCoordinate to) throws HantoException
	{
		// Verify the piece to be moved is owned by the current player  
		if(from != null) 
		{
			final HantoPiece toPiece = state.board.getPieceAt(from);
			
			if(toPiece.getColor() != state.currPlayer) {
				throw new HantoException("Illegal move:  your can only move pieces" +
						"of your own color!");
			}
			
			if(toPiece.getType() != piece){
				throw new HantoException("Illegal move:  piece type specified does " +
						"not match piece at source coordinate!");
			}
		}
		
		// If this is the first move, we need a piece at the origin
		if(state.numMoves == 0 && 
				(to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("Illegal move:  First piece must be placed " +
					"at origin!");
		}
		
		// If we find any pieces at the destination, it's not a legal move.  
		if(state.board.getPieceAt(to) != null){
			throw new HantoException("Illegal move:  can't place a piece " +
					"on top of an existing piece!");
		}
	}
	
	/**
	 * Verify that a piece to be placed at a coordinate is not adjacent to any pieces
	 * with the opposing color, as specified.  
	 * This condition is not checked for the first move.  
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @throws HantoException if the condition has been violated
	 */
	protected void verifyPlacementIsNotNextToAnotherColor(HexCoordinate from, HexCoordinate to) 
			throws HantoException {
		
		// We only care about this rule when placing pieces and after the second move
		// and if this isn't our first placement of either color
		if(from == null && to != null && 
				state.numMoves > NUM_MOVES_BEFORE_CARE_ABOUT_COLOR_ADJACENCY) { 
			
			// If one of the neighbors of this piece is next to a piece of another color
			for(HantoPiece p : state.board.getNeighborsOf(to)) {
				if(p.getColor() != state.currPlayer) {
					throw new HantoException("Illegal move:  " +
							"you can only place pieces next to those of your own color!");
				}
			}
		}
	}
	
	/**
	 * Verify all of the pieces on the board are in a
	 * single contiguous grouping.  
	 * @throws HantoException if any pieces are separated from the group
	 */
	protected void verifyBoardIsContiguous() throws HantoException
	{
		// If we violated the adjacency rules
		if(!state.board.isBoardContiguous()) {
			throw new HantoException("Illegal move:  pieces must retain a contiguous group!");
		}
	}
	
	/**
	 * Set whether or not the game has ended based on the current move result
	 * @param res Result to determine game's ending state
	 */
	protected void determineIfGameHasEnded(MoveResult res)
	{
		state.setGameOver(res == MoveResult.DRAW || 
				res == MoveResult.BLUE_WINS || res == MoveResult.RED_WINS);
	}

	/**
	 * Check if a player has won by surrounding their opponent's
	 * butterfly.  If both butterflies are surrounded, it's a DRAW.  
	 * @return winning player if they have surrounded their opponent's butterfly,
	 * DRAW if both are surrounded, OK if none of these conditions have been met
	 */
	protected MoveResult winIfButterflyIsSurrounded() {
		MoveResult ret = MoveResult.OK;
		
		for(HantoPiece p : state.getBoard().getPiecesOfType(HantoPieceType.BUTTERFLY))
		{
			if(state.getBoard().isSurrounded(p)) {
				ret = (ret != MoveResult.OK) ? // If we have already found a surrounded butterfly
						MoveResult.DRAW :      // It's a draw
							((p.getColor() == HantoPlayerColor.BLUE) ?      
									MoveResult.RED_WINS : MoveResult.BLUE_WINS); 
			}
		}
		
		return ret;
	}

	/**
	 * Ensure that a butterfly must be placed by the fourth term,
	 * as the rules specify.  Therefore, a player moving on/after 
	 * the fourth turn with no butterfly on the board MUST 
	 * place their butterfly.  
	 * 
	 * @param piece The piece involved in the move
	 * @throws HantoException if trying to place a butterfly without
	 * one for that player on the board
	 */
	protected void verifyButterflyHasBeenPlacedByFourthTurn(HantoPieceType piece)
			throws HantoException {
				if(piece != HantoPieceType.BUTTERFLY && 
				   state.getNumMoves() >= NUM_MOVES_PRE_BUTTERFLY &&
				   !state.getBoard().contains(state.getCurrPlayer(), HantoPieceType.BUTTERFLY)) 
				{
					throw new HantoException("Illegal move:  " +
							"Butterfly must be placed by the foruth move!");
				}
			}
}
