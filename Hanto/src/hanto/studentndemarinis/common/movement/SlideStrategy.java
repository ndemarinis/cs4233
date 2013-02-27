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
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HexCoordinate;

/**
 * This class represents a strategy for sliding
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class SlideStrategy implements HantoMoveStrategy {

	private int distance;
	
	/**
	 * Initialize a sliding strategy
	 */
	public SlideStrategy(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean canMoveTo(HantoGameState state,
			HexCoordinate from, HexCoordinate to) throws HantoException {
		return from.isAdjacentTo(to) && state.getBoard().canSlideTo(from, to);
	}
	
	@Override
	public void tryMoveTo(HantoGameState state, 
			HexCoordinate from, HexCoordinate to) throws HantoException
	{
		if(!canMoveTo(state, from, to)) {
			throw new HantoException("Illegal move:  " +
					"piece can only slide one hex!");
		}
	}

}
