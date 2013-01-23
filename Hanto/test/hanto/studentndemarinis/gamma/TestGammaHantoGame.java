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

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class provides tests for GammaHanto.  
 * @author ndemarinis
 * @version Jan 22, 2013
 *
 */
public class TestGammaHantoGame {

	private HantoGame game;
	private final HexCoordinate origin = new HexCoordinate(0, 0);
	private final HexCoordinate adjToOrigin10 = new HexCoordinate(1, 0);
	private final HexCoordinate adjToOrigin01 = new HexCoordinate(0, 1);
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		game = new GammaHantoGame();
	}

	@Test
	public void canInitializeGammaHantoGame() 
	{
		assertNotNull(game);
	}
	
	@Test
	public void canPlaceButterflyAtOrigin() throws HantoException
	{
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		assertEquals(MoveResult.OK, ret);
	}
	
	@Test
	public void canPlaceSomethingElseAtOrigin() throws HantoException
	{
		MoveResult ret = game.makeMove(HantoPieceType.SPARROW, null, origin);
		
		assertEquals(MoveResult.OK, ret);
	}
	
	@Test
	public void canMoveButterflyFromOriginToAdjacentSquare() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY, origin, adjToOrigin01);
		
		assertEquals(MoveResult.OK, ret); // Make sure the move was valid
		assertFalse(((GammaHantoGame)(game)).doesPieceExistAt(origin)); // Make sure the origin is now empty
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceThatIsntMine() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		// Now it's blue's turn, so try to move the red butterfly
		game.makeMove(HantoPieceType.BUTTERFLY, adjToOrigin10, adjToOrigin01);
	}
	
	
}
