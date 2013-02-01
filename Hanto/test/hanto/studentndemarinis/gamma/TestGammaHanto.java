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
import hanto.testutil.HexPiece;
import hanto.testutil.TestHantoGame;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * This class provides tests for GammaHanto.  
 * @author ndemarinis
 * @version Jan 22, 2013
 *
 */
public class TestGammaHanto {

	private HantoGame game;
	
	private final HexCoordinate origin = new HexCoordinate(0, 0);
	private final HexCoordinate adjToOrigin10 = new HexCoordinate(1, 0);
	private final HexCoordinate adjToOrigin01 = new HexCoordinate(0, 1);
	private final HexCoordinate wayOffOrigin = new HexCoordinate(3, 5);
	
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
	
	@Test(expected=HantoException.class)
	public void cantPlaceSomethingNotButterflyForFourthMoveIfNonePresent() throws HantoException
	{
		((GammaHantoGame)(game)).setNumMoves(3); // Fake it:  3 moves have been completed
		game.makeMove(HantoPieceType.SPARROW, null, origin);
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
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceOutsideGrouping() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		// Now it's blue's turn, so try to move the blue butterfly somewhere insane
		game.makeMove(HantoPieceType.BUTTERFLY, origin, wayOffOrigin);	
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceThatBreaksGrouping() throws HantoException
	{
		// Make a contiguous group of pieces
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW,   null, new HexCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW,   null, new HexCoordinate(1, -1));
		
		// Now move the butterfly such that it's near some pieces, but leaves the
		// red butterfly at (0, 1) on its own.  
		game.makeMove(HantoPieceType.BUTTERFLY, origin, new HexCoordinate(-1, 0));
	}
	
	@Test
	public void canMoveAPieceSomewhereElse() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin10); // Place red butterfly
		
		game.makeMove(HantoPieceType.BUTTERFLY, origin, new HexCoordinate(1, -1)); // Move the blue butterfly
		
		// Now move the red butterfly
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY, adjToOrigin10, new HexCoordinate(2, -1));
		
		assertEquals(MoveResult.OK, ret); // Make sure the move was valid
		assertFalse(((GammaHantoGame)(game)).doesPieceExistAt(adjToOrigin10)); // Make sure its location is now empty
	}
	
	@Test(expected=HantoException.class)
	public void cantMoveAPieceOnTopOfAnotherPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		game.makeMove(HantoPieceType.SPARROW, null, adjToOrigin10);
		
		game.makeMove(HantoPieceType.BUTTERFLY, origin, adjToOrigin10);
	}
	
	@Test(expected=HantoException.class)
	public void cantMovePieceThatIsntOnBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, origin, adjToOrigin10);
	}
	
	
	@Test
	public void gameShouldEndOnTenthMove() throws HantoException
	{
		((GammaHantoGame)(game)).setNumMoves(9); // Nine moves have completed, next is tenth.  
		MoveResult ret = game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		
		assertEquals(MoveResult.DRAW, ret);
	}
	
	@Test
	public void printedGameRepresentationShouldMakeSense() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW,  null, origin);
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin01);
		
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
		assertEquals(HantoPlayerColor.BLUE, ((GammaHantoGame)(game)).getCurrPlayer());
	}
	
	@Test
	public void currTurnAlternates() throws HantoException
	{
		// We know starting player is blue
		game.makeMove(HantoPieceType.SPARROW, null, origin);
		assertEquals(HantoPlayerColor.RED, ((GammaHantoGame)(game)).getCurrPlayer());
	}
	
	
	@Test
	public void initializeClearsBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, origin);
		game.initialize(HantoPlayerColor.BLUE);
		
		// An empty board returns an empty string.  
		assertEquals("", game.getPrintableBoard());
	}
	
	@Test
	public void canInitializeWithInitialConfiguration() throws HantoException
	{
		TestHantoGame testGame = new TestGammaHantoGame();
		HexPiece config[] = {new HexPiece(new HexCoordinate(0, 0), HantoPlayerColor.RED, HantoPieceType.BUTTERFLY)};
		testGame.initialize(HantoPlayerColor.RED, config);
		
		assertEquals(HantoPlayerColor.RED, ((TestGammaHantoGame)(testGame)).getCurrPlayer());
		assertEquals(0, ((TestGammaHantoGame)(testGame)).getNumMoves());
	}
	
	
	@Test(expected=HantoException.class)
	public void cantPlaceMoreThanOneButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin); // Place blue butterfly
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin01); // Place red butterfly
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, adjToOrigin10); // Blue shouldn't be able to place another butterfly
	}
	
	@Test(expected=HantoException.class)
	public void cantAddPiecesNotButterfliesOrSparrows() throws HantoException
	{
		game.makeMove(HantoPieceType.CRAB, null, origin);
	}
	
	@Test
	public void canMakeMoveWithInitialConfiguration() throws HantoException
	{
		TestHantoGame testGame = new TestGammaHantoGame();
		HexPiece config[] = {new HexPiece(new HexCoordinate(0, 0), HantoPlayerColor.RED, HantoPieceType.BUTTERFLY)};
		testGame.initialize(HantoPlayerColor.RED, config);
		
		MoveResult ret = testGame.makeMove(HantoPieceType.SPARROW, origin, adjToOrigin01);
		assertEquals(MoveResult.OK, ret);
	}
	
	@Test
	public void winWithButterflySurrounded() throws HantoException
	{
		// Initialize the board with a blue butterfly surrounded by red sparrows, save one hex
		TestHantoGame testGame = new TestGammaHantoGame();
		HexPiece config[] = {new HexPiece(new HexCoordinate(0, 0), HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY), 
							 new HexPiece(new HexCoordinate(0, 1), HantoPlayerColor.RED,  HantoPieceType.SPARROW),
							 new HexPiece(new HexCoordinate(1, 0), HantoPlayerColor.RED,  HantoPieceType.SPARROW),
							 new HexPiece(new HexCoordinate(1, -1), HantoPlayerColor.RED,  HantoPieceType.SPARROW),
							 new HexPiece(new HexCoordinate(0, -1), HantoPlayerColor.RED,  HantoPieceType.SPARROW),
							 new HexPiece(new HexCoordinate(-1, 0), HantoPlayerColor.RED,  HantoPieceType.SPARROW)
							};
		
		testGame.initialize(HantoPlayerColor.RED, config);
		
		// Place that one sparrow in the remaining open hex, which should result in a win.  
		MoveResult ret = testGame.makeMove(HantoPieceType.SPARROW, null, new HexCoordinate(-1, 1));
		assertEquals(MoveResult.RED_WINS, ret);
	}
	
	@Test(expected=HantoException.class)
	public void moveWithInvalidDestination() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, null);
	}
	
}