/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.gamma;

import hanto.common.HantoException;
import hanto.testutil.HexPiece;
import hanto.testutil.TestHantoGame;
import hanto.util.HantoPlayerColor;

/**
 * This class provides a testing harness for GammaHanto, 
 * it implements the methods required by the TestHantoGame
 * interface for automated testing.  
 * 
 * @author ndemarinis
 * @version Jan 22, 2013
 *
 */
public class TestGammaHantoGame extends GammaHantoGame implements TestHantoGame {

	/**
	 * @throws HantoException
	 */
	public TestGammaHantoGame() throws HantoException {
		super();
	}

	/**
	 * Initialize the board with a given state before playing.
	 * Since it was not specified, this method performs
	 * ABSOLUTELY NO VERIFICATION OF THE PIECES ADDED TO THE BOARD.  
	 * Therfore, this method could create an ENTIRELY INVALID
	 * board configuration and BREAK EVERYTHING.  
	 * @param firstPlayer player to make the first move
	 * @param configuration array of pieces to add to the board
	 */
	@Override
	public void initialize(HantoPlayerColor firstPlayer,
			HexPiece[] configuration) {
		
		// Your interface doesn't let me throw this otherwise.  
		try {
			this.initialize(firstPlayer);
		} catch(HantoException e) {
			throw new RuntimeException("Can't initialize the board based on your config!");
		}
		
		int i;
		for(i = 0; i < configuration.length; i++)
		{
			this.addToBoard(configuration[i].getPlayer(), 
					configuration[i].getPiece(), configuration[i].getCoordinate());
		}
	}

}
