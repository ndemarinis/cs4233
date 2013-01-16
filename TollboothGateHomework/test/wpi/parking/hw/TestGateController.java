/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package wpi.parking.hw;

import wpi.parking.*;

/**
 * This is the gate controller that is used for unit tests.
 *
 * @author gpollice
 * @version Jan 9, 2013
 */
public class TestGateController implements TollboothGateController
{
	private boolean[] closeResults;
	private boolean[] openResults;
	private int openResultsIndex;
	private int closeResultsIndex;

	/**
	 * Default constructor, the open and close operations always succeed.
	 *
	 */
	public TestGateController()
	{
		this(null, null);
	}
	
	/**
	 * The constructor takes two arrays of booleans. The first array determines whether
	 * an open operation succeeds or fails in the hardware. The second array does the
	 * same for the close operation. If these are null, then the respective operations
	 * always succeed.
	 *
	 * @param openResults
	 * @param closeResults
	 */
	public TestGateController(boolean[] openResults, boolean[] closeResults)
	{
		this.openResults = openResults;
		this.closeResults = closeResults;
		openResultsIndex = closeResultsIndex = 0;
	}

	/*
	 * @see wpi.parking.hw.TollboothGateController#open()
	 */
	@Override
	public void open() throws WPIPSException
	{
		if (openResults != null && !openResults[openResultsIndex++]) {
			throw new WPIPSException("TollboothGateController failed to open properly");
		}
	}

	/*
	 * @see wpi.parking.hw.TollboothGateController#close()
	 */
	@Override
	public void close() throws WPIPSException
	{
		if (closeResults != null && !closeResults[closeResultsIndex++]) {
			throw new WPIPSException("TollboothGateController failed to close properly");
		}
	}
	
	/**
	 * Reprogram the way the test controller behaves on open.
	 *
	 * @param states the new set of results for the open operation.
	 */
	public void setOpenResults(boolean[] states)
	{
		openResults = states;
		openResultsIndex = 0;
	}
	
	/**
	 * Reprogram the way the test controller behaves on close.
	 *
	 * @param states the new set of results for the close operation.
	 */
	public void setCloseResults(boolean[] states)
	{
		closeResults = states;
		closeResultsIndex = 0;
	}

}
