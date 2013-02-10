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
import hanto.studentndemarinis.common.HantoPlayer;
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

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

	private HantoRuleSet rules;
	
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
		initialize(HantoPlayerColor.BLUE); // By default, starting player is blue
		setupGame();
	}
	
	public void setupGame() throws HantoException {	
		state.setNumMoves(0);
		state.setGameOver(false);
		
		state.setPlayersHand(HantoPlayerColor.BLUE, getStartingHand());
		state.setPlayersHand(HantoPlayerColor.RED, getStartingHand());
		
		rules = new GammaHantoRules(state);
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException 
	{
		final HexCoordinate src = HexCoordinate.extractHexCoordinate(from);
		final HexCoordinate dest = HexCoordinate.extractHexCoordinate(to);
		
		// Verify the game is not over
		if(state.isGameOver()) {
			throw new HantoException("Illegal move:  game has already ended!");
		}
		
		// Verify the source piece is valid, if provided.  
		rules.doPreMoveChecks(pieceType, src, dest);

	
		// Now that we know we can make the move, do it for realsies.  
		rules.actuallyMakeMove(pieceType, src, dest);
		
		// Make sure that move we just did was valid
		// (We're assuming that just throwing an exception is okay here,
		// the incorrect move is applied and NOT changed for now.)
		rules.doPostMoveChecks(dest);
		

		// Finish move
		completeMove();	
		
		// Return the result of the move
		return rules.evaluateMoveResult();
	}
	
	/**
	 * "Factory method" for creating the hand with which each
	 * player starts.  If the hand has not already been setup, 
	 * this method creates it.  
	 * 
	 * @return the player's initial hand
	 */
	protected static Map<HantoPieceType,Integer> getStartingHand()
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
