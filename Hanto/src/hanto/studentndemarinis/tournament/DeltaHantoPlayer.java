/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.tournament;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.HantoPlayerHand;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Skeleton for eventual DeltaHantoPlayer
 * @author ndemarinis
 * @version Feb 25, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer {

	HantoPlayerColor color; // Our color in this game
	
	private SimulatedHantoGame game;
	private HantoPlayerStrategy strategy;
	private HantoPlayerHand hand; // Available pieces for play, represented similarly in the game itself
	
	// This enum describes how we can place pieces:  
	private enum MoveState { 	        STARTING,   // This is the first move, we only have one option
				 			MUST_PLACE_BUTTERFLY,   // We MUST place the butterfly on this move
				 				      PLACE_ONLY,   // We can only place pieces
				 				  PLACE_AND_MOVE }; // We can place and move pieces
	MoveState moveState;
	
	private static final int NUM_MOVES_PRE_BUTTERFLY = 3; // Fake it and copypaste this
	private static final int NUM_MOVES_BEFORE_CARE_ABOUT_COLOR_ADJACENCY = 1;
	
	/**
	 * Create a new DeltaHantoPlayer, as given
	 * @param color Color for the player, given by tournament
	 * @param starting true if this player is making the starting move
	 */
	public DeltaHantoPlayer(HantoPlayerColor color, boolean isStarting) throws HantoException{
		this(color, isStarting, null);
	}
	
	/**
	 * Create a new DeltaHantoPlayer, as given
	 * @param color Color for the player
	 * @param isStarting true if this player is making a starting move
	 * @param strategy PlayerStrategy to use when selecting moves
	 * @throws HantoException if anything goes wrong
	 * 
	 * TODO: THIS SHOULD NOT THROW AN EXCEPTION
	 */
	public DeltaHantoPlayer(HantoPlayerColor color, boolean isStarting, 
			HantoPlayerStrategy strategy) throws HantoException {
		this.color = color;
		moveState = isStarting ? MoveState.STARTING : MoveState.PLACE_ONLY;
		
		game = new SimulatedHantoGame(HantoGameID.DELTA_HANTO);
		hand = new HantoPlayerHand(game.getStartingHand()); // Setup our available pieces
		
		// If we've passed in a strategy, use it.  Otherwise, pick the random one.  
		if(strategy == null) {
			this.strategy = new RandomSelectStrategy();
		} else {
			this.strategy = strategy;
		}
	}
	
	/**
	 * Make the player's next move.
	 * @param opponentsMove this is the result of the opponent's last move, in response
	 * 	to your last move. This will be null if you are making the first move of the game.
	 * @return your move
	 */
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		List<HantoMoveRecord> possibleMoves;
		
		if(opponentsMove != null) // If this wasn't the starting move 
		{
			try {
				game.makeMove(opponentsMove); // Record the opponent's move on our board
			} catch (HantoException e) {
				throw new HantoPlayerException("Opponent's move was bad!  You wrote a bad test!  " +
						"Exception was:  " + e.getMessage());
			}
		}
		
		determineCurrentMoveState();
		possibleMoves = findPlacementMoves();
		
		// Pick a random move based on those given strategy
		// If no moves are available, resign.  
		HantoMoveRecord selectedMove = possibleMoves.isEmpty() ? null : 
			strategy.selectMove(game, possibleMoves);

		if(selectedMove != null) {
			// Run this move on our game, not only so we update our state, 
			// but to verify it against the rules.  
			try {
				// Try to actually make the move
				game.makeMove(selectedMove);

				// If we are placing a piece, remove it from our hand
				if(selectedMove.getFrom() == null) { 
					hand.removeFromHand(selectedMove.getPiece());
				}

			} catch(HantoException e) {
				System.out.println("Failure:  " + e.getMessage() + "\nMove was:  " + getPrintableMove(selectedMove));

				throw new HantoPlayerException("NOO!  Our move was bad! " +
						"Something has gone horribly wrong!   Message was:  " + e.getMessage() + 
						"Move was:  " + getPrintableMove(selectedMove));
			}
		}
		

		
		return selectedMove;
	}
	
	private void determineCurrentMoveState()
	{
		boolean butterflyPlaced = game.getBoard().contains(color, HantoPieceType.BUTTERFLY);
		
		// If no moves have been made, we're starting
		if(game.getNumMoves() == 0){
			moveState = MoveState.STARTING;
		}
		// If we haven't placed our butterfly yet, but it's not the fourth turn,
		// we can place anything we want, but not move
		else if(!butterflyPlaced && game.getNumMoves() < NUM_MOVES_PRE_BUTTERFLY) {
			moveState = MoveState.PLACE_ONLY;
		} 
		// If we haven't placed it and it is the fourth turn, we need to place the butterfly
		else if(!butterflyPlaced && game.getNumMoves() >= NUM_MOVES_PRE_BUTTERFLY) {
			moveState = MoveState.MUST_PLACE_BUTTERFLY;
		} 
		// Otherwise, we can place or move as we like
		else {
			moveState = MoveState.PLACE_AND_MOVE;
		}
	}
	
	private List<HantoMoveRecord> findPlacementMoves()
	{
		List<HantoMoveRecord> ret = new ArrayList<HantoMoveRecord>();
		Collection<HantoPieceType> piecesToPlace = new Vector<HantoPieceType>();
		
		switch(moveState)
		{
		case STARTING: // We only have one move option
			ret.add(new HantoMoveRecord(HantoPieceType.BUTTERFLY, 
					null, new HexCoordinate(0, 0)));
			break;
			
		case MUST_PLACE_BUTTERFLY: // We can only place the butterfly, nothing else
			piecesToPlace.add(HantoPieceType.BUTTERFLY);
			break;
		
		case PLACE_ONLY:     // For these, we can place any piece that is available
		case PLACE_AND_MOVE: // Yes, I am abusing fallthrough DON'T HATE ME I KNOW IT'S BAD
			
			for(HantoPieceType p : game.getStartingHand().keySet()) {
				if(hand.getRemainingPiecesToPlay(p) > 0) {
					piecesToPlace.add(p);
				}
			}
			break;
		}
		
		if(moveState != MoveState.STARTING) {
			
			// Theoretically, we can place a piece on any space that is adjacent to 
			// an existing piece--by definition, this preserves the contiguous board condition
			for(HexCoordinate c : game.getBoard().getAllEmptyNeighborCoordinates())
			{
				HantoPlayerColor opponentColor = (color == HantoPlayerColor.RED) ? 
						HantoPlayerColor.BLUE : HantoPlayerColor.RED;
				
				if(game.getNumMoves() <= NUM_MOVES_BEFORE_CARE_ABOUT_COLOR_ADJACENCY ||  
						!game.getBoard().hasNeighborsOfColor(c, opponentColor)) {
					for(HantoPieceType t : piecesToPlace) {
						ret.add(new HantoMoveRecord(t, null, c));
					}
				}
			}
		}
		
		return ret;
	}
	
	private String getPrintableMove(HantoMoveRecord r)
	{
		return ((r.getFrom() == null) ? "PLACE " : " MOVE  ") + 
				((r.getPiece() == null) ? "(null)" : r.getPiece()) +
		((r.getFrom() == null) ? (" at " + r.getTo()) : 
			(r.getFrom() + " -> " + r.getTo()));
	}

}
