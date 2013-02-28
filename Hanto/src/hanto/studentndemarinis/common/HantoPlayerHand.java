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
public class HantoPlayerHand {
	
	// Map representing number of pieces of each type available for play
	private final Map<HantoPieceType,Integer> hand;
	
	/**
	 * This class provides an abstraction for each player in GammaHanto.  
	 * It maintains the types and numbers of pieces available for play.
	 * 
	 * @param hand Map of pieces to the number of which the player has
	 * available to use
	 * 
	 */
	public HantoPlayerHand(Map<HantoPieceType,Integer> hand)
	{
		this.hand = new HashMap<HantoPieceType, Integer>();
		
		if(hand != null) {
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
	
	/**
	 * Sets a player's hand, overwriting its previous hand.  
	 * @param hand Map of pieces to counts indicating how many of each the player can use, 
	 * setting this to null will clear the player's hand.  
	 */
	public void setHand(Map<HantoPieceType, Integer> hand)
	{
		this.hand.clear();
		
		if(hand != null) {
			this.hand.putAll(hand);
		}
	}
	
	/**
	 * Get number of pieces a player has available for play
	 * @param p the piece to check
	 * @return number of pieces available, 0 if none exist
	 */
	public int getRemainingPiecesToPlay(HantoPieceType p)
	{
		final Integer numPieces = hand.get(p);
		
		return numPieces == null ? 0 : numPieces.intValue();
	}

}
