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
 * This interface describes the functionality necessary
 * for a strategy to select moves for a Hanto Player's AI
 * 
 * This encapsulates the strategy for selecting a move from
 * the set of available moves away from the Hanto Player.  
 * 
 * This is most useful for faking move selections for testing
 * purposes.  
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public interface HantoPlayerStrategy {

	/**
	 * Select a move based on the set of possible moves
	 * @param game The HantoGame (internal to the player), 
	 * which may be useful for simulation (could turn into the state)
	 * @param possibleMoves List of possible moves
	 * @return Move record for "best" move based on this strategy
	 */
	HantoMoveRecord selectMove(InternalHantoGame game, List<HantoMoveRecord> possibleMoves);
}
