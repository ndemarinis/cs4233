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
import static flyweight.FlyweightTestStrings.*;

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
	
	final String hello = "Hello world!";
	final String helloAgain = "Hello world!";
	final String notHello = "Goodbye, cruel world!";
	final String helloFormat = "Hello %s!";
	

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
		InternedString helloInterned = (InternedString)stringFactory.makeInternedString(hello);
		
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
	public void canCompareTwoDifferentInternedStringsSameContent()
	{
		// Make a string, then make another one (which should just return the previous one)
		InternedString helloInterned = 
				(InternedString)stringFactory.makeInternedString(hello);
		InternedString helloAgainInterned = 
				(InternedString)stringFactory.makeInternedString(helloAgain);
		
		// These two references should point to the same flyweight object
		assertTrue(helloInterned == helloAgainInterned);
		
		// Just to be sure, equals() should work, too
		assertTrue(helloInterned.equals(helloAgainInterned));

	}
	
	@Test
	public void canCompareTwoDifferentInternedStringsDiffContent()
	{
		InternedString helloInterned = (InternedString)stringFactory.makeInternedString(hello);
		InternedString otherString = (InternedString)stringFactory.makeInternedString(notHello);
		
		// These two references should not point to the same object
		assertFalse(helloInterned == otherString);
		
		// Just to be sure, equals() should work, too
		assertFalse(helloInterned.equals(otherString));
	}

	
	@Test
	public void compareTwoReallyLongStringsWhenInterned()
	{
		InternedString loremIpsumInterned = 
				(InternedString)stringFactory.makeInternedString(loremIpsum);
		InternedString loremIpsumModifiedInterned = 
				(InternedString)stringFactory.makeInternedString(loremIpsumModified);
		
		assertFalse(loremIpsumInterned == loremIpsumModifiedInterned);
		assertFalse(loremIpsumInterned.equals(loremIpsumModifiedInterned));
	}
	
	@Test
	public void compareTwoReallyLongStringsWhenNotInterned()
	{
		// Unless Java optimizes this (and I'm sure it does), this should resort
		// to a byte-by-byte compare, which obviously takes longer than our interened method.  
		assertFalse(loremIpsum.equals(loremIpsumModified));
	}
	
	@Test
	public void passContextToFlyweightWithFormattedString()
	{
		// Intern a string with a format parameter
		InternedString helloFormatInterned = 
				(InternedString)stringFactory.makeInternedString(helloFormat);
		
		// Give it an external context (the string "grader") and then check the output
		assertTrue(helloFormatInterned.formatWithString("grader").equals("Hello grader!"));
	}
}
