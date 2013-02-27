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
 * This class encapsulates common functionality for a
 * Move Strategy.  
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 *
 */
public abstract class AbstractMoveStrategy implements HantoMoveStrategy {

	protected int distance; // Distance for a given move

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

}
