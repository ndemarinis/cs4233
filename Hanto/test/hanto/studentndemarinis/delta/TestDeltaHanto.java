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

import static hanto.studentndemarinis.testutil.TestCoordinates.*;

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
	
	@Test(expected=HantoException.class)
	public void cantPlaceFirstPieceAtNonOrigin() throws HantoException {
		game.makeMove(BUTTERFLY, null, c10);
	}
	
	
}
