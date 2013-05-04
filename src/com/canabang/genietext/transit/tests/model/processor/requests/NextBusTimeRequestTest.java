/*
 * @(#)NextBusTimeRequestTest.java	1.0	05/31/09
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
package com.canabang.genietext.transit.tests.model.processor.requests;

import static org.junit.Assert.assertEquals;
import com.canabang.genietext.transit.model.processor.requests.NextBusTimeRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the methods of the NextBusTimeRequest class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class NextBusTimeRequestTest
{
	/** An instance of the class to test. */
	private NextBusTimeRequest r1;

	/** An instance of the class to test. */
	private NextBusTimeRequest r2;

	/** An instance of the class to test. */
	private NextBusTimeRequest r3;


	/**
	 * Sets up the environment for testing.
	 * @throws java.lang.Exception If there are any problems setting up the environment.
	 */
	@Before
	public void setUp() throws Exception
	{
		Calendar c = new GregorianCalendar(Locale.CANADA);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		c.set(Calendar.HOUR_OF_DAY, 15);
		c.set(Calendar.MINUTE, 15);
		this.r1 = new NextBusTimeRequest(121, 8894, c);

		Calendar c2 = new GregorianCalendar(Locale.CANADA);
		c2.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		c2.set(Calendar.HOUR_OF_DAY, 22);
		c2.set(Calendar.MINUTE, 45);		
		this.r2 = new NextBusTimeRequest(121, 8894, c2);

		Calendar c3 = new GregorianCalendar(Locale.CANADA);
		c3.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c3.set(Calendar.HOUR_OF_DAY, 22);
		c3.set(Calendar.MINUTE, 45);		
		this.r3 = new NextBusTimeRequest(121, 8894, c3);
	}


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.requests.NextBusTimeRequest#process()}.
	 */
	@Test
	public void testProcess()
	{
		assertEquals( "3:31 PM, 4:01 PM, 4:31 PM", r1.process() );
		assertEquals( "5:55 AM, 6:06 AM, 7:07 AM", r2.process() );
		assertEquals( "5:36 AM, 6:06 AM, 6:26 AM", r3.process() );
	}
}