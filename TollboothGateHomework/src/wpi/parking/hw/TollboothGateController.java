/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/


package wpi.parking.hw;

import wpi.parking.WPIPSException;

/**
 * This interface describes the behavior that is required by the WPI Parking System
 * of any tollbooth gate controller.
 *
 * @author gpollice
 * @version Jan 9, 2013
 */
public interface TollboothGateController
{
	/**
	 * Open the gate using the controller.
	 * @throws WPIPSException if the gate does not open
	 */
	void open() throws WPIPSException;
	
	/**
	 * Close the gate using the controller
	 * @throws WPIPSException if the gate does not close
	 */
	void close() throws WPIPSException;
}
