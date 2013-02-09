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

	final int x, y;
	
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
			   (c instanceof HantoCoordinate) ? new HexCoordinate(c.getX(), c.getY()) :
				   			                                                      null;
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
	 *  @return true if both coordinates have the same
	 *  x and y coordinates
	 */
	public boolean equals(Object obj) 
	{
		boolean ret = false; // I have NO idea why CodePro wants this to be final.  It's wrong.  
		
		if(obj instanceof HantoCoordinate) {
			// I think this cast is making CodePro throw a warning here. 
			// Since I AM actually comparing things, I am ignoring the warning.  
			// If I'm dong this wrong, please let me know.  
			final HantoCoordinate other = (HantoCoordinate)obj;
			ret = (x == other.getX() && y == other.getY());
		}
	
		return ret;
	}
	
	public int HashCode()
	{
		return x*10000+y;
	}

}
