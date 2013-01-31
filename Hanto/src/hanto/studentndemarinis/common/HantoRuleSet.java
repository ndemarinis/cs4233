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
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

/**
 * This class specifies the sets of rules that apply to a
 * Hanto Game.  
 * 
 * @author ndemarinis
 * @vresion Jan 31, 2013
 */
public interface HantoRuleSet {

	public void doPreMoveChecks(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to) 
			throws HantoException;
	
	public void doPostMoveChecks(HantoCoordinate dest) throws HantoException;
	
	public MoveResult evaluateWinConditions() throws HantoException;
}
