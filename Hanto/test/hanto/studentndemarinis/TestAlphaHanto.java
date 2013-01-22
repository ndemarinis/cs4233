/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.testutil.HexPiece;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * TestAlphaHanto:  Test cases for AlphaHanto
 * @author ndemarinis
 *
 */
public class TestAlphaHanto {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void canInitializeGameAndButterfly() throws HantoException {
		HantoGame game = new AlphaHanto();
		HexPiece blueButterfly = new HexPiece(new HexCoordinate(0, 0), 
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		
		assertNotNull(game);
		assertNotNull(blueButterfly);
	}
	
	@Test
	public void canPlaceButterflyAtOrigin() throws HantoException {
		HantoGame game = new AlphaHanto();
		HexPiece blueButterfly = new HexPiece(new HexCoordinate(0, 0),
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		
		assertEquals(MoveResult.OK, game.makeMove(blueButterfly.getPiece(), null, 
				blueButterfly.getCoordinate()));
	}

}
