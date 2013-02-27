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
import hanto.studentndemarinis.common.HexCoordinate;

/**
 * This class represents a move stragegy 
 * for a given piece
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 *
 */
public interface HantoMoveStrategy {

	/**
	 * Try to move to a given coordinate using the above strategy
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @throws HantoException if the move was invalid
	 */
	public void tryMoveTo(HexCoordinate from, HexCoordinate to) throws HantoException;
	
	/**
	 * Determine whether a given piece can move to a given coordinate
	 * based on its unique strategy
	 * 
	 * @param from Source coordinate
	 * @param to Destination coordinate
	 * @return true if the move is possible
	 * 
	 * @throws HantoException if something went wrong calculating the move
	 * This method does NOT throw an exception if the move was invalid
	 */
	public boolean canMoveTo(HexCoordinate from, HexCoordinate to) throws HantoException;
	
}
