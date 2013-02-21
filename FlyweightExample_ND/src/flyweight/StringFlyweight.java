package flyweight;
/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * Generic interface for a flyweight keyed with a string
 * @author ndemarinis
 * @vresion Feb 20, 2013
 *
 */
public interface StringFlyweight {

	
	/**
	 * @return the flyweight's string
	 */
	public String getString();	
	
	/**
	 * Simple example for string formatting/context passing
	 * Outputs the given string replacing the format parameter %s
	 * with given string f.  
	 * If %s is not found in the stored string, the original string is returned
	 * 
	 * This functionality has been kept incredibly simple by design--the only
	 * point of this is to demonstrate passing of an external context to a flyweight.  
	 * 
	 * @param f Format string
	 * @return Stored string with format string in place of %s
	 */
	public String formatWithString(String f);
}
