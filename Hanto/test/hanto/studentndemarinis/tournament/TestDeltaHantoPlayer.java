/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.tournament;

import static hanto.studentndemarinis.testutil.TestCoordinates.c00;
import static hanto.studentndemarinis.testutil.TestCoordinates.c01;
import static hanto.studentndemarinis.testutil.TestCoordinates.c0_1;
import static hanto.studentndemarinis.testutil.TestCoordinates.origin;
import static hanto.util.HantoPieceType.*;
import static hanto.util.HantoPlayerColor.*;
import static hanto.util.MoveResult.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.HantoFactory;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.testutil.TestHantoCoordinate;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Test cases for DeltaHantoPlayer.  
 * 
 * @author ndemarinis
 * @version Feb 25, 2013
 */
public class TestDeltaHantoPlayer {


	private HantoMoveRecord butterflyAtOrigin = new HantoMoveRecord(BUTTERFLY, null, origin);
	private HantoGame game;
	private FakeHantoTournament tourn;
	
	
	@Before
	public void setUp() throws HantoException 
	{
		game = HantoFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		game.initialize(BLUE);
	}

	@Test
	public void canCreateDeltaHantoPlayer() throws HantoException
	{
		HantoGamePlayer player = new DeltaHantoPlayer(BLUE, false);
		assertNotNull(player);
	}
	
	@Test
	public void startingPlayerPlacesButterflyAtOrigin() throws HantoException
	{
		HantoGamePlayer player = new DeltaHantoPlayer(BLUE, true);
		
		HantoMoveRecord ret = player.makeMove(null);
		makeMoveRecord(game, ret);
		
		assertTrue(equalsMoveRecord(butterflyAtOrigin, ret));
	}
	
	@Test
	public void notStartingPlayerPlacesPieceSomewhereElse() throws HantoException
	{
		HantoGamePlayer player = new DeltaHantoPlayer(BLUE, false);
		
		makeMoveRecord(game, butterflyAtOrigin);
		HantoMoveRecord moveNotOrigin = player.makeMove(new HantoMoveRecord(BUTTERFLY, null, origin));
		makeMoveRecord(game, moveNotOrigin);
		
		assertTrue(moveNotOrigin.getFrom() == null && 
				!(moveNotOrigin.getTo().getX() == 0 && moveNotOrigin.getTo().getY() == 0));
	}
	
	@Test
	public void canCreateFakeTournament() throws HantoException
	{
		tourn = new FakeHantoTournament(BLUE, BLUE);
		
		assertNotNull(tourn);
	}
	
	@Test
	public void fakeTournamentPlayerPutsButterflyAtOrigin() throws HantoException
	{
		tourn = new FakeHantoTournament(BLUE, BLUE);
		tourn.playerMove();
		
		assertTrue(equalsMoveRecord(tourn.lastMove, butterflyAtOrigin));
	}
	
	@Test(expected=HantoPlayerException.class)
	public void playerCantMoveOutOfTurnInTournament() throws HantoException
	{
		tourn = new FakeHantoTournament(BLUE, RED);
		tourn.playerMove();
	}
	
	@Test(expected=HantoPlayerException.class)
	public void testerCantMoveOutOfTurnInTournament() throws HantoException
	{
		tourn = new FakeHantoTournament(RED, RED);
		tourn.manualMove(null, null, null);
	}
	
	@Test
	public void tournPlayerPutsButterflyAtNonOrigin() throws HantoException
	{
		tourn = new FakeHantoTournament(BLUE, RED);
		tourn.manualMove(BUTTERFLY, null, origin);
		tourn.playerMove();
		
		assertTrue(tourn.lastMove.getFrom() == null && 
				!(tourn.lastMove.getTo().getX() == 0 && tourn.lastMove.getTo().getY() == 0));
	}
	
	
	@Test
	public void tournPlaceTwoPiecesEach() throws HantoException
	{
		tourn = new FakeHantoTournament(BLUE, BLUE, new RandomSelectStrategy(true, false));
		tourn.playerMove();
		tourn.manualMove(BUTTERFLY, null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, getRandomValidEmptyCoordinate(tourn.game));
	}
	
	@Test
	public void playerCorrectlyPicksPiecesWhenOneExhausted() throws HantoException {
		HantoMoveRecord moves[] = {new HantoMoveRecord(BUTTERFLY, null, c00), 
								   new HantoMoveRecord(SPARROW,   null, c0_1),
								   new HantoMoveRecord(SPARROW,   null, new TestHantoCoordinate(0, -2)), 
								   new HantoMoveRecord(SPARROW,   null, new TestHantoCoordinate(0, -3)),
								   new HantoMoveRecord(SPARROW,   null, new TestHantoCoordinate(0, -4))};
		
		tourn = new FakeHantoTournament(BLUE, BLUE, new PartialFakedSelectStrategy(moves, true, false));
		
		tourn.playerMove();
		tourn.manualMove(BUTTERFLY, null, c01);
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, new TestHantoCoordinate(0, 2));
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, new TestHantoCoordinate(0, 3));
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, new TestHantoCoordinate(0, 4));
		tourn.playerMove();
		tourn.manualMove(CRAB,   null, new TestHantoCoordinate(0, 5));
		tourn.playerMove();
		
		assertEquals(CRAB, tourn.lastMove.getPiece());
	}
	
	@Test
	public void playerResignsIfPicksBadMove() throws HantoException {
		HantoMoveRecord moves[] = { new HantoMoveRecord(BUTTERFLY, null, new TestHantoCoordinate(3, 7))};
		
		tourn = new FakeHantoTournament(BLUE, RED, new PartialFakedSelectStrategy(moves, false, false));
		
		tourn.manualMove(BUTTERFLY, null, origin);
		
		// Player should try to make an invalid move, which should fail, so it resigns
		assertEquals(BLUE_WINS, tourn.playerMove()); 
	}

	
	@Test
	public void playerResignsIfGivenABadOpponentMove() throws HantoException 
	{
		HantoGamePlayer player = new DeltaHantoPlayer(BLUE, true);
		
		// Give the player an invalid move, so it should resign.  
		// We signal this to the director by returning null
		assertNull(player.makeMove(new HantoMoveRecord(BUTTERFLY, 
				null, new TestHantoCoordinate(3, 7))));
	}
	
	
	@Test
	public void playerIsForcedToPlaceButterflyByFourthTurn() throws HantoException
	{
		HantoMoveRecord moves[] = {new HantoMoveRecord(SPARROW, null, c01), 
								   new HantoMoveRecord(SPARROW, null, new TestHantoCoordinate(0, 2)), 
								   new HantoMoveRecord(SPARROW, null, new TestHantoCoordinate(0, 3))};
		
		tourn = new FakeHantoTournament(BLUE, RED, new PartialFakedSelectStrategy(moves));
		
		tourn.manualMove(BUTTERFLY, null, c00);
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, -1));
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, -2));
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, -3));
		tourn.playerMove();
		
		assertEquals(BUTTERFLY, tourn.lastMove.getPiece());
	}
	
	@Test
	public void playerResignsWhenOutOfMoves() throws HantoException
	{
		tourn = new FakeHantoTournament(BLUE, BLUE, new RandomSelectStrategy(true, false));

		tourn.playerMove();
		tourn.manualMove(BUTTERFLY, null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(SPARROW,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(CRAB,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(CRAB,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(CRAB,   null, getRandomValidEmptyCoordinate(tourn.game));
		tourn.playerMove();
		tourn.manualMove(CRAB,   null, getRandomValidEmptyCoordinate(tourn.game));
		
		// Player (BLUE) should be out of pieces now, so it should resign.  
		assertEquals(RED_WINS, tourn.playerMove());  	
	}
	
	@Test
	public void playerCanMoveAPiece() throws HantoException {
		HantoMoveRecord moves[] = {new HantoMoveRecord(BUTTERFLY, null, c0_1),
								   new HantoMoveRecord(SPARROW,   null, new TestHantoCoordinate(0, -2))};
		
		tourn = new FakeHantoTournament(BLUE, RED, new PartialFakedSelectStrategy(moves, false, true));
		
		tourn.manualMove(BUTTERFLY, null, c00);
		tourn.playerMove();
		tourn.manualMove(CRAB, null, new TestHantoCoordinate(0, 1));
		tourn.playerMove();
		// Player should be required to move now.  
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, 2));
		tourn.playerMove();
		
		// The last move should not be a placement, meaning it has a valid source
		assertNotNull(tourn.lastMove.getFrom());
	}

	@Test
	public void playerCanMakeTwoMoves() throws HantoException {
		HantoMoveRecord moves[] = {new HantoMoveRecord(BUTTERFLY, null, c0_1),
								   new HantoMoveRecord(SPARROW,   null, new TestHantoCoordinate(0, -2)), 
								   new HantoMoveRecord(SPARROW,   new TestHantoCoordinate(0, -2), 
										   new TestHantoCoordinate(1, -2)),
								   new HantoMoveRecord(SPARROW,   new TestHantoCoordinate(1, -2), 
										   new TestHantoCoordinate(0, -2)),
								    };
		
		tourn = new FakeHantoTournament(BLUE, RED, new PartialFakedSelectStrategy(moves, false, true)); 
		tourn.manualMove(BUTTERFLY, null,origin);
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, c01);
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, 2));
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, 3));
		tourn.playerMove();
	}
	
	@Test
	public void playerCanOnlyPickTwoMoves() throws HantoException {
		HantoMoveRecord moves[] = {new HantoMoveRecord(BUTTERFLY, null, c0_1),
									new HantoMoveRecord(SPARROW,   null, new TestHantoCoordinate(0, -2))
								    };
		
		tourn = new FakeHantoTournament(BLUE, RED, new PartialFakedSelectStrategy(moves, false, true)); 
		tourn.manualMove(BUTTERFLY, null,origin);
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, c01);
		
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, 2));
		tourn.playerMove();
		tourn.manualMove(SPARROW, null, new TestHantoCoordinate(0, 3));
		
		assertEquals(OK, tourn.playerMove());
	}
	
	
	/* *********** HELPER METHODS *****************/
	
	/**
	 * Compare two move records.  Equal records have the same
	 * pieces as well as source and destination coordinates
	 * 
	 * I don't know where to put this right now.  
	 * 
	 * @param a A move record
	 * @param b Another move record
	 * @return true if the two move records have the same coordinates 
	 * and piece
	 */
	private boolean equalsMoveRecord(HantoMoveRecord a, HantoMoveRecord b)
	{
		return a != null && b != null && 
				(a.getFrom() == null && b.getFrom() == null) || 
					(a.getFrom().getX() == b.getFrom().getX() && a.getFrom().getY() == b.getFrom().getY()) &&
				(a.getTo().getX() == b.getTo().getX() && a.getTo().getY() == b.getTo().getY()) &&
				a.getPiece() == b.getPiece();
	}
	
	/**
	 * Make a move based on a move record
	 * Again, I'm not sure where to put this yet
	 * 
	 * @param game A hanto game
	 * @param record A move record
	 * @throws HantoException if anything went wrong during the move
	 */
	private MoveResult makeMoveRecord(HantoGame game, HantoMoveRecord record) throws HantoException
	{
		return game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
	}

	
	/**
	 * Just get me a valid empty coordinate.  This is gross, but just do it.  
	 * 
	 * @return An empty coordinate on the board with at least one adjacent piece
	 * @throws HantoException if something went wrong
	 */
	private HexCoordinate getRandomValidEmptyCoordinate(InternalHantoGame game) throws HantoException {
		
		List<HexCoordinate> possibleMoves = new ArrayList<HexCoordinate>();
		HantoPlayerColor oppositePlayer = (game.getCurrPlayer() == HantoPlayerColor.RED) ? 
				HantoPlayerColor.BLUE : HantoPlayerColor.RED;
		
		for(HexCoordinate c : game.getBoard().getAllEmptyNeighborCoordinates()) {
			if(game.getNumMoves() <= 1 || !game.getBoard().hasNeighborsOfColor(c, oppositePlayer)) {
				possibleMoves.add(c);
			}
		}
		
		return possibleMoves.get((int)(Math.random() * possibleMoves.size()));
	}
}
