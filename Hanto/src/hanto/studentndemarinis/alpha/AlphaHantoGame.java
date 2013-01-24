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
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HexCoordinate;
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
public class AlphaHantoGame implements HantoGame {

	private int numMoves; // Total number of moves elapsed in the game so far
	private HantoPlayerColor currPlayer; // Player currently making a move, or about to make one
	
	Collection<HantoPiece> board;
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	public AlphaHantoGame() throws HantoException {
		this.initialize(HantoPlayerColor.BLUE); // As specified, blue always moves first.
	}
	
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// 	While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException {
		numMoves = 0;
		currPlayer = HantoPlayerColor.BLUE; // As specified, blue always moves first
		board = new Vector<HantoPiece>(); 
		
		// Since blue always moves first, I'm not sure how we could possibly
		// violate the rules to throw an exception here.  
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException 
	{
		boolean isValid = false;  
		
		// Starting butterfly should be at origin, or else.
		if(numMoves == 0 && (to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("Illegal move:  starting butterfly must be at origin!");
		}

		// We shouldn't be able to place any pieces other than butterflies
		if(pieceType != HantoPieceType.BUTTERFLY) {
			throw new HantoException("Illegal move:  " +
					"can't place anyting other than butterflies!");
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
		board.add(new HantoPiece(currPlayer, pieceType, to));
		
		// After placing the current piece, the current player has made a move, 
		// so switch the next player
		currPlayer = (currPlayer == HantoPlayerColor.BLUE) ? 
					HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		// First move is OK if valid, then the game ends in a draw on the second move
		return ((numMoves++ == 0) ? MoveResult.OK : MoveResult.DRAW);
		
	}

	/**
	 * Return a string representing the current state of the board,
	 * empty string if the board is empty.  
	 */
	@Override
	public String getPrintableBoard() {
		String ret = "";
		
		for(HantoPiece p : board) {
			ret += (p + "\n");
		}
		
		return ret;
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
	public HantoPlayerColor getCurrPlayer() {
		return currPlayer;
	}


	/**
	 * @param nextPlayer Player to make the next move
	 */
	public void setCurrPlayer(HantoPlayerColor currPlayer) {
		this.currPlayer = currPlayer;
	}

}
