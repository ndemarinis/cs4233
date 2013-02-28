/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.common.movement;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.HantoBoard;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HexCoordinate;

/**
 * This class encapsulates common functionality for a
 * Move Strategy.  
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 *
 */
public abstract class AbstractMoveStrategy implements HantoMoveStrategy {

	@Override
	public void tryMoveTo(HantoGameState state, HexCoordinate from,
			HexCoordinate to) throws HantoException {
		
		// In the interest of KISS, I am okay with leaving this message ambiguous 
		if(!canMoveTo(state, from, to)) {
			throw new HantoException("Illegal move: move strategy violated!");
		}

	}

	@Override
	public abstract boolean canMoveTo(HantoGameState state, HexCoordinate from,
			HexCoordinate to) throws HantoException;
	
	
	/**
	 * Test the move doesn't violate the contiguity condition
	 * Here, I do this by clone the board by simulating the move.
	 * No changes are made to the real board or state.  
	 *	
	 * I UNDERSTAND THAT THIS IS COMPLETELY HORRIBLE IDEA
	 * SINCE IT USES A TON OF MEMORY AND IS REALLY SLOW
	 *	
	 * However, since we do not have timing constraints
	 * and we don't have any requirements that this implementation
	 * needs to scale, I am leaving it for now.  I'm really tired. 
	 * 
	 * @param state the game's state
	 * @param from source coordinate
	 * @param to destination coordinate
	 * @return true if the board is contiguous after the move
	 */
	protected boolean isBoardContiguousAfterSimulatingMove(HantoGameState state, 
			HexCoordinate from, HexCoordinate to) {
		 
		final HantoBoard mock = state.getBoard().clone(); // Clone the board
		mock.remove(from);
		mock.addPieceAt(new HantoPiece(null,  null, null), to);
		
		return mock.isBoardContiguous();
	}
	
}
