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

import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 *
 * This class provides an abstraction for a piece on the Hanto Board.  
 * It maintains the type, location, etc. of each piece to determine
 * movement properties and rules.  
 * @author ndemarinis
 * @version Jan 22, 2013
 */
public class HantoPiece {

	private final HantoPlayerColor color; // The color of the player that placed this piece 
	private final HantoPieceType type; // The type of piece placed here
	private HexCoordinate coordinate; // The piece's location
	
	/**
	 * Creates a Hanto piece.  
	 * 
	 * @param color The color of the player placing the piece
	 * @param type The type of piece as played
	 * @param loc The coordinate of the piece on the hex grid
	 */
	public HantoPiece(HantoPlayerColor color, HantoPieceType type, 
			HexCoordinate coordinate) 
	{	
		this.color = color;
		this.type = type;
		this.coordinate = coordinate;
	}

	/**
	 * @return color the color of the player that placed this piece
	 */
	public HantoPlayerColor getColor() {
		return color;
	}


	/**
	 * @return The type of the piece placed here
	 */
	public HantoPieceType getType() {
		return type;
	}

	
	/**
	 * @return Coordinate associated with this piece
	 */
	public HexCoordinate getCoordinate() {
		return coordinate; 
	}
	
	public void setCoordinate(HexCoordinate c) {
		coordinate = c;
	}
	
	/** 
	 * @return A string representation of the piece
	 */
	public String toString() 
	{
		return color + " " + type + " at " + 
				((this.coordinate == null) ? "<unplaced>" : this.coordinate);
	}
	
	public int hashCode()
	{
		return this.toString().hashCode();
	}

}
