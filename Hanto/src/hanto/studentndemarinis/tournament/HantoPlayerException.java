/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package hanto.studentndemarinis.tournament;

/**
 * This exception class is used for player-related errors
 * that shouldn't be officially thrown in the implementation, but
 * should be evaluated during testing.  
 * 
 * Therefore, this is a RuntimeException.  We don't have to catch it, 
 * but we need to know when it happens.  
 * 
 * (This is helpful for ensuring I write my tests properly)
 * 
 * @author ndemarinis
 * @version Feb 26, 2013
 *
 */
public class HantoPlayerException extends RuntimeException {


	/**
	 * An exception with a message
	 * @param message message about the exception's cause
	 */
	public HantoPlayerException(String message) {
		super(message);
	}

	/**
	 * Exception with a throwable
	 * @param cause Throwable about the cause
	 */
	public HantoPlayerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Exception with message and information about cause
	 * @param message information about cause
	 * @param cause the cause info
	 */
	public HantoPlayerException(String message, Throwable cause) {
		super(message, cause);
	}

}
