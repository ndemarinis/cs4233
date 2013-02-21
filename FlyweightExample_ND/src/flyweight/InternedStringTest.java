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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Some simple test cases to demonstrate the functionality
 * of my Interned String implementation.  
 * 
 * @author ndemarinis
 * @version Feb 19, 2013
 */
public class InternedStringTest {

	InternedStringFactory stringFactory;
	
	String hello = "Hello world!";
	String helloAgain = "Hello world!";
	String notHello = "Goodbye, cruel world!";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		stringFactory = InternedStringFactory.getInstance();
	}
	
	@After
	public void tearDown() throws Exception
	{
		InternedStringFactory.resetInstance();
	}

	@Test
	public void canInitializeISFactory() {
		assertNotNull(stringFactory);
	}
	
	@Test
	public void canAddSomeString() {
		InternedString helloInterned = stringFactory.makeInternedString(hello);
		
		assertEquals(helloInterned.getString(), hello);
	}
	
	@Test
	public void canCompareTwoStringsAsSameObject()
	{
		String helloAgainFromSameObject = hello;
		
		assertTrue(hello == helloAgainFromSameObject);
	}
	
	@Test 
	public void cantCompreTwoDifferentStringObjectsWithSameContent()
	{
		assertFalse(hello != helloAgain);
	}
	
	@Test
	public void canCompareTwoDifferentInternedStrings()
	{
		// Make a string, then make another one (which should just return the previous one)
		InternedString helloInterned = stringFactory.makeInternedString(hello);
		InternedString helloAgainInterned = stringFactory.makeInternedString(helloAgain);
		
		// These two references should point to the same flyweight object
		assertTrue(helloInterned == helloAgainInterned);
	}

}
