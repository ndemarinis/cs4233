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
import hanto.common.HantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * AlphaHanto - Initial Hanto implementation
 * @author ndemarinis
 *
 */
public class AlphaHanto implements HantoGame {

	private int numMoves;
	
	
	public AlphaHanto() throws HantoException {
		this.initialize(HantoPlayerColor.BLUE);
	}
	
	
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException {
		numMoves = 0;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		
		// Starting butterfly should be at origin, or else.
		if(numMoves == 0 && (to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("Illegal move:  starting butterfly must be at origin!");
		}
		
		if(numMoves++ == 0) {
			return MoveResult.OK;
		} else {
			return MoveResult.DRAW;
		}
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
