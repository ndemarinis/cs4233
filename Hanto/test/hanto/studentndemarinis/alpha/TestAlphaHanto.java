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

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.alpha.AlphaHanto;
import hanto.studentndemarinis.alpha.HexCoordinate;
import hanto.testutil.HexPiece;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TestAlphaHanto:  Test cases for AlphaHanto
 * @author ndemarinis
 *
 */
public class TestAlphaHanto {

	HantoGame game;

	final HantoCoordinate origin = new HexCoordinate(0, 0);
	final HantoCoordinate adjToOrigin = new HexCoordinate(1, 0);
	final HantoCoordinate wayOffFromOrigin = new HexCoordinate(3, 5);
	
	@Before
	public void setUp() throws HantoException 
	{
		game = new AlphaHanto();
	}

	@Test
	public void canInitializeGameAndButterfly() throws HantoException 
	{		
		assertNotNull(game);
	}
	
	@Test
	public void canPlaceBlueButterflyAtOrigin() throws HantoException 
	{		
		assertEquals(MoveResult.OK, 
				game.makeMove(HantoPieceType.BUTTERFLY, null, origin));
	}
	
	@Test(expected=HantoException.class)
	public void cantPlaceBlueButterflyAtNonOrigin() throws HantoException 
	{
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY, 
				null, adjToOrigin);
		
		assertEquals(ret, MoveResult.OK);
	}
	
	@Test(expected=HantoException.class)
	public void cantPlaceRedButterflyOnTopOfBlueButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
	}
	
	@Test
	public void canPlaceRedButterflyAtAdjacentLocation() throws HantoException 
	{
	
		// Place the blue butterfly, as before
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		
		assertEquals(MoveResult.DRAW, 
				game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin));
	}
	
	@Test(expected=HantoException.class)
	public void cantPlaceBlueButterflyAtNonAdjacentLocation() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY, 
				null, wayOffFromOrigin);
		
		assertEquals(ret, MoveResult.DRAW);
		fail("Adjacency checking has not yet been implemented");
	}
	
	
}
