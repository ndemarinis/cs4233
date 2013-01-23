/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.gamma;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * GammaHanto - Extended hanto implementation
 * supporting Butterflies, Sparrows, and movement 
 * of pieces.  Ends after 10 turns.  
 * 
 * @author ndemarinis
 * @version Jan 21, 2013
 */

/*
 * NOTE:  I started GammaHanto by starting with AlphaHanto and MASSIVELY
 * refactoring it.  This felt like the right way to start for me.  
 * If I was totally off base in doing this, I plead ignorance.  
 */

public class GammaHantoGame implements HantoGame {

	private int numMoves; // Total number of moves elapsed in the game so far
	private HantoPlayerColor currPlayer; // Player that making the current/next move
	
	// Number of moves before we MUST place a butterfly
	private final int NUM_MOVES_PRE_BUTTERFLY = 3;
	
	// Maximum number of possible neighbors on a hex grid
	private final int MAX_NEIGHBORS = 6;
	
	// Collection of pieces representing the board for now
	Collection<HantoPiece> board;
	
	// Map of player colors to their hands (for now)
	Map<HantoPlayerColor, GammaHantoPlayer> players = 
			new HashMap<HantoPlayerColor, GammaHantoPlayer>();
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	public GammaHantoGame() throws HantoException {
		this.initialize(HantoPlayerColor.BLUE); // By default, starting player is blue
	}
	
	
	// NOTE:  CodePro throws a warning here about the missing exception.  
	// 	While it's not technically necessary, I'm leaving it since it's in
	// the interface.  
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException {
		numMoves = 0;
		currPlayer = firstPlayer;
		board = new Vector<HantoPiece>();
		
		// Initialize each player's hand.  
		players.put(HantoPlayerColor.BLUE, new GammaHantoPlayer());
		players.put(HantoPlayerColor.RED, new GammaHantoPlayer());
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException 
	{
		boolean isValid = false;
		
		// Verify the source piece is valid, if provided.  
		if(from != null) 
		{
			if(!this.doesPieceExistAt(from)) {
				throw new HantoException("Illegal move:  " +
						"source piece does not exist on board!");
			}
			
			if(this.getPieceAt(from).getColor() != currPlayer) {
				throw new HantoException("Illegal move:  your can only move pieces" +
						"of your own color!");
			}
			
		}
		
		
		// Verify the destination is valid, provided the board is populated.  
		if(!board.isEmpty())
		{
			for(HantoPiece p : board) {
				
				// See if at least one piece is adjacent to the proposed move
				if(p.isAdjacentTo(to)) {
					isValid = isValid || true;
				}
			}

			if(!isValid) {
				throw new HantoException("Illegal move:  piece must be adjacent to the group!");
			}
			
			// If we find any pieces in the destination, it's not a legal move.  
			if(this.doesPieceExistAt(to)){
				throw new HantoException("Illegal move:  can't place a piece " +
						"on top of an existing piece!");
			}
		}
		
		// Verify the piece type is valid for this move
		if(numMoves >= NUM_MOVES_PRE_BUTTERFLY && pieceType != HantoPieceType.BUTTERFLY) {
			throw new HantoException("Illegal move:  " +
					"Butterfly must be placed by the foruth turn!");
		}
		
		
		// If we were moving a piece, remove the old piece from the "board"
		if(from != null) {
			board.remove(new HexCoordinate(from));
		} else { // Otherwise, we're placing a piece, so try to remove one from the player's hand
			players.get(currPlayer).removeFromHand(pieceType);
		}
		
		// Finally, if we haven't thrown an error already, this move is valid, 
		// so add it to the "board"
		board.add(new HantoPiece(currPlayer, pieceType, to));
		
		// After placing the current piece, the current player has made a move, 
		// so switch the next player
		currPlayer = (currPlayer == HantoPlayerColor.BLUE) ? 
					HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		// Check win conditions (max number of moves, butterfly surrounded)
		MoveResult ret = (++numMoves != 10) ? MoveResult.OK : MoveResult.DRAW;
		
		for(HantoPiece p : getPiecesOfType(HantoPieceType.BUTTERFLY))
		{
			if(getNeighborsOf(p).size() == MAX_NEIGHBORS) {
				ret = (p.getColor() == HantoPlayerColor.BLUE) ? 
						MoveResult.RED_WINS : MoveResult.BLUE_WINS;
			}
		}
		
		// First move is OK if valid, then the game ends in a draw on the second move
		return ret;
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
	 * @return true if a piece exists on the board
	 * @param c coordinate to check for a piece
	 * 
	 * NOTE:  CodePro's warning doesn't make sense to me.  
	 * The name of this method seems accurate.  
	 * If there's a better way to do this, please let me know.  
	 */
	public boolean doesPieceExistAt(HantoCoordinate c) {
		return this.getPieceAt(c) != null;
	}

	/**
	 * Add a coordinate to the board
	 * @param color color of new piece
	 * @param type type of new piece
	 * @param c location of new piece
	 */
	public void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c) {
		final HantoPiece p = new HantoPiece(color, type, c);
		board.add(p);
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
	 * @return the current player up for a move
	 */
	public HantoPlayerColor getCurrPlayer() {
		return currPlayer;
	}


	/**
	 * @param currPlayer player set to be next to move
	 */
	public void setCurrPlayer(HantoPlayerColor currPlayer) {
		this.currPlayer = currPlayer;
	}

	/**
	 * Find a piece matching a given coordinate on the board
	 * @param c Coordinate to search on the board
	 * @return the piece matching that coordinate, null if none exists
	 */
	private HantoPiece getPieceAt(HantoCoordinate c) 
	{
		HantoPiece ret = null;
		
		for(HantoPiece p : board)
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
	private Collection<HantoPiece> getNeighborsOf(HantoCoordinate c)
	{
		Collection<HantoPiece> res = new Vector<HantoPiece>();
		
		for(HantoPiece p : board) 
		{
			if(p.isAdjacentTo(c)) {
				res.add(p);
			}
		}
		
		return res;
	}
	
	
	private Collection<HantoPiece> getPiecesOfType(HantoPieceType t)
	{
		Collection<HantoPiece> res = new Vector<HantoPiece>();
		
		for(HantoPiece p : board)
		{
			if(p.getType() == t) {
				res.add(p);
			}
		}
		
		return res;
	}
	// TODO:  When we know more about the board, I can write
	// HashCode in such a way that is works with a real
	// board implementation.  For now, I'm ignoring the warning.
}
