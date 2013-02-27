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

import hanto.studentndemarinis.common.HantoGameState;

/**
 * This class creates moves for HantoPieces
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 */
public class MoveFactory {

	private static MoveFactory instance = null;
	
	private Map<HantoMoveType, HantoMoveStrategy> strategies;
	
	/**
	 * Create a MoveFactory.  Since this method is a singleton, 
	 * this is a private constructor.  
	 */
	private MoveFactory() {
		strategies = new HashMap<HantoMoveType, HantoMoveStrategy>();
	}

	public static MoveFactory getInstance()
	{
		if(instance == null){
			instance = new MoveFactory();
		}
		
		return instance;
	}
	
	public HantoMoveStrategy makeMoveStrategy(HantoGameState state, 
			HantoMoveType type, int distance)
	{
		HantoMoveStrategy ret = strategies.get(type);
		
		if(ret == null) // If we don't have a strategy for that yet, make it
		{
			switch(type)
			{
			case WALK:
				ret =  new WalkStrategy(state, distance);
				break;

			case SLIDE:
				ret = new SlideStrategy(state, distance);
				break;

			case FLY:
				ret = new FlyStrategy(state, distance);
				break;
			}
			
			// Add it to the map for later use
			strategies.put(type, ret);
		}
		
		return ret;
	}
}
