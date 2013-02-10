/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.delta;

import hanto.common.HantoException;
import hanto.studentndemarinis.common.AbstractHantoRuleSet;
import hanto.studentndemarinis.common.HantoGameState;
import hanto.studentndemarinis.common.HantoRuleSet;
import hanto.util.MoveResult;

/**
 * Ruleset for Delta Hanto
 * @author ndemarinis
 *
 */
public class DeltaHantoRuleset extends AbstractHantoRuleSet implements
		HantoRuleSet {

	/**
	 * Create a ruleset for Delta Hanto
	 */
	public DeltaHantoRuleset(HantoGameState state) {
		this.state = state;
	}

	@Override
	public MoveResult evaluateMoveResult() throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}

}
