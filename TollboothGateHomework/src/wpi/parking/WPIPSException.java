/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package wpi.parking;

/**
 * Description
 *
 * @author gpollice
 * @version Dec 31, 2012
 */
public class WPIPSException extends Exception
{

	/**
	 * Default constructor
	 * @see java.lang.Exception#Exception()
	 */
	public WPIPSException()
	{
		// Intentionally left empty
	}

	/**
	 * Constructor with a message.
	 * @see java.lang.Exception#Exception(String)
	 */
	public WPIPSException(String message)
	{
		super(message);
	}

	/**
	 * Constructor with a nested exception as the cause
	 * @see java.lang.Exception#Exception(Throwable)
	 */
	public WPIPSException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Constructor with a message and cause.
	 * @see java.lang.Exception#Exception(String, Throwable)
	 */
	public WPIPSException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
