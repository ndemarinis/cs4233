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

import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;

import java.util.Collection;
import java.util.Vector;

/**
 * This class represents the hexagonal Hanto Board.  
 * 
 * @author ndemarinis
 * @version Jan 23, 2013
 *
 */
public class HantoBoard extends Vector<HantoPiece> {

	// Maximum number of possible neighbors on a hex grid
	private final int MAX_NEIGHBORS = 6;
	
	/**
	 * Initialize a new HantoBoard
	 */
	public HantoBoard() {
		super();
	}
	
	/**
	 * Find a piece matching a given coordinate on the board
	 * @param c Coordinate to search on the board
	 * @return the piece matching that coordinate, null if none exists
	 */
	public HantoPiece getPieceAt(HantoCoordinate c) 
	{
		HantoPiece ret = null;
		
		for(HantoPiece p : this)
		{
			if(p.getX() == c.getX() && p.getY() == c.getY()) {
				ret = p;
			}
		}
		return ret;
	}
	
	/**
	 * Find neighbors of a specific coordinate on the board
	 * @param c Coordinate to find neighbors
	 * @return Collection of neighbors, empty if none
	 */
	public Collection<HantoPiece> getNeighborsOf(HantoCoordinate c)
	{
		Collection<HantoPiece> res = new Vector<HantoPiece>();
		
		for(HantoPiece p : this) 
		{
			if(p.isAdjacentTo(c)) {
				res.add(p);
			}
		}
		
		return res;
	}
	
	/**
	 * Get pieces with a specific PieceType
	 * @param t The type for which to search on the board
	 * @return Collection of matching pieces
	 */
	public Collection<HantoPiece> getPiecesOfType(HantoPieceType t)
	{
		Collection<HantoPiece> res = new Vector<HantoPiece>();
		
		for(HantoPiece p : this)
		{
			if(p.getType() == t) {
				res.add(p);
			}
		}
		
		return res;
	}
	
	/**
	 * Check if a coordinate is surrounded
	 * @param c Coordinate to check
	 * @return true if the specified coordinate is surrounded
	 */
	public boolean isSurrounded(HantoCoordinate c)
	{
		return this.getNeighborsOf(c).size() == MAX_NEIGHBORS;
	}
}
