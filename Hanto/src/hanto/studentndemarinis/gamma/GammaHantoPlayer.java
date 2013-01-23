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
import hanto.util.HantoPieceType;

/**
 * @author ndemarinis
 * @version Jan 23, 2012
 */
public class GammaHantoPlayer {

	private final int MAX_BUTTERFLIES = 1;
	private final int MAX_SPARROWS = 5;
	
	private final Map<HantoPieceType,Integer> pieces = new HashMap<HantoPieceType,Integer>();
	
	/**
	 * This class provides an abstraction for each player in GammaHanto.  
	 * It maintains the types and numbers of pieces available for play.
	 * 
	 * This is all completely hard-coded now because it's supposed
	 * to be the simplest thing that works.  
	 * It' s making me cringe, but I am TRYING to embrace TDD.  
	 */
	public GammaHantoPlayer() {
		pieces.put(HantoPieceType.BUTTERFLY, MAX_BUTTERFLIES);
		pieces.put(HantoPieceType.SPARROW, MAX_SPARROWS);
	}
	
	/**
	 * Remove a piece of some type from the player's hand
	 * @param type Type to remove from their hand
	 * @throws HantoException if player doesn't have any pieces of that type
	 */
	public void removeFromHand(HantoPieceType type) throws HantoException
	{
		Integer remaining = pieces.get(type);
		
		if(remaining != null && remaining > 0) {
			pieces.put(type, --remaining);
		} else {
			throw new HantoException("Illegal move:  " +
					"Current player has no pieces to play of specified type!");
		}
	}

}
