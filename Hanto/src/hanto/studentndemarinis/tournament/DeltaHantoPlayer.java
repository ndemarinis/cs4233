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
import hanto.studentndemarinis.HantoFactory;
import hanto.studentndemarinis.common.HantoPiece;
import hanto.studentndemarinis.common.HantoPlayerHand;
import hanto.studentndemarinis.common.HexCoordinate;
import hanto.studentndemarinis.common.InternalHantoGame;
import hanto.studentndemarinis.common.movement.HantoMoveStrategy;
import hanto.studentndemarinis.common.movement.HantoMoveType;
import hanto.studentndemarinis.common.movement.MoveFactory;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Skeleton for eventual DeltaHantoPlayer
 * @author ndemarinis
 * @version Feb 25, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer {

	HantoPlayerColor color; // Our color in this game
	
	// Game used for simulating moves and checking their validity
	private InternalHantoGame game; // Since it's our player, we can use our interface for the game

	private HantoPlayerHand hand; // Available pieces for play, represented in the game itself
	
	// Representation of pieces we have placed, this is so we don't have to search
	// the board for them each time.  
	private Map<HexCoordinate, HantoPiece> placedPieces;
	
	// Strategies for movement of each piece type--just like the ruleset
	// TODO:  can I do this FROM the ruleset?  
	private Map<HantoPieceType, HantoMoveStrategy> moveStrategies;
	
	// This enum describes how we can place pieces:  
	private enum MoveState { 	        STARTING,   // This is the first move, so only one option
				 			MUST_PLACE_BUTTERFLY,   // We MUST place the butterfly on this move
				 				      PLACE_ONLY,   // We can only place pieces
				 				  PLACE_AND_MOVE }; // We can place and move pieces
				 					
	private MoveState moveState;
	private HantoPlayerStrategy strategy;
	
	// Constants for DeltaHanto's rules, which are embedded in the strategy
	// TODO:  find a good way to pull these out of DeltaHanto instead of copypasting them
	private static final int NUM_MOVES_PRE_BUTTERFLY = 7; 
	private static final int NUM_MOVES_BEFORE_CARE_ABOUT_COLOR_ADJACENCY = 1;
	
	/**
	 * Create a new DeltaHantoPlayer, as given
	 * @param color Color for the player, given by tournament
	 * @param isStarting true if this player is making the starting move
	 */
	public DeltaHantoPlayer(HantoPlayerColor color, boolean isStarting) {
		this(color, isStarting, null);
	}
	
	/**
	 * Create a new DeltaHantoPlayer, as given
	 * @param color Color for the player
	 * @param isStarting true if this player is making a starting move
	 * @param strategy PlayerStrategy to use when selecting moves
	 * 
	 * TODO: THIS SHOULD NOT THROW AN EXCEPTION
	 */
	public DeltaHantoPlayer(HantoPlayerColor color, boolean isStarting, 
			HantoPlayerStrategy strategy) {
		this.color = color;
		moveState = isStarting ? MoveState.STARTING : MoveState.PLACE_ONLY;
		
		game = (InternalHantoGame)
				(HantoFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO));
		
		hand = new HantoPlayerHand(game.getStartingHand()); // Setup our available pieces
		placedPieces = new HashMap<HexCoordinate, HantoPiece>(); // Initialize list of pieces we will place
		moveStrategies = setupMoveStrategies();
		
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
		final List<HantoMoveRecord> possibleMoves;
		boolean needToResign = false; // If we need to resign based on a bad opponent move
		
		if(opponentsMove != null) // If this wasn't the starting move 
		{
			try {
				game.makeMove(opponentsMove); // Record the opponent's move on our board
			} catch (HantoException e) {
				needToResign = true;
				
				System.err.println("Received invalid opponent move, resigning.  " + 
						"I don't have time for your nonsense.  " +
						"Exception was:  " + e.getMessage() + 
						"\nMove was:  " + getPrintableMove(opponentsMove));
			}
		}
		
		// Find the current state and set of all possible moves
		determineCurrentMoveState();
		possibleMoves = findValidPlacementMoves();
		possibleMoves.addAll(findValidMovementMoves());
		
		// Pick a random move based on those given strategy
		// If no moves are available, resign.  
		HantoMoveRecord selectedMove = (needToResign) ? null  : 
			           possibleMoves.isEmpty() ? null : 
			        	   strategy.selectMove(game, possibleMoves);

		if(selectedMove != null) {
			try {
				// Run this move on our game, not only so we update our state, 
				// but to verify it against the rules.  
				game.makeMove(selectedMove);
				
				// Update our known pieces
				recordMove(selectedMove);

			} catch(HantoException e) {
				System.err.println("Picked invalid move, resigning.  Cause:  " + 
						e.getMessage() + "\nMove was:  " + getPrintableMove(selectedMove));
				
				// Since we just broke our game, resign.  
				selectedMove = null; 
			}
		}
		
		return selectedMove;
	}
	
	/**
	 * Find the current move state, which governs how we place/move pieces
	 */
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
	
	/**
	 * Find possible moves that involve placing new pieces
	 * @return list of all posible placement move records
	 */
	private List<HantoMoveRecord> findValidPlacementMoves()
	{
		final List<HantoMoveRecord> ret = new ArrayList<HantoMoveRecord>();
		
		// Lists of all available pieces and coordinates we can place
		final Collection<HantoPieceType> piecesToPlace = new Vector<HantoPieceType>();
		final Collection<HexCoordinate>  possiblePlacementCoords = new Vector<HexCoordinate>();
		
		switch(moveState)
		{
		case STARTING: // If we're starting, we can only place at the origin
			addAvailablePiecesFromHand(piecesToPlace); // We can place anything in our hand
			possiblePlacementCoords.add(new HexCoordinate(0, 0));
			break;
			
		case MUST_PLACE_BUTTERFLY: // We can only place the butterfly, nothing else
			piecesToPlace.add(HantoPieceType.BUTTERFLY);
			addValidEmptyCoords(possiblePlacementCoords);
			break;
		
		case PLACE_ONLY:     // For these, we can place any piece that is available
			addAvailablePiecesFromHand(piecesToPlace);
			addValidEmptyCoords(possiblePlacementCoords);
			break;
			
		case PLACE_AND_MOVE: // Same here
			addAvailablePiecesFromHand(piecesToPlace);
			addValidEmptyCoords(possiblePlacementCoords);
			break;
		}
		
		// Cross both of the sets, which will give us all possible placements
		for(HexCoordinate c : possiblePlacementCoords) {
			for(HantoPieceType t : piecesToPlace) {
				ret.add(new HantoMoveRecord(t, null, c));
			}
		}
		
		return ret;
	}
	
	/**
	 * Determine possible moves that involve moving pieces
	 * @param currentMoves Current list of moves
	 */
	private Collection<HantoMoveRecord> findValidMovementMoves()
	{
		final Collection<HantoMoveRecord> possibleMoves = new Vector<HantoMoveRecord>();
		final Collection<HexCoordinate> possibleDestCoords = new Vector<HexCoordinate>(); 
		
		if(moveState == MoveState.PLACE_AND_MOVE) // We can only move when allowed
		{
			addValidEmptyCoords(possibleDestCoords);
			
			// Check each piece for valid moves from its location
			for(HantoPiece p : placedPieces.values()) {
				HantoMoveStrategy strat = moveStrategies.get(p.getType());
				
				// Find if a piece can move to that coordinate
				for(HexCoordinate c : possibleDestCoords) {
					try {
						// If there's a valid move, based on the piece's strategy,
						// from the piece to a coordinate, it's a possible move 
						if(strat.canMoveTo(game.getState(), p.getCoordinate(), c)) {
							possibleMoves.add(new HantoMoveRecord(p.getType(), 
									p.getCoordinate(), c));
						}
					} catch(HantoException e) {
						System.err.println("Found invalid move!  Cause:  " + e.getMessage());
					}
				}
				
			}
		}
		
		return possibleMoves;
	}
	
	/* ********************** HELPER METHODS ***************************/
	
	private void addAvailablePiecesFromHand(Collection<HantoPieceType> pieces)
	{
		for(HantoPieceType p : game.getStartingHand().keySet()) {
			if(hand.getRemainingPiecesToPlay(p) > 0) {
				pieces.add(p);
			}
		}
	}
	
	private void addValidEmptyCoords(Collection<HexCoordinate> coords)
	{
		// Theoretically, we can place a piece on any space that is adjacent to 
		// an existing piece--by definition, this preserves the contiguous board condition
		for(HexCoordinate c : game.getBoard().getAllEmptyNeighborCoordinates())
		{
			HantoPlayerColor opponentColor = (color == HantoPlayerColor.RED) ? 
					HantoPlayerColor.BLUE : HantoPlayerColor.RED;

			if(game.getNumMoves() <= NUM_MOVES_BEFORE_CARE_ABOUT_COLOR_ADJACENCY ||  
					!game.getBoard().hasNeighborsOfColor(c, opponentColor)) {
				coords.add(c);
			}
		}
	}
	
	/**
	 * Setup move strategies based on DeltaHanto
	 * TODO:  pull this from DeltaHanto itself
	 * @return
	 */
	private Map<HantoPieceType, HantoMoveStrategy> setupMoveStrategies()
	{
		final Map<HantoPieceType, HantoMoveStrategy> ret = 
				new HashMap<HantoPieceType, HantoMoveStrategy>();
		
		ret.put(HantoPieceType.BUTTERFLY, 
				MoveFactory.getInstance().getMoveStrategy(HantoMoveType.SLIDE));
		ret.put(HantoPieceType.CRAB, 
				MoveFactory.getInstance().getMoveStrategy(HantoMoveType.SLIDE));
		ret.put(HantoPieceType.SPARROW, 
				MoveFactory.getInstance().getMoveStrategy(HantoMoveType.FLY));
		
		return ret;
	}
	
	/**
	 * Update our data structure of available pieces based on the last
	 * move result.  This data structure allows us to check our pieces
	 * for valid moves without having to search the board for them.  
	 * 
	 * @param latestMove the move we just made
	 * @throws HantoException if something went wrong updating the state
	 */
	private void recordMove(HantoMoveRecord latestMove) throws HantoException
	{
		final HexCoordinate dest = 
				HexCoordinate.extractHexCoordinate(latestMove.getTo());
		final HexCoordinate src = 
				HexCoordinate.extractHexCoordinate(latestMove.getFrom());
		
		// If we placed a piece, add it to our list of available pieces
		if(src == null) { 
			hand.removeFromHand(latestMove.getPiece());
			
			placedPieces.put(dest, 
					new HantoPiece(color, latestMove.getPiece(), dest));
		}
		else // If we're moving a piece, remove it and add at the new coordinate
		{
			placedPieces.remove(src);
			placedPieces.put(dest, 
					new HantoPiece(color, latestMove.getPiece(), dest));
		}
	}
	
	/**
	 * Get a printable representation of a MoveRecord
	 * @param r a record
	 * @return String representation of that record (since MoveRecord has no toString())
	 */
	private String getPrintableMove(HantoMoveRecord r)
	{
		return ((r.getFrom() == null) ? "PLACE " : " MOVE  ") + 
				((r.getPiece() == null) ? "(null)" : r.getPiece()) +
		((r.getFrom() == null) ? (" at " + r.getTo()) : 
			(r.getFrom() + " -> " + r.getTo()));
	}

}
