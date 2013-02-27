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

import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.tournament.HantoMoveRecord;

import java.util.List;

/**
 * This class contains the behavior for a Hanto Player
 * strategy that randomly selects a move.  
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class RandomSelectStrategy implements HantoPlayerStrategy {

	/**
	 * Initialize a random strategy
	 */
	public RandomSelectStrategy() {
		// Nothing to do here.  
	}

	public 	HantoMoveRecord selectMove(InternalHantoGame game, 
			List<HantoMoveRecord> possibleMoves) {
		
		// Just randomly select a move.  That's it.  
		return possibleMoves.get((int)(Math.random() * possibleMoves.size()));
	}
}
