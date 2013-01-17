/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package wpi.parking;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import wpi.parking.hw.TollboothGateController;

/**
 * The TollboothGate class encapsulates all control functions for a single
 * gate at a tollbooth in the parking management system.
 *
 * @author gpollice (Initial implementation)
 * @author ndemarinis (Extensions for US4 and US5)
 * @version January 16, 2013
 */
public class TollboothGate
{
	public enum TollboothGateState { UNKNOWN, OPEN, CLOSED, DEACTIVATED };
	private final TollboothGateController controller;
	private TollboothGateState state;
	private long delayTimeMsec = 0; // Time to delay closing the gate in seconds
	
	// Timer object for implementing the delay time (could also only 
	// instantiate this when we know we have a delay time)
	private final Timer timer = new Timer();
	
	// Task run when the delayed close timer fires, closing the gate
	private TimerTask delayTask;
	
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
	 * Initialize a tollbooth gate with a delayed closing
	 * 
	 * @param id The tollbooth gate's ID
	 * @param controller The hardware tollbooth gate controller
	 * @param delayTimeSec Time in seconds the gate should remain open before closing automatically
	 * @throws WPIPSException If any errors occur during initialization
	 */
	public TollboothGate(String id, TollboothGateController controller, 
			int delayTimeSec) throws WPIPSException
	{
		this(id, controller);
		
		delayTimeMsec = TimeUnit.SECONDS.toMillis(delayTimeSec);

	}
	
	/**
	 * Open the gate.
	 * @return the gate's state. It will be OPEN or UNKNOWN if there was a problem.
	 * @throws WPIPSException if an error occurred and the gate could not open
	 */
	public TollboothGateState open() throws WPIPSException
	{
		if(state == TollboothGateState.DEACTIVATED) {
			throw new WPIPSException("Cannot open gate; gate is deactivated!");
		}
		
		try {
			controller.open();
			state = TollboothGateState.OPEN;
				
			if(delayTimeMsec > 0) // If we have a delay time, start off the timer
			{
				// If a task is currently running, cancel it.  
				if(delayTask != null) {
					delayTask.cancel();
				}
				
				// Make a new task for each timer run (this lets us cancel/restart them)
				delayTask = new TimerTask() 
				{
					public void run()
					{
						state = TollboothGateState.CLOSED;
					}
				};	
				
				timer.schedule(delayTask, delayTimeMsec);
			}
			
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
		if(state == TollboothGateState.DEACTIVATED) {
			throw new WPIPSException("Cannot close gate; gate is deactivated!");
		}
		
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
	
	/**
	 * Deactivate a gate.
	 * @return The gate's state
	 * @throws WPIPSException if gate is already deactivated
	 */
	public TollboothGateState deactivate() throws WPIPSException
	{
		if(state == TollboothGateState.DEACTIVATED) {
			throw new WPIPSException("Can't deactivate a deactivated gate!");
		}
		
		// If we've scheduled a task, cancel it.  
		if(delayTask != null)
		{
			delayTask.cancel();
		}
		
		state = TollboothGateState.DEACTIVATED;
		
		return state;
	}
	
	/**
	 * Activate a deactivated gate.  
	 * @return The gate's state, should be CLOSED unless an error occurs
	 * @throws WPIPSException if gate is already active
	 */
	public TollboothGateState activate() throws WPIPSException
	{
		if(state != TollboothGateState.DEACTIVATED) {
			throw new WPIPSException("Gate is already active!");
		}
		
		// After activating the gate, close it.  Since you can, in theory, 
		// deactivate an open gate, it makes sense to tell the hardware
		// to close it when reactivating it to ensure that our state
		// syncs up with the hardware.  So until we close it with the 
		// controller, the state is technically unknown.  
		
		state = TollboothGateState.UNKNOWN; 
		return this.close();
	}
}
