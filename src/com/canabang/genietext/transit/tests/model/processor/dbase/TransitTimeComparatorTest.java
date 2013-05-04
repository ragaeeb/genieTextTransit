/*
 * @(#)TransitTimeComparatorTest.java	1.0	05/31/09
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
package com.canabang.genietext.transit.tests.model.processor.dbase;

import static org.junit.Assert.assertEquals;
import com.canabang.genietext.transit.model.processor.dbase.TransitTime;
import com.canabang.genietext.transit.model.processor.dbase.TransitTimeComparator;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the methods of the TransitTimeComparator class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitTimeComparatorTest
{
	/** An instance of the class to test. */
	private TreeSet<TransitTime> times;


	/**
	 * Sets up the environment for testing.
	 * @throws java.lang.Exception If there are any problems setting up the environment.
	 */
	@Before
	public void setUp() throws Exception
	{
		this.times =  new TreeSet<TransitTime>( TransitTimeComparator.getInstance() );
		times.add( new TransitTime(1000) );
		times.add( new TransitTime(1200) );
		times.add( new TransitTime(1300) );
	}


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.dbase.TransitTimeComparator#compare(com.canabang.genietext.transit.model.processor.dbase.TransitTime, com.canabang.genietext.transit.model.processor.dbase.TransitTime)}.
	 */
	@Test
	public void testCompare()
	{
		assertEquals( 2, times.tailSet( new TransitTime(1100) ).size() );
		assertEquals( 3, times.tailSet( new TransitTime(900) ).size() );
	}
}