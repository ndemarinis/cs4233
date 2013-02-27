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
 *
 */
public class HantoPlayerException extends RuntimeException {

	/**
	 * 
	 */
	public HantoPlayerException() {
	}

	/**
	 * @param arg0
	 */
	public HantoPlayerException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public HantoPlayerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public HantoPlayerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
