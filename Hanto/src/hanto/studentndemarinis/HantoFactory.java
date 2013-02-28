/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentndemarinis.alpha.AlphaHantoGame;
import hanto.studentndemarinis.delta.DeltaHantoGame;
import hanto.studentndemarinis.gamma.GammaHantoGame;
import hanto.util.HantoGameID;

/**
 * Factory for creating Hanto Games.  
 * Currently supports Alpha and Gamma Hanto.  
 * 
 * @author ndemarinis
 * @version Feb 9 2013
 */
public class HantoFactory {

	private static HantoFactory instance = null;
	
	/**
	 * Factory for Hanto Games
	 * This constructor is private so this is a singleton
	 */
	private HantoFactory() {
	}
	
	/**
	 * Get the instance of this HantoFactory
	 * If it does not exist, create it
	 * @return the HantoFactory instance
	 */
	public static HantoFactory getInstance() {
		if(instance == null) {
			instance = new HantoFactory();
		}
		
		return instance;
	}
	
	
	/**
	 * Create an instance of a HantoGame based on the
	 * give game type.  Only Alpha, and Gamma Hanto
	 * are currently supported.  
	 * 
	 * @param gameID Type of HantoGame to create
	 * @return Instance of the specified Hanto Game, null
	 * if game could not be made
	 */
	public HantoGame makeHantoGame(HantoGameID gameID)
	{
		HantoGame ret = null;
		try {
			switch(gameID) {
			case ALPHA_HANTO:
				ret = new AlphaHantoGame();
				break;

			case GAMMA_HANTO:
				ret = new GammaHantoGame();
				break;

			case DELTA_HANTO:
				ret = new DeltaHantoGame();
				break;

			default:
				// We could throw an exception here (and I was); however, 
				// this causes issues with DeltaHantoPlayer, which needs to make a 
				// HantoGame but can't throw exceptions in the constructor.  
				// Since we have absolutely no requirement for throwing an exception,
				// I removed it.  
				ret = null; 
			}
		} catch(HantoException e) {
			ret = null;
		}
		
		return ret;
	}

}
