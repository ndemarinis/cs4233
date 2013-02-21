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

import java.util.HashMap;
import java.util.Map;

/**
 * Factory to manage and create flyweights
 * @author ndemarinis
 * @version Feb 19, 2013
 */
public class InternedStringFactory {

	private static InternedStringFactory instance = null;
	
	// This is our flyweight pool, indexed by the original string
	private final Map<String, InternedString> strings;
	
	/**
	 * Creates interned strings
	 */
	private InternedStringFactory() {
		strings = new HashMap<String, InternedString>();
	}
	
	/**
	 * @return instance of this Flyweight factory 
	 */
	public static InternedStringFactory getInstance()
	{	
		if(instance == null) {
			instance = new InternedStringFactory();
		}	
		
		return instance;
	}
	
	/**
	 * Completely clears the instance to start over with a new blank pool
	 */
	public static void resetInstance()
	{
		instance = new InternedStringFactory();
	}
	
	/**
	 * Creates an interned string based on the given string
	 * Two strings with the same content map to the same object
	 * If it is already in the pool, the existing one will be returned
	 * @param str String to be created
	 * @return The interned string object
	 */
	public InternedString makeInternedString(String str)
	{
		if(strings.get(str) == null) {
			strings.put(str, new InternedString(str));
		}
		
		return strings.get(str);
	}
	
	

}
