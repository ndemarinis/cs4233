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

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * @author ndemarinis
 * @version Jan 31, 2013
 *
 */
public abstract class AbstractHantoGame implements HantoGame, InternalHantoGame {

	protected HantoGameState state;
	protected HantoRuleSet rules;
	
	/**
	 * Abstract HantoGame providing basic implementation
	 */
	
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException {
		
		// Setup all of the initial conditions
		state.setCurrPlayer(firstPlayer);
		setupGame();
		
		// Reset the board
		state.board.reset();
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		final HexCoordinate src = HexCoordinate.extractHexCoordinate(from);
		final HexCoordinate dest = HexCoordinate.extractHexCoordinate(to);

		// Verify the source piece is valid, if provided.  
		rules.doPreMoveChecks(pieceType, src, dest);

		// Now that we know we can make the move, do it for realsies.  
		rules.actuallyMakeMove(pieceType, src, dest);

		// Make sure that move we just did was valid
		// (We're assuming that just throwing an exception is okay here,
		// the incorrect move is applied and NOT changed for now.)
		rules.doPostMoveChecks(dest);


		// Finish move
		completeMove();	

		// Return the result of the move
		return rules.evaluateMoveResult();
	}

	
	/**
	 * Game-specific method for performing any necessary setup tasks,
	 * called by initialize().     
	 */
	public abstract void setupGame();
	
	
	/**
	 * Return a string representing the current state of the board,
	 * empty string if the board is empty.  
	 */
	@Override
	public String getPrintableBoard() {
		return state.getBoard().getPrintableBoard();
	}
	
	/**
	 * @return true if a piece exists on the board
	 * @param c coordinate to check for a piece
	 * 
	 * TODO:  Move this to the test harness?
	 * 
	 * NOTE:  this name makes sense to me.  
	 * I don't understand how the suggestions in
	 * CodePro's audit rule could make more sense here.   
	 */
	public boolean doesPieceExistAt(HantoCoordinate c) {
		final HexCoordinate h = HexCoordinate.extractHexCoordinate(c);
		
		return state.getBoard().getPieceAt(h) != null;
	}

	/**
	 * Add a coordinate to the board
	 * 
	 * TODO:  Move this to the test harness
	 * 
	 * @param color color of new piece
	 * @param type type of new piece
	 * @param c location of new piece
	 */
	public void addToBoard(HantoPlayerColor color, HantoPieceType type, HantoCoordinate c) {
		final HexCoordinate hc = HexCoordinate.extractHexCoordinate(c);
		final HantoPiece p = new HantoPiece(color, type, hc);
		
		state.board.addPieceAt(p, hc);
	}

	/**
	 * @return the representation of the Hanto Board.  
	 */
	public HantoBoard getBoard() {
		return state.board;
	}
	
	/**
	 * @return the number of moves made in this game
	 */
	public int getNumMoves() {
		return state.numMoves;
	}

	/**
	 * Perform actions necessary to finish a move, 
	 * committing it as valid
	 * 
	 * Currently switches the current player and increments
	 * the total number of moves
	 */
	public void completeMove() {
		// After placing the current piece, the current player has made a move, 
		// so switch the next player
		state.currPlayer = (state.currPlayer == HantoPlayerColor.BLUE) ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		// Increment the number of moves
		state.numMoves++;
	}

	/**
	 * @param numMoves the number of moves to set
	 */
	public void setNumMoves(int numMoves) {
		state.setNumMoves(numMoves);
	}


	/**
	 * @return the current player up for a move
	 */
	public HantoPlayerColor getCurrPlayer() {
		return state.getCurrPlayer();
	}

}
