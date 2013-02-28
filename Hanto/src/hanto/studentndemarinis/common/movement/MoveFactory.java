/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.common.movement;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates moves for HantoPieces
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class MoveFactory {

	private static MoveFactory instance = null;
	
	private final Map<HantoMoveType, HantoMoveStrategy> strategies;
	
	/**
	 * Create a MoveFactory.  Since this method is a singleton, 
	 * this is a private constructor.  
	 */
	private MoveFactory() {
		strategies = new HashMap<HantoMoveType, HantoMoveStrategy>();
	}

	/**
	 * Get the instance of this move factory.  
	 * This factory method ensures consistent access to the same
	 * factory for creating moves, maintaining the singleton pattern.  
	 * @return instance of the move factory
	 */
	public static MoveFactory getInstance()
	{
		if(instance == null){
			instance = new MoveFactory();
		}
		
		return instance;
	}
	
	/**
	 * Get a move strategy matching a particular type
	 * 
	 * This COULD allow movement based on a particular distance,
	 * but we don't actually need it for our game
	 * 
	 * @param type Type of move strategy, defined by HantoMoveType
	 * 
	 * @return The given move strategy.  If one does not exist in the factory already,
	 * one will be created
	 */
	public HantoMoveStrategy getMoveStrategy(HantoMoveType type)
	{
		HantoMoveStrategy ret = strategies.get(type);
		
		if(ret == null) // If we don't have a strategy for that yet, make it
		{
			switch(type)
			{
			case NO_MOVE:
				ret = new NoMoveStrategy();
				break;
				
			case WALK:
				ret =  new WalkStrategy();
				break;

			case SLIDE:
				ret = new SlideStrategy();
				break;

			case FLY:
				ret = new FlyStrategy();
				break;
			}
			
			// Add it to the map for later use
			strategies.put(type, ret);
		}
		
		return ret;
	}
}
