/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.alpha;

import java.util.Collection;
import java.util.Vector;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * AlphaHanto - Initial Hanto implementation
 * @author ndemarinis
 * @version Jan 21, 2013
 *
 */
public class AlphaHanto implements HantoGame {

	private int numMoves; // Total number of moves elapsed in the game so far
	private HantoPlayerColor nextPlayer; // Next player to make a move.
	
	Collection<HexCoordinate> board;
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	public AlphaHanto() throws HantoException {
		this.initialize(HantoPlayerColor.BLUE);
		board = new Vector<HexCoordinate>();
	}
	
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// 	While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException {
		numMoves = 0;
		nextPlayer = firstPlayer;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException 
	{
		boolean isValid = false;
		MoveResult ret; // I have NO idea why CodePro wants this to be final.  It's wrong.  
		
		// Starting butterfly should be at origin, or else.
		if(numMoves == 0 && (to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("Illegal move:  starting butterfly must be at origin!");
		}


		// If there are pieces on the board, look at the existing ones 
		// to determine if this is a valid move
		if(!board.isEmpty())
		{
			for(HexCoordinate c : board) {
				
				// See if at least one piece is adjacent to the proposed move
				if(c.isAdjacentTo(to)) {
					isValid = isValid || true;
				}
				
				// If we find any pieces in that location, it's not a legal move.  
				if(c.equals(to)) {
					throw new HantoException("Illegal move:  can't place a piece " +
							"on top of an existing piece!");
				}
			}

			if(!isValid) {
				throw new HantoException("Illegal move:  piece must be adjacent to the group!");
			}
		}
		
		// Finally, if we haven't thrown an error already, this move is valid, 
		// so add it to the "board"
		board.add(new HexCoordinate(to.getX(), to.getY()));
		
		// After placing the current piece, the current player has made a move, 
		// so switch the next player
		nextPlayer = (nextPlayer == HantoPlayerColor.BLUE) ? 
					HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		// First move is OK if valid, then the game ends in a draw on the second move
		ret = (numMoves++ == 0) ? MoveResult.OK : MoveResult.DRAW;
		
		return ret;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @return the number of moves made in this game
	 */
	public int getNumMoves() {
		return numMoves;
	}


	/**
	 * @param numMoves the number of moves to set
	 */
	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}


	/**
	 * @return the next player that can make a move
	 */
	public HantoPlayerColor getNextPlayer() {
		return nextPlayer;
	}


	/**
	 * @param nextPlayer Player to make the next move
	 */
	public void setNextPlayer(HantoPlayerColor nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

}
