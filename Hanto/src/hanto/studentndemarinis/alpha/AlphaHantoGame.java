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

import hanto.common.HantoException;
import hanto.studentndemarinis.common.AbstractHantoGame;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoPiece;
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
public class AlphaHantoGame extends AbstractHantoGame {
	
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
		state = new HantoGameState(HantoPlayerColor.BLUE);
		
		// Since blue always moves first, I'm not sure how we could possibly
		// violate the rules to throw an exception here.  
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException 
	{	
		// Starting butterfly should be at origin, or else.
		if(state.getNumMoves() == 0 && (to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("Illegal move:  starting butterfly must be at origin!");
		}

		// We shouldn't be able to place any pieces other than butterflies
		if(pieceType != HantoPieceType.BUTTERFLY) {
			throw new HantoException("Illegal move:  " +
					"can't place anyting other than butterflies!");
		}

		// If we find any pieces in that location, it's not a legal move.  
		if(state.getBoard().getPieceAt(to) != null) {
			throw new HantoException("Illegal move:  can't place a piece " +
					"on top of an existing piece!");
		}
		
		// Finally, if we haven't thrown an error already, this move is valid, 
		// so add it to the "board"
		state.getBoard().add(new HantoPiece(state.getCurrPlayer(), pieceType, to));
		
		
		// Make sure the pieces are in a contiguous group after adding the new one.  
		if(!state.getBoard().isBoardContiguous()) {
			throw new HantoException("Illegal move:  piece must be adjacent to the group!");
		}
		
		
		// After placing the current piece, the current player has made a move, 
		// so switch the next player
		state.setCurrPlayer((state.getCurrPlayer() == HantoPlayerColor.BLUE) ? 
					HantoPlayerColor.RED : HantoPlayerColor.BLUE);
		
		// First move is OK if valid, then the game ends in a draw on the second move
		MoveResult ret = (state.getNumMoves() == 0) ? MoveResult.OK : MoveResult.DRAW;
		state.setNumMoves(state.getNumMoves() + 1);
		
		return ret;
		
	}

}
