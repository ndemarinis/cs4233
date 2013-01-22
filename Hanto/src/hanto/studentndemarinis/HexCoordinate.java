/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html * 
 */
package hanto.studentndemarinis;

import hanto.util.HantoCoordinate;

/**
 * This class provides the implementation for 
 * Hanto's Hexagonal coordinates
 * @author ndemarinis
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

}
