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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import hanto.tournament.*;

import hanto.util.*;

/**
 * @author ndemarinis
 *
 */
public class TestDeltaHantoPlayerDirector {

	/*
	 * Instead of throwing exceptions, we print to stderr before resigning
	 * if we detect an error.  
	 * To check this with the director, we can see if anything has been printed to
	 * stderr.  Source:  http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
	 */
	private final ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
	private final int NUM_RUNS = 5;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.setErr(new PrintStream(errBuffer));
	}

	@Test
	public void test() {
		for(int i = 0; i < NUM_RUNS; i++) {
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
			director.playGame();

			// If the director didn't throw an exception and we
			// haven't put anything in stderr, this must have worked.
			assertEquals("", errBuffer.toString());
		}
	}
	
	@After
	public void finish() {
		System.setErr(System.err);
	}

}
