/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package wpi.parking;

import static org.junit.Assert.*;
import org.junit.*;
import wpi.parking.hw.*;

/**
 * Test cases for the TollboothGate class.
 *
 * @author gpollice
 * @version Dec 31, 2012
 */
public class TollboothGateTest
{
	private TestGateController controller;
	
	/**
	 * Create the gate controller that we will use in the tests.
	 */
	@Before
	public void setup()
	{
		controller = new TestGateController();
	}
	/**
	 * Ensure that an initialized tollbooth gate is closed.
	 * @throws WPIPSException
	 */
	@Test
	public void initializedTollboothGateIsClosed() throws WPIPSException
	{
		final TollboothGate gate = new TollboothGate("gate1", controller);
		assertNotNull(gate);
		assertEquals(TollboothGate.TollboothGateState.CLOSED, gate.getState());
	}

	/**
	 * A blank ID should cause an exception.
	 * @throws WPIPSException
	 */
	@Test(expected=WPIPSException.class)
	public void blankIDIsInvalid() throws WPIPSException
	{
		new TollboothGate("", controller);
	}
	
	/**
	 * A null ID should cause an exception.
	 * @throws WPIPSException
	 */
	@Test(expected=WPIPSException.class)
	public void nullIDIsInvalid() throws WPIPSException
	{
		new TollboothGate(null, controller);
	}
	
	/**
	 * Open a closed gate. This should make the gate's state OPEN.
	 * @throws WPIPSException
	 */
	@Test
	public void openAClosedGateShouldGiveAnOpenState() throws WPIPSException
	{
		final TollboothGate gate = new TollboothGate("gate1", controller);
		assertEquals(TollboothGate.TollboothGateState.OPEN, gate.open());
		assertEquals(TollboothGate.TollboothGateState.OPEN, gate.getState());
	}
	
	/**
	 * Close an open gate. This should make the gate's state CLOSED.
	 * @throws WPIPSException
	 */
	@Test
	public void closeAnOpenGateShouldGiveAClosedState() throws WPIPSException
	{
		final TollboothGate gate = new TollboothGate("gate1", controller);
		gate.open();
		assertEquals(TollboothGate.TollboothGateState.CLOSED, gate.close());
		assertEquals(TollboothGate.TollboothGateState.CLOSED, gate.getState());
	}
	
	/**
	 * If there is an error in the gate controller hardware, then the gate should be
	 * in an UNKNOWN state after closing.
	 */
	@Test
	public void errorOnClosingCausesExceptionAndUnknownState()
	{
		controller.setCloseResults(new boolean[] {false});
		TollboothGate gate = null;
		try {
			gate = new TollboothGate("gate1", controller);
			gate.close();		// in case close is not called on initialization
			fail("Expected gate controller exception");
		} catch (WPIPSException e) {
			assertEquals(TollboothGate.TollboothGateState.UNKNOWN, gate.getState());
		}
	}
	
	/**
	 * If there is an error in the gate controller hardware, then the gate should be
	 * in an UNKNOWN state after opening.
	 */
	@Test
	public void errorOnOpeningCausesExceptionAndUnknownState()
	{
		controller.setOpenResults(new boolean[] {false});
		TollboothGate gate = null;
		try {
			gate = new TollboothGate("gate1", controller);
			gate.open();
			fail("Expected gate controller exception");
		} catch (WPIPSException e) {
			assertEquals(TollboothGate.TollboothGateState.UNKNOWN, gate.getState());
		}
	}
	
	/**
	 * When I make a new gate, it should not be deactivated
	 * @throws WPIPSException
	 */
	
	@Test
	public void initializedGateIsActivated() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		assertTrue(TollboothGate.TollboothGateState.DEACTIVATED != gate.getState());
	}
	
	@Test
	public void deactivatingAGateShouldGiveADeactivatedState() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		
		assertEquals(gate.deactivate(), TollboothGate.TollboothGateState.DEACTIVATED);
		assertEquals(gate.getState(), TollboothGate.TollboothGateState.DEACTIVATED);
	}
	
	@Test
	public void activatingADectivatedGateShouldBeInClosedState() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		
		gate.deactivate();
		
		assertEquals(gate.activate(), TollboothGate.TollboothGateState.CLOSED);
		assertEquals(gate.getState(), TollboothGate.TollboothGateState.CLOSED);
	}
	
	@Test(expected=WPIPSException.class)
	public void activatingAnActivatedGateShouldThrowError() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		
		gate.activate();
	}
	
	@Test(expected=WPIPSException.class)
	public void deactivatingADeactivatedGateShouldThrowError() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		gate.deactivate();
		
		gate.deactivate();
	}
	
	@Test(expected=WPIPSException.class)
	public void ClosingADeactivatedGateThrowsError() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		gate.deactivate();
		
		gate.close();
	}
	
	@Test(expected=WPIPSException.class)
	public void OpeningADeactivatedGateThrowsError() throws WPIPSException
	{
		TollboothGate gate = new TollboothGate("gate", controller);
		gate.deactivate();
		
		gate.open();
	}
}
