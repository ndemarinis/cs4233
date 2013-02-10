/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.delta;

import static org.junit.Assert.*;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.testutil.TestHantoCoordinate;

import hanto.util.MoveResult;

import static hanto.util.HantoGameID.*;
import static hanto.util.HantoPieceType.*;
import static hanto.util.HantoPlayerColor.*;
import static hanto.util.MoveResult.*;

import static hanto.studentndemarinis.testutil.TestCoordinates.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for DelatHanto
 * 
 * @author ndemarinis
 * @version Feb 9, 2013 
 */
public class TestDeltaHanto {

	private HantoGame game;
	
	/**
	 * Initialize DeltaHanto for testing
	 */
	@Before
	public void setUp() throws HantoException {
		game = factory.makeHantoGame(DELTA_HANTO);
	}

	@Test
	public void canInitializeGammaHantoGame() {
		assertNotNull(game);
	}
	
	
	@Test
	public void canInitializeDeltaHanto() {
		assertNotNull(game);
	}


	@Test(expected=HantoException.class)
	public void cantPlaceFirstPieceAtNonOrigin() throws HantoException {
		game.makeMove(BUTTERFLY, null, c10);
	}
	

	@Test
	public void canPlaceButterflyAtOrigin() throws HantoException
	{
		MoveResult ret = game.makeMove(BUTTERFLY, null, origin);
		assertEquals(OK, ret);
	}
	
	
	@Test
	public void canPlaceSomethingElseAtOrigin() throws HantoException
	{
		MoveResult ret = game.makeMove(SPARROW, null, origin);
		
		assertEquals(OK, ret);
	}
	
	@Test(expected=HantoException.class)
	public void cantPlaceSomethingNotButterflyForFourthMoveIfNonePresent() throws HantoException
	{
		((InternalHantoGame)game).setNumMoves(3); // Fake it:  3 moves have been completed
		game.makeMove(SPARROW, null, origin);
	}
	
	@Test
	public void canMoveButterflyFromOriginToAdjacentSquare() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		MoveResult ret = game.makeMove(BUTTERFLY, origin, adjToOrigin01);
		
		assertEquals(OK, ret); // Make sure the move was valid
		assertFalse(((InternalHantoGame)game).doesPieceExistAt(origin)); // Make sure the origin is now empty
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceThatIsntMine() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		// Now it's blue's turn, so try to move the red butterfly
		game.makeMove(BUTTERFLY, adjToOrigin10, adjToOrigin01);
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceOutsideGrouping() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		// Now it's blue's turn, so try to move the blue butterfly somewhere insane
		game.makeMove(BUTTERFLY, origin, wayOffOrigin);	
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceThatBreaksGrouping() throws HantoException
	{
		// Make a contiguous group of pieces
		game.makeMove(BUTTERFLY, null, new HexCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new HexCoordinate(0, 1));
		game.makeMove(SPARROW,   null, new HexCoordinate(-1, 0));
		game.makeMove(SPARROW,   null, new HexCoordinate(0, 2));
		
		// Now move the butterfly such that it's near some pieces, but leaves the
		// red butterfly at (0, 1) on its own.  
		game.makeMove(BUTTERFLY, origin, new HexCoordinate(0, -1));
	}
	
	@Test
	public void canMoveAPieceSomewhereElse() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		game.makeMove(BUTTERFLY, origin, new HexCoordinate(1, -1)); // Move the blue butterfly
		
		// Now move the red butterfly
		MoveResult ret = game.makeMove(BUTTERFLY, adjToOrigin10, new HexCoordinate(2, -1));
		
		assertEquals(OK, ret); // Make sure the move was valid
		assertFalse(((InternalHantoGame)game).doesPieceExistAt(adjToOrigin10)); // Make sure its location is now empty
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceOnTopOfAnotherPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(SPARROW, null, adjToOrigin10);
		
		game.makeMove(BUTTERFLY, origin, adjToOrigin10);
	}
	
	@Test(expected=HantoException.class)
	public void cantMovePieceThatIsntOnBoard() throws HantoException
	{
		game.makeMove(BUTTERFLY, origin, adjToOrigin10);
	}
	
	
	@Test
	public void printedGameRepresentationShouldMakeSense() throws HantoException
	{
		game.makeMove(SPARROW,  null, origin);
		game.makeMove(BUTTERFLY, null, adjToOrigin01);
		
		assertEquals("BLUE Sparrow at (0, 0)\nRED Butterfly at (0, 1)\n", game.getPrintableBoard());
	}
	
	@Test
	public void printedGameIsEmptyStringWithNothingOnIt()
	{
		assertEquals("", game.getPrintableBoard());
	}

	@Test
	public void startingPlayerIsBlue()
	{
		assertEquals(BLUE, ((InternalHantoGame)game).getCurrPlayer());
	}
	
	@Test
	public void currTurnAlternates() throws HantoException
	{
		// We know starting player is blue
		game.makeMove(SPARROW, null, origin);
		assertEquals(RED, ((InternalHantoGame)game).getCurrPlayer());
	}
	
	
	@Test
	public void initializeClearsBoard() throws HantoException
	{
		game.makeMove(SPARROW, null, origin);
		game.initialize(BLUE);
		
		// An empty board returns an empty string.  
		assertEquals("", game.getPrintableBoard());
	}
	
	
	@Test(expected=HantoException.class)
	public void cantPlaceMoreThanOneButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(BUTTERFLY, null, adjToOrigin01); // Place red butterfly
		
		game.makeMove(BUTTERFLY, null, adjToOrigin10); // Blue shouldn't be able to place another butterfly
	}
	
	@Test(expected=HantoException.class)
	public void cantAddPiecesNotButterfliesOrSparrows() throws HantoException
	{
		game.makeMove(HORSE, null, origin);
	}
	
	
	@Test(expected=HantoException.class)
	public void moveWithInvalidDestination() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, null);
	}
	
	/* ********* NEW TESTS BEGIN HERE *************************/
	
	@Test(expected=HantoException.class)
	public void cantMoveButterflyTwoHexes() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(BUTTERFLY, null, c01);
		game.makeMove(BUTTERFLY, origin, new TestHantoCoordinate(0, 2));
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveUntilPlacedButterflyBlue() throws HantoException
	{
		game.makeMove(SPARROW, null, origin);
		game.makeMove(SPARROW, null,  c10);
		game.makeMove(SPARROW, origin, c01); // Blue moves before placing butterfly
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveUntilPlacedButterflyRed() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(CRAB, null, c01);
		game.makeMove(SPARROW, null, c10);
		game.makeMove(CRAB, c01, c11); // Should fail since butterfly hasn't been placed
	}
	
	@Test(expected=HantoException.class)
	@Ignore
	public void cantMoveButterflyWithoutRoomToSlide() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
	
		// Box in the butterfly
		game.makeMove(CRAB, null, c0_1);
		game.makeMove(SPARROW, null, c_11); 
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(1, -1));
		
		// Now try to move it
		game.makeMove(BUTTERFLY, origin, c_10);
	}
	
	@Test
	public void canFlyToHexInGroup() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(BUTTERFLY, null, c0_1);
		game.makeMove(CRAB, null, c01);
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(CRAB, null, new TestHantoCoordinate(0, -3));
			
		assertEquals(OK, game.makeMove(SPARROW, new TestHantoCoordinate(0, 2), 
				new TestHantoCoordinate(0, -4)));
	}
	
	@Test(expected=HantoException.class)
	public void cantFlyToHexAndBreakGrouping() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(BUTTERFLY, null, c0_1);
		game.makeMove(CRAB, null, c01);
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(CRAB, null, new TestHantoCoordinate(0, -3));
			
		assertEquals(OK, game.makeMove(SPARROW, new TestHantoCoordinate(0, 2), 
				new TestHantoCoordinate(-2, 1)));
	}
	
	@Test
	public void crabCanWalkOneHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(BUTTERFLY, null, c01);
		game.makeMove(CRAB, null, c10);
		game.makeMove(BUTTERFLY, c01, c_11);
		game.makeMove(CRAB, c10, c01);
	}
	
	@Test(expected=HantoException.class)
	public void crabCantWalkMoreThanOneHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, origin);
		game.makeMove(BUTTERFLY, null, c01);
		game.makeMove(CRAB, null, c10);
		game.makeMove(BUTTERFLY, c01, c_11);
		game.makeMove(CRAB, c10, new TestHantoCoordinate(-1, 2));	
	}
}
