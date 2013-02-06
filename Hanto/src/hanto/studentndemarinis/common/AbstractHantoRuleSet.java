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
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
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
	
	@Override
	public void doPreMoveChecks(HantoPieceType piece, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		// Verify the source piece is valid, if provided.  
		if(from != null) 
		{
			if(state.board.getPieceAt(from) == null) {
				throw new HantoException("Illegal move:  " +
						"source piece does not exist on board!");
			}

			if(state.board.getPieceAt(from).getColor() != state.currPlayer) {
				throw new HantoException("Illegal move:  your can only move pieces" +
						"of your own color!");
			}
		}

		// Verify a destination coordinate has actually been provided
		if(to == null){
			throw new HantoException("Illegal move:  Destination coordinate must be provided!");
		}
		
		// If this is the first move, we need a piece at the origin
		if(state.numMoves == 0 && 
				to.getX() != 0 && to.getY() != 0) {
			throw new HantoException("Illegal move:  First piece must be placed " +
					"at origin!");
		}
		
		// If we find any pieces at the destination, it's not a legal move.  
		if(state.board.getPieceAt(to) != null){
			throw new HantoException("Illegal move:  can't place a piece " +
					"on top of an existing piece!");
		}

	}
	
	@Override
	public void actuallyMakeMove(HantoPieceType type, HantoCoordinate from, HantoCoordinate to)
	{
		// Remove the old piece from the board (if we haven't failed yet)
		if(from != null) {
			state.getBoard().remove(from);
		}
		
		// Finally, add the new piece to the board.  
		state.getBoard().add(new HantoPiece(state.getCurrPlayer(), type, to));
	}
	
	@Override
	public void doPostMoveChecks(HantoCoordinate to) throws HantoException {
		// If we violated the adjacency rules
		if(!state.board.isBoardContiguous()) {
			throw new HantoException("Illegal move:  pieces must retain a contiguous group!");
		}
	}

	@Override
	public MoveResult evaluateMoveResult() throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}

}
