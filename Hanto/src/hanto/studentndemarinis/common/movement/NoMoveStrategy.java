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
 * This class represents a move strategy 
 * for a piece that doesn't move
 *
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class NoMoveStrategy extends AbstractMoveStrategy {

	/**
	 * Create a nomove strategy
	 */
	public NoMoveStrategy() {
		// We literally have nothing to do here
	}

	@Override
	public boolean canMoveTo(HantoGameState state, HexCoordinate from,
			HexCoordinate to) throws HantoException {
		
		return false; // We can't move, so always fail.  
	}

}