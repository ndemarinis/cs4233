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

import org.junit.Before;
import org.junit.Test;


/**
 * Test cases for DeltaHantoPlayer.  
 * 
 * @author ndemarinis
 * @version Feb 25, 2013
 */
public class TestDeltaHantoPlayer {


	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void canCreateDeltaHantoPlayer()
	{
		DeltaHantoPlayer player = new DeltaHantoPlayer(BLUE, false);
	}
}
