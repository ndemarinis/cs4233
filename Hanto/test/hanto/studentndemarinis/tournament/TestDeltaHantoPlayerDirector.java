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

import org.junit.Before;
import org.junit.Test;
import static hanto.util.HantoPieceType.*;

import static hanto.util.MoveResult.*;
import hanto.tournament.*;

import hanto.tournament.HantoGameDirector.HantoLogLevel;

import hanto.util.*;

/**
 * @author ndemarinis
 *
 */
public class TestDeltaHantoPlayerDirector {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		// Put the following code in the method where you want to run the game.

		// create two of your players (say in variable blue and red.
		HantoGameDirector director = HantoGameDirector.getInstance();
		director.initialize(new DeltaHantoPlayer(HantoPlayerColor.BLUE, false), 
				new DeltaHantoPlayer(HantoPlayerColor.RED, true));
		
		// if you want logging turned on include the next line.
		director.setLogLevel(HantoGameDirector.HantoLogLevel.BASIC);
		// if you want to limit the game to n full moves include the next line.
		director.setMoveLimit(150);
		// now play the game
		// returns a MoveResult that says who wins or if it is a draw
		assertEquals(MoveResult.OK,director.playGame());
	}

}
