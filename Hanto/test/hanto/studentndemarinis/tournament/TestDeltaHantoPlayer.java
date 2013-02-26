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

import static org.junit.Assert.*;
import static hanto.util.HantoPlayerColor.*;
import static hanto.util.HantoPieceType.*;
import static hanto.studentndemarinis.testutil.TestCoordinates.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.HantoFactory;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.MoveResult;

import org.junit.Before;
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
	public boolean equalsMoveRecord(HantoMoveRecord a, HantoMoveRecord b)
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
	public MoveResult makeMoveRecord(HantoGame game, HantoMoveRecord record) throws HantoException
	{
		return game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
	}

}