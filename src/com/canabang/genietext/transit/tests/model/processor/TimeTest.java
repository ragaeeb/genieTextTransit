/*
 * @(#)TimeTest.java	1.0	05/31/09
 *
 * Copyright 2009 Canabang Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms is not permitted without the written
 * consent from Canabang Inc.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.canabang.genietext.transit.tests.model.processor;

import static org.junit.Assert.assertEquals;
import com.canabang.genietext.core.model.structs.Time;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods of the Time class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TimeTest
{
	/** An instance of the class to test. */
	private Time t1;

	/** An instance of the class to test. */
	private Time t2;


	/**
	 * Sets up the environment for testing.
	 * @throws java.lang.Exception If there are any problems setting up the environment.
	 */
	@Before
	public void setUp() throws Exception
	{
		t1 = new Time("1033");
		t2 = new Time("2311");
	}

	/**
	 * Test method for {@link com.canabang.genietext.core.model.structs.Time#Time(java.lang.String)}.
	 */
	@Test
	public void testTimeString()
	{
		assertEquals( 1033, t1.getUnformattedIntegerValue() );
		assertEquals( 2311, t2.getUnformattedIntegerValue() );
	}

	/**
	 * Test method for {@link com.canabang.genietext.core.model.structs.Time#compareTo(com.canabang.genietext.core.model.structs.Time)}.
	 */
	@Test
	public void testCompareTo()
	{
		assertEquals(true, t1.compareTo(t2) < 0 );
		assertEquals(true, t2.compareTo(t1) > 0 );
	}

	/**
	 * Test method for {@link com.canabang.genietext.core.model.structs.Time#getUnformattedValue()}.
	 */
	@Test
	public void testGetUnformattedValue()
	{
		assertEquals( "1033", t1.getUnformattedValue() );
		assertEquals( "2311", t2.getUnformattedValue() );
	}

	/**
	 * Test method for {@link com.canabang.genietext.core.model.structs.Time#toString()}.
	 */
	@Test
	public void testToString()
	{
		assertEquals( "10:33 AM", t1.toString() );
		assertEquals( "11:11 PM", t2.toString() );

		t1 = new Time(1003);
		assertEquals( "10:03 AM", t1.toString() );
		Time.setMilitaryFormat();
		assertEquals( "10:03", t1.toString() );
	}
}