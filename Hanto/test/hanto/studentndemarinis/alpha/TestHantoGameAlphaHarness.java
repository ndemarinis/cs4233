/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.alpha;

import hanto.testutil.TestHantoGame;
import hanto.util.HantoCoordinate;

/**
 * @author ndemarinis
 *
 */
public interface TestHantoGameAlphaHarness extends TestHantoGame {

	/**
	 * 
	 * @param c Some non-null coordinate on a hexagonal grid
	 * @return true if another piece is on the board at that coordinate, false otherwise
	 */
	public boolean doesPieceExistAt(HantoCoordinate c);
	
	/**
	 * Adds a some piece directly to the board.  
	 * This function is designed for testing only, it does NOT perform any 
	 * checking to ensure that the piece added can legally be part of the board.  
	 * 
	 * @param c The piece to add.  
	 */
	public void addToBoard(HantoCoordinate c);
}
