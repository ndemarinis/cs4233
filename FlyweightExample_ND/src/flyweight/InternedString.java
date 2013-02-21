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
 * @author ndemarinis
 * @version Feb 19, 2013
 */
public class InternedString {

	private String str;
	
	/**
	 * Class representing an interned string, or our flyweight
	 */
	public InternedString(String str) 
	{
		this.str = str;
	}
	
	
	public String getString()
	{
		return str;
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
