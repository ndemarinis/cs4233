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

import hanto.common.HantoException;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.TestHantoGameHarness;
import hanto.testutil.HexPiece;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * This class provides an extended Hanto implementation for AlphaHanto
 * with additional methods for testing.  
 * 
 * @author ndemarinis
 *
 */
public class TestAlphaHantoGame extends AlphaHantoGame implements
		TestHantoGameHarness {

	/**
	 * @throws HantoException
	 */
	public TestAlphaHantoGame() throws HantoException {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(HantoPlayerColor firstPlayer,
			HexPiece[] configuration) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return true if a piece exists on the board
	 */
	@Override
	public boolean doesPieceExistAt(HantoCoordinate c) {
		return state.getBoard().contains(c);
	}

	/**
	 * Add a coordinate to the board
	 */
	@Override
	public void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c) {
		state.getBoard().add(new HantoPiece(color, type, c));
	}

}
