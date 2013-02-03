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
import hanto.studentndemarinis.common.AbstractHantoGame;
import hanto.studentndemarinis.common.HantoBoard;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import java.util.HashMap;
import java.util.Map;

/**
 * GammaHanto - Extended hanto implementation
 * supporting Butterflies, Sparrows, and movement 
 * of pieces.  Ends after 10 turns or when butterfly 
 * is surrounded.  
 * 
 * @author ndemarinis
 * @version Jan 21, 2013
 */

/*
 * NOTE:  I started GammaHanto by starting with AlphaHanto and MASSIVELY
 * refactoring it.  This felt like the right way to start for me.  
 * If I was totally off base in doing this, I plead ignorance.  
 * 
 * I did, however, start with a completely new set of tests.  
 */

public class GammaHantoGame extends AbstractHantoGame {

	private HantoRuleSet rules;
	
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
		state = new HantoGameState(firstPlayer);
		rules = new GammaHantoRules(state);
		
		// Initialize each player's hand.  
		players.put(HantoPlayerColor.BLUE, new GammaHantoPlayer());
		players.put(HantoPlayerColor.RED, new GammaHantoPlayer());
		
		// Based on the specification, red can move first even though it's
		// technically not in the rules if we initialize the board that way,
		// therefore, I don't see how we can be breaking a rule here.  
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException 
	{
		
		// Verify the game is not over
		if(state.isGameOver()) {
			throw new HantoException("Illegal move:  game has already ended!");
		}
		
		// Verify the source piece is valid, if provided.  
		rules.doPreMoveChecks(pieceType, from, to);

	
		// Now that we know we can make the move, do it for realsies.  
		actuallyMakeMove(pieceType, from, to);
		
		// Make sure that move we just did was valid
		// (We're assuming that just throwing an exception is okay here,
		// the incorrect move is applied and NOT changed for now.)
		rules.doPostMoveChecks(to);
		
		
		// If this move involved placing a new piece, remove it from the player's hand
		if(from == null) {
			players.get(state.getCurrPlayer()).removeFromHand(pieceType);
		}
		
		// After placing the current piece, the current player has made a move, 
		// so switch the next player
		state.setCurrPlayer((state.getCurrPlayer() == HantoPlayerColor.BLUE) ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE);
		
		state.setNumMoves(state.getNumMoves() + 1);
		
		// Determine if this move ended the game
		MoveResult ret = rules.evaluateWinConditions();
		if(ret == MoveResult.DRAW || 
				ret == MoveResult.RED_WINS || ret == MoveResult.BLUE_WINS)
			state.setGameOver(true);
		
		return ret;
	}
	
	/**
	 * Make a move, regardless of whether or not it is valid.
	 * Any piece currently at the source and destination locations
	 * are REMOVED when this method is calle.d  
	 * 
	 * @param type Piece type to place at the destination
	 * @param from Source coordinate of the piece, null if piece is not on the board
	 * @param to Destination coordinate of the piece
	 */
	private void actuallyMakeMove(HantoPieceType type, HantoCoordinate from, HantoCoordinate to)
	{
		// Remove the old piece from the board (if we haven't failed yet)
		if(from != null) {
			state.getBoard().remove(from);
		}
		
		// Finally, add the new piece to the board.  
		state.getBoard().add(new HantoPiece(state.getCurrPlayer(), type, to));
	}
	
	// TODO:  When we know more about the board, I can write
	// HashCode in such a way that is works with a real
	// board implementation.  For now, I'm ignoring the warning.
}
