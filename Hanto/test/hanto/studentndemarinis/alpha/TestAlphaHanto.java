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
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.alpha.AlphaHantoGame;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.testutil.TestHantoGameHarness;
import hanto.testutil.HexPiece;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * TestAlphaHanto:  Test cases for AlphaHanto
 * @author ndemarinis
 * @version Jan 22, 2013
 *
 */
public class TestAlphaHanto {

	HantoGame game;

	final HantoCoordinate origin = new HexCoordinate(0, 0);
	final HantoCoordinate adjToOrigin = new HexCoordinate(1, 0);
	final HantoCoordinate adjToOriginAgain = new HexCoordinate(0, -1);
	final HantoCoordinate wayOffFromOrigin = new HexCoordinate(3, 5);
	
	final HexPiece blueButterfly = new HexPiece(new HexCoordinate(0, 0), 
			HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
	
	final HexPiece redButterfly = new HexPiece(new HexCoordinate(0, 0), 
			HantoPlayerColor.RED, HantoPieceType.BUTTERFLY);
	
	@Before
	public void setUp() throws HantoException 
	{
		game = new TestAlphaHantoGame();
	}

	
// Interface-based tests
	@Test
	public void canInitializeGame() throws HantoException 
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
		// Place one blue butterfly, which we know works
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		
		// Now place another one.  
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
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, wayOffFromOrigin);
	}
	
	@Test
	public void canPlaceRedButterflyAtAnotherAdjacentLocationEndsInDraw() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY,  null, adjToOriginAgain);
		
		assertEquals(ret, MoveResult.DRAW);
	}
	
// Additional tests based on my extensions
	@Test
	public void butterflyAtOriginActuallyIsAtOrigin() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		assertTrue(((TestHantoGameHarness)(game)).doesPieceExistAt(origin));
	}
	
	@Test
	public void canAddButterflyAtOriginManually() throws HantoException
	{
		((TestHantoGameHarness)(game)).addToBoard(blueButterfly.getPlayer(), 
				blueButterfly.getPiece(), blueButterfly.getCoordinate());
		assertTrue(((TestHantoGameHarness)(game)).doesPieceExistAt(origin));
	}
	
	@Test
	public void currentPlayerIsBlueOnInitialization()
	{
		assertEquals(HantoPlayerColor.BLUE, ((AlphaHantoGame)(game)).getCurrPlayer());
	}
	
	@Test
	public void currentPlayerSwitchesAfterMakingAMove() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		
		assertEquals(HantoPlayerColor.RED, ((AlphaHantoGame)(game)).getCurrPlayer());
	}
	
	
	@Test
	public void gameStartsWithZeroMoves()
	{
		assertEquals(0, ((AlphaHantoGame)(game)).getNumMoves());
	}
	
	@Test
	public void numberOfMovesIncrementsAfterOneMove() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		assertEquals(1, ((AlphaHantoGame)(game)).getNumMoves());
	}
	
	@Test(expected=HantoException.class)
	public void cantPlaceAnythingOtherThanButterflies() throws HantoException
	{
		game.makeMove(HantoPieceType.CRAB, null, origin);
	}
	
	@Test
	public void printingEmptyBoardReturnsEmptyString()
	{
		assertEquals("", game.getPrintableBoard());
	}
	
	
	@Test
	public void printableBoardOutputMakesSense() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin);
		
		assertEquals("BLUE Butterfly at (0, 0)\nRED Butterfly at (1, 0)\n", 
				game.getPrintableBoard());
	}
	
	@Test
	public void initializeResetsBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		game.initialize(HantoPlayerColor.BLUE);
		
		assertEquals("", game.getPrintableBoard());
		assertEquals(0, ((AlphaHantoGame)(game)).getNumMoves());
		assertEquals(HantoPlayerColor.BLUE, ((AlphaHantoGame)(game)).getCurrPlayer());
	}
}
