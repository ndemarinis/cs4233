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

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.AbstractHantoGame;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * Representation of DeltaHanto
 * 
 * @author ndemarinis
 * @version Feb 9, 2013
 *
 */
public class DeltaHantoGame extends AbstractHantoGame {

	private static Map<HantoPieceType, Integer> startingHand = null;
	
	// Counts of pieces in a player's hand
	private static final int MAX_BUTTERFLIES = 1;
	private static final int MAX_SPARROWS = 4;
	private static final int MAX_CRABS = 4;
	
	/**
	 * Create an instance of DeltaHanto
	 */
	public DeltaHantoGame() throws HantoException {
		state = new HantoGameState(HantoPlayerColor.BLUE);
		rules = new DeltaHantoRuleset(state);
		
		initialize(HantoPlayerColor.BLUE);
		setupGame();
	}

	@Override
	public void setupGame() {
		state.setNumMoves(0);
		state.setGameOver(false);

		state.setPlayersHand(HantoPlayerColor.BLUE, makeStartingHand());
		state.setPlayersHand(HantoPlayerColor.RED, makeStartingHand());
	}
	
	/**
	 * Create the starting hand for DeltaHanto, 
	 * using a map of pieces in a player's hand to 
	 * their respective counts 
	 * @return map of pieces available to a player at 
	 * the beginning of the game with their corresponding counts
	 */
	protected static Map<HantoPieceType, Integer> makeStartingHand()
	{
		if(startingHand == null) {
			startingHand = new HashMap<HantoPieceType, Integer>();
			startingHand.put(HantoPieceType.BUTTERFLY, MAX_BUTTERFLIES);
			startingHand.put(HantoPieceType.SPARROW, MAX_SPARROWS);
			startingHand.put(HantoPieceType.CRAB, MAX_CRABS);
		}
		
		return startingHand;
	}
	
	/**
	 * @return the player's starting hand
	 */
	public Map<HantoPieceType, Integer> getStartingHand()
	{
		return startingHand;
	}

}
