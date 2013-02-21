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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ndemarinis
 * @version Feb 19, 2013
 */
public class InternedStringFactory {

	private static InternedStringFactory instance = null;
	
	private Map<String, InternedString> strings;
	
	/**
	 * Creates interned strings
	 */
	private InternedStringFactory() {
		strings = new HashMap<String, InternedString>();
	}
	
	public static InternedStringFactory getInstance()
	{	
		if(instance == null)
			instance = new InternedStringFactory();
		
		return instance;
	}
	
	public static void resetInstance()
	{
		instance = null;
		getInstance();
	}
	
	public InternedString makeInternedString(String str)
	{
		if(strings.get(str) == null) {
			strings.put(str, new InternedString(str));
		}
		
		return strings.get(str);
	}
	
	

}
