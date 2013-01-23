/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html * 
 */
package hanto.studentndemarinis.alpha;

import hanto.util.HantoCoordinate;

/**
 * This class provides the implementation for 
 * Hanto's Hexagonal coordinates
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
	 * Make a new HexCoordinate based on a generic
	 * HantoCoordinate.  
	 * 
	 * @param c The HantoCoordinate to turn into a HexCoordinate
	 */
	public HexCoordinate(HantoCoordinate c)
	{
		x = c.getX();
		y = c.getY();
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
			HantoCoordinate other = (HantoCoordinate)obj;
			ret = (x == other.getX() && y == other.getY());
		}
	
		return ret;
	}

}
