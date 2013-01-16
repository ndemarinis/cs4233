/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package wpi.parking;

import wpi.parking.hw.TollboothGateController;

/**
 * The TollboothGate class encapsulates all control functions for a single
 * gate at a tollbooth in the parking management system.
 *
 * @author gpollice
 * @version Dec 31, 2012
 */
public class TollboothGate
{
	public enum TollboothGateState { UNKNOWN, OPEN, CLOSED };
	private final TollboothGateController controller;
	private TollboothGateState state;
	
	/**
	 * Default constructor.
	 *
	 * @param id the tollbooth gate's ID.
	 * @param controller the hardware tollbooth gate controller for this gate
	 * @throws WPIPSException if there are any errors that occur during the initialization
	 */
	public TollboothGate(String id, TollboothGateController controller) throws WPIPSException
	{
		if (id == null || id.equals("")) {
			throw new WPIPSException("Tollbooth gate ID cannot be blank");
		}
		this.controller = controller;
		state = TollboothGateState.CLOSED;
	}
	
	/**
	 * Open the gate.
	 * @return the gate's state. It will be OPEN or UNKNOWN if there was a problem.
	 * @throws WPIPSException if an error occurred and the gate could not open
	 */
	public TollboothGateState open() throws WPIPSException
	{
		try {
			controller.open();
			state = TollboothGateState.OPEN;
		} catch (WPIPSException e) {
			state = TollboothGateState.UNKNOWN;
			throw e;
		}
		return state;
	}
	
	/**
	 * Close the gate.
	 * @return the gate's state. It will be CLOSED or UNKNOWN if there was a problem.
	 * @throws WPIPSException if an error occurred and the gate could not close
	 */
	public TollboothGateState close() throws WPIPSException
	{
		try {
			controller.close();
			state = TollboothGateState.CLOSED;
		} catch (WPIPSException e) {
			state = TollboothGateState.UNKNOWN;
			throw e;
		}
		return state;
	}
	
	/**
	 * Get the tollbooth gate state.
	 */
	public TollboothGateState getState()
	{
		return state;
	}
	
}
