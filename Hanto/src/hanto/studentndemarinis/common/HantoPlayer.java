/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.common;

import hanto.common.HantoException;
import hanto.util.HantoPieceType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ndemarinis
 * @version Jan 23, 2012
 */
public class HantoPlayer {
	
	// Map representing number of pieces of each type available for play
	private final Map<HantoPieceType,Integer> hand = new HashMap<HantoPieceType, Integer>();
	
	/**
	 * This class provides an abstraction for each player in GammaHanto.  
	 * It maintains the types and numbers of pieces available for play.
	 */
	public HantoPlayer(Map<HantoPieceType,Integer> hand) throws HantoException
	{
		if(hand == null) {
			throw new HantoException("Player must be initialized with a hand!");
		} else {
			this.hand.putAll(hand);
		}
	}
	
	/**
	 * Remove a piece of some type from the player's hand
	 * @param type Type to remove from their hand
	 * @throws HantoException if player doesn't have any pieces of that type
	 */
	public void removeFromHand(HantoPieceType type) throws HantoException
	{
		Integer remaining = hand.get(type);
		
		if(remaining != null && remaining > 0) {
			hand.put(type, --remaining);
		} else {
			throw new HantoException("Illegal move:  " +
				 "Current player has no pieces of type " + type + " to play!");
		}
	}

}
