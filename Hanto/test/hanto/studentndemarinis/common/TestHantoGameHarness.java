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

import hanto.testutil.TestHantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * This interface abstracts some common test
 * methods for manipulating the board for ALphaHanto.  
 *
 * Note:  I decided against using this for GammaHanto in favor 
 * of just implementing these methods in the source class.  
 * I'm not sure whether it makes sense to keep this interface 
 * or not in the future--I'll probably make that decision in 
 * future revisions.  
 * 
 * @author ndemarinis
 * @version Jan 22, 2013
 *
 */
public interface TestHantoGameHarness extends TestHantoGame {

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
	public void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c);
}
