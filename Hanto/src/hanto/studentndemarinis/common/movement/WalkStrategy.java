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

import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HexCoordinate;

/**
 * This class represents a strategy for walking
 * In this case, walking does NOT involve sliding
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class WalkStrategy extends AbstractMoveStrategy {
	
	@Override
	public boolean canMoveTo(HantoGameState state, 
			HexCoordinate from, HexCoordinate to) {

		// Walking (no sliding) is valid if we're not moving to our own piece,
		// the destination is adjacent to this one,
		// the board remains contiguous after the move
		return from != to && 
				from.isAdjacentTo(to) && 
				isBoardContiguousAfterSimulatingMove(state, from, to);
	}

}
