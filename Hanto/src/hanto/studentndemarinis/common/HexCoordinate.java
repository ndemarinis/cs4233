/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html * 
 */
package hanto.studentndemarinis.common;


import hanto.util.HantoCoordinate;

/**
 * This class provides an internal implementation
 * for representing HantoCoordinates on a Hexagonal
 * board.  
 *   
 * @author ndemarinis
 * @version Jan 21, 2013
 *
 */
public class HexCoordinate implements HantoCoordinate {

	private final int x, y;
	
	/**
	 * Make a new HexCoordinate with the specified coordinates
	 * @param x coordinate on x-axis
	 * @param y coordinate on y-axis
	 */
	public HexCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Static method to create or extract HexCoordinates from
	 * given HantoCoordinates.  
	 * 
	 * I had a constructor for this, but after seeing Prof. Pollice's
	 * "factory method" implementation in class, I wanted to try it instead.  
	 * This is neat because it doesn't create a new object if the coordinate
	 * is already a HexCoordinate. 
	 *  
	 * @param c Coordinate to check
	 * @return a HexCoordinate representing the given coordinate.  If c is
	 * a HantoCoordinate, a HexCoordinate will be made with its coordinates.  
	 * If c is a HexCoordinate, it will be returned unchanged.  
	 * For any other inputs, this function returns null.  
	 */
	public static HexCoordinate extractHexCoordinate(HantoCoordinate c)
	{
		return (c == null) 					  ?                                  null :
			   (c instanceof HexCoordinate)   ?                    (HexCoordinate)(c) :
				   								 new HexCoordinate(c.getX(), c.getY());
	}
	
	/**
	 * @return x coordinate of HexCoordinate
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * @return y coordinate of HexCoordinate
	 */
	@Override
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @param other Some other coordinate
	 * @return true if this coordinate is adjacent to this one
	 * using a hexagonal coordinate system
	 */
	public boolean isAdjacentTo(HantoCoordinate other)
	{
		return (other.getX() == x     && other.getY() == y + 1) ||
 			   (other.getX() == x + 1 && other.getY() == y    ) ||
			   (other.getX() == x + 1 && other.getY() == y - 1) ||
		       (other.getX() == x     && other.getY() == y - 1) ||
			   (other.getX() == x - 1 && other.getY() == y    ) || 
			   (other.getX() == x - 1 && other.getY() == y + 1);
	}
	
	/**
	 * @return array of coordiantes that are adjacent to this one
	 */
	public HexCoordinate[] getNeighboringCoordinates()
	{		
		final HexCoordinate[] coords = {new HexCoordinate(    x, y + 1), 
								  		new HexCoordinate(x + 1,     y), 
								  		new HexCoordinate(x + 1, y - 1),
								  		new HexCoordinate(    x, y - 1),
								  		new HexCoordinate(x - 1,     y), 
								  		new HexCoordinate(x - 1, y + 1)};
		return coords;
	}
	
	/**
	 *  @return true if both coordinates have the same
	 *  x and y coordinates
	 *  
	 *  CodePro is telling me that I'm not checking instanceof here; 
	 *  however, it is wrong--I am just doing a nullcheck first.  
	 */
	public boolean equals(Object obj) 
	{
		boolean ret = false; // I have NO idea why CodePro wants this to be final.  It's wrong.  
		
		if(obj == null) {
			ret = false;
		} else if(obj instanceof HexCoordinate) {
			final HexCoordinate hc = (HexCoordinate)obj;
			ret = (this == obj) || (x == hc.getX() && y == hc.getY());
		}
		
		return ret;
	}
	
	public int hashCode()
	{
		return (x * 10000) + y;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

}
