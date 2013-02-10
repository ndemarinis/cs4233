/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.gamma;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.AbstractHantoGame;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * GammaHanto - Extended Hanto implementation
 * supporting Butterflies, Sparrows, and movement 
 * of pieces.  Ends after 10 turns or when butterfly 
 * is surrounded.  
 * 
 * @author ndemarinis
 * @version Jan 21, 2013
 */

/*
 * NOTE:  I started GammaHanto by starting with AlphaHanto and MASSIVELY
 * refactoring it.  This felt like the right way to start for me.  
 * If I was totally off base in doing this, I plead ignorance.  
 * 
 * I did, however, start with a completely new set of tests.  
 */

public class GammaHantoGame extends AbstractHantoGame {
	
	// Maximum piece counts for this game
	private static final int MAX_BUTTERFLIES = 1;
	private static final int MAX_SPARROWS = 5;
	
	// Representation for pieces given to a player on initialization
	// This is controlled by the factory method getStartingHand()
	private static Map<HantoPieceType,Integer> startingHand = null;
	
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	public GammaHantoGame() throws HantoException {	
		state = new HantoGameState(HantoPlayerColor.BLUE);
		rules = new GammaHantoRuleset(state);
		
		initialize(HantoPlayerColor.BLUE); // By default, starting player is blue
		setupGame();
	}
	
	public void setupGame() {	
		state.setNumMoves(0);
		state.setGameOver(false);
		
		state.setPlayersHand(HantoPlayerColor.BLUE, makeStartingHand());
		state.setPlayersHand(HantoPlayerColor.RED, makeStartingHand());
	}
	
	/**
	 * "Factory method" for creating the hand with which each
	 * player starts.  If the hand has not already been setup, 
	 * this method creates it.  
	 * 
	 * @return the player's initial hand
	 */
	protected static Map<HantoPieceType,Integer> makeStartingHand()
	{
		if(startingHand == null){
			startingHand = new HashMap<HantoPieceType, Integer>();
			startingHand.put(HantoPieceType.BUTTERFLY, MAX_BUTTERFLIES);
			startingHand.put(HantoPieceType.SPARROW, MAX_SPARROWS);
		}
		
		return startingHand;
	}

	
	// TODO:  When we know more about the board, I can write
	// HashCode in such a way that is works with a real
	// board implementation.  For now, I'm ignoring the warning.
}
