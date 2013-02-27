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
 * This class abstracts the behavior for selecting
 * moves based on a predefined pattern.  
 * When the pattern runs out of moves, we pick random ones
 * based on RandomSelect strategy.  
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class PartialFakedSelectStrategy implements HantoPlayerStrategy {

	private HantoMoveRecord[] moves;
	private HantoPlayerStrategy randomStrategy = new RandomSelectStrategy();
	
	private int moveIndex;
	
	/**
	 * Create a partially-faked strategy
	 * @param moves Array of moves this player should make
	 */
	public PartialFakedSelectStrategy(HantoMoveRecord[] moves) {
		this.moves = moves;
		moveIndex = 0;
	}

	@Override
	public HantoMoveRecord selectMove(InternalHantoGame game,
			List<HantoMoveRecord> possibleMoves) {
		
		HantoMoveRecord ret;
		
		if(moveIndex < moves.length) {
			ret = moves[moveIndex++];
		} 
		else {
			ret = randomStrategy.selectMove(game, possibleMoves);
		}
		
		return ret;
	}

}
