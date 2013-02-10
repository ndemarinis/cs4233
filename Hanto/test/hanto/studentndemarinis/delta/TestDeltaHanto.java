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
import hanto.studentndemarinis.HantoFactory;
import hanto.testutil.TestHantoCoordinate;

import static hanto.util.HantoGameID.*;
import static hanto.util.HantoPieceType.*;
import static hanto.util.HantoPlayerColor.*;
import static hanto.util.MoveResult.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for DelatHanto
 * 
 * @author ndemarinis
 * @version Feb 9, 2013 
 */
public class TestDeltaHanto {

	private HantoGame game;
	private HantoFactory factory = HantoFactory.getInstance();
	
	private TestHantoCoordinate origin = new TestHantoCoordinate(0, 0);
	
	// The following coordinates are adjacent to (0, 0)
	private TestHantoCoordinate c00 = new TestHantoCoordinate(0, 0);
	private TestHantoCoordinate c01 = new TestHantoCoordinate(0, 1);
	private TestHantoCoordinate c10 = new TestHantoCoordinate(1, 0);
	private TestHantoCoordinate c1_1 = new TestHantoCoordinate(1, -1);
	private TestHantoCoordinate c0_1 = new TestHantoCoordinate(0, 1);
	private TestHantoCoordinate c_10 = new TestHantoCoordinate(1, 0);
	private TestHantoCoordinate c_11 = new TestHantoCoordinate(1, 1);
	
	
	
	private TestHantoCoordinate c11 = new TestHantoCoordinate(1, 1);
	
	
	/**
	 * Initialize DeltaHanto for testing
	 */
	@Before
	public void setUp() throws HantoException {
		game = factory.makeHantoGame(DELTA_HANTO);
	}

	@Test
	public void canInitializeDeltaHanto() {
		assertNotNull(game);
	}

	
	@Test
	public void canPlaceButterflyAtOrigin() throws HantoException {
		game.makeMove(BUTTERFLY, null, origin);
	}
}
