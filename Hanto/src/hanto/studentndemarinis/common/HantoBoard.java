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
import hanto.util.HantoPlayerColor;

import java.util.Collection;
import java.util.Vector;

/**
 * This class represents the hexagonal Hanto Board.  
 * It extends a class of Collection, so it implements
 * all of the nice collection-parsing methods while containing
 * logic for parsing our hex board.  
 * 
 * @author ndemarinis
 * @version Jan 23, 2013
 *
 */
public class HantoBoard extends Vector<HantoPiece> {

	// Maximum number of possible neighbors on a hex grid
	private final int MAX_NEIGHBORS = 6;
	
	
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
		final Collection<HantoPiece> res = new Vector<HantoPiece>();
		
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
		final Collection<HantoPiece> res = new Vector<HantoPiece>();
		
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
	
	
	/**
	 * Check if a particular piece is somewhere on the board
	 * @param c Color of piece to find
	 * @param t Type of piece to find
	 * @return true if at least one piece matching the type 
	 * and color are on the board
	 */
	public boolean containsPiece(HantoPlayerColor c, HantoPieceType t)
	{
		boolean ret = false;
		
		for(HantoPiece p : this)
		{
			ret = ret || (p.getColor() == c && p.getType() == t);
		}
		
		return ret;
	}
	
	// TODO:  There's a warning about not implementing clone()
	// I'm not sure the right way to do this, so I'm leaving it alone.  
	// Specifically, I don't know enough about making it synchronized etc.  
}
