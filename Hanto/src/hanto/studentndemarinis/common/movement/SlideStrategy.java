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
 * This class represents a strategy for sliding
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class SlideStrategy extends AbstractMoveStrategy {

	/**
	 * Initialize a sliding strategy
	 */
	public SlideStrategy(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean canMoveTo(HantoGameState state,
			HexCoordinate from, HexCoordinate to) throws HantoException {
		 
		boolean moveIsPossible = from.isAdjacentTo(to) && state.getBoard().canSlideTo(from, to);
		
		
		// Test the move doesn't violate the contiguity condition
		// Here, I do this by clone the board by simulating the move
		
		// I UNDERSTAND THAT THIS IS COMPLETELY HORRIBLE IDEA
		// SINCE IT USES A TON OF MEMORY AND IS REALLY SLOW
		
		// However, since we do not have timing constraints
		// and we don't have any requirements that this implementation
		// needs to scale, I am leaving it for now.  I'm really tired.  
		
		HantoBoard mock = state.getBoard().clone();
		mock.remove(from);
		mock.addPieceAt(new HantoPiece(null,  null, null), to);
		moveIsPossible = moveIsPossible && mock.isBoardContiguous();
		
		return moveIsPossible; 
	}
}
