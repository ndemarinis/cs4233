/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package flyweight;

/**
 * This provides an example Flyweight.  
 * In this case, it's just a wrapper around a string
 * 
 * @author ndemarinis
 * @version Feb 19, 2013
 */
public class InternedString {

	// By definition, the flyweight's intrinsic state should be immuatble
	private final String str;
	
	/**
	 * Create a new flyweight based on a given string
	 * @param str The string to represent
	 */
	public InternedString(String str) 
	{
		this.str = str;
	}
	
	/**
	 * 
	 * @return the flyweight's string
	 */
	public String getString()
	{
		return str;
	}
	
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
	public String formatWithString(String f)
	{
		return str.replace("%s", f);
	}
	
	
	/**
	 * Compare two interned strings
	 * Based on the definition of a flyweight, there is only ever
	 * one flyweight object for each string, meaning we only need to
	 * make sure the objects' references are the same.  
	 * 
	 * (So technically, I shouldn't even need to override this)
	 */
	@Override
	public boolean equals(Object o)
	{
		InternedString other = null;
		
		if(o instanceof InternedString) {
			other = (InternedString)(o);
		}
		
		return o != null && other != null && other == this;
	}
}
