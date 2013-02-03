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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
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
public class HantoBoard implements Iterable<HantoPiece> {

	// Maximum number of possible neighbors on a hex grid
	private final int MAX_NEIGHBORS = 6;
	
	private Vector<HantoPiece> pieces;
	
	
	public HantoBoard(){
		pieces = new Vector<HantoPiece>();
	}
	
	/**
	 * Add a piece to the board
	 * Note that this method DOES NOT perform any
	 * error checking to ensure the piece is in a valid position
	 * @param p The piece to add
	 */
	public void add(HantoPiece p)
	{
		pieces.add(p);
	}
	
	/**
	 * Find a piece matching a given coordinate on the board
	 * @param c Coordinate to search on the board
	 * @return the piece matching that coordinate, null if none exists
	 */
	public HantoPiece getPieceAt(HantoCoordinate c) 
	{
		HantoPiece ret = null;
		
		for(HantoPiece p : pieces)
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
		
		for(HantoPiece p : pieces) 
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
		
		for(HantoPiece p : pieces)
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
	 * 
	 * NOTE:  this name makes sense to me.  I don't understand how the suggestions in
	 * CodePro's audit rule could make more sense here.  
	 */
	public boolean contains(HantoPlayerColor c, HantoPieceType t)
	{
		boolean ret = false;
		
		for(HantoPiece p : pieces)
		{
			ret = ret || (p.getColor() == c && p.getType() == t);
		}
		
		return ret;
	}
	/**
	 * @return true if pieces are in a contiguous grouping, 
	 * false otherwise.  
	 */
	public boolean isBoardContiguous()
	{
		boolean isContiguous = true;
		
		// Now that we've added the piece, check if it doesn't violate the adjacency rules
		for(HantoPiece p : pieces)
		{
			// If everything is in one contiguous group, we should be able to
			// pick any piece on the board and find a path from it
			// to every other piece.  
			// If one fails, we broke the rules.  
			isContiguous = isContiguous && this.thereExistsPathBetween(pieces.get(0), p);
		}
		
		return isContiguous;
	}
	
	/**
	 * Find a path between two HantoCoordinates on the board using BFS.  
	 * @param a Some HantoCoordinate
	 * @param b Some other HantoCoordinate
	 * @return true if a path exists between the two
	 * 
	 * NOTE:  this name makes sense to me.  I don't understand how the suggestions in
	 * CodePro's audit rule could make more sense here.  
	 */
	public boolean thereExistsPathBetween(HantoCoordinate a, HantoCoordinate b) 
	{
		final Queue<HantoCoordinate> q = new LinkedList<HantoCoordinate>(); // Queue for our BFS
		
		// We also need something to hold visited nodes, preferably which we can
		// check in constant time.  After some research, a HashSet fills this requirement.  
		final Set<HantoCoordinate> visited = new HashSet<HantoCoordinate>();
		
		boolean ret = false;
		
		// Add the starting node.  
		q.add(a);
		visited.add(a);
		
		while(!ret && !q.isEmpty()) // Run until we find a path or we run out of nodes
		{
			HantoCoordinate c = q.remove();
			if(c.getX() == b.getX() && c.getY() == b.getY()) {
				ret = true;
			}
			
			for(HantoCoordinate neighbor : this.getNeighborsOf(c))
			{
				if(!visited.contains(neighbor)) {
					visited.add(neighbor);
					q.add(neighbor);
				}
			}
		}
		
		return ret;
	}
	
	
	/**
	 * 
	 * @return true if the board is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return pieces.isEmpty();
	}
	
	/**
	 * Create an iterator over all of the pieces on the board
	 */
	public Iterator<HantoPiece> iterator()
	{
		return pieces.iterator();
	}
	
	/**
	 * @param p Piece at HantoCoordinate to remove from the board
	 */
	public void remove(HantoCoordinate p)
	{
		pieces.remove(p);
	}
	
	
	// TODO:  There's a warning about not implementing clone()
	// I'm not sure the right way to do this, so I'm leaving it alone.  
	// Specifically, I don't know enough about making it synchronized etc.  
}
