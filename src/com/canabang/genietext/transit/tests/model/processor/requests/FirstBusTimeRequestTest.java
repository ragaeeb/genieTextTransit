/*
 * @(#)FirstBusTimeRequestTest.java	1.0	05/31/09
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
import com.canabang.genietext.transit.model.processor.requests.FirstBusTimeRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;


/**
 * Test the FirstBusTimeRequest class's methods.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class FirstBusTimeRequestTest
{
	/** The instance of the class to test. */
	private FirstBusTimeRequest r1;


	/**
	 * Sets up the environment for testing.
	 * @throws java.lang.Exception If there are any problems setting up the environment.
	 */
	@Before
	public void setUp() throws Exception
	{
		Calendar c = new GregorianCalendar(Locale.CANADA);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 15);
		c.set(Calendar.MINUTE, 15);

		this.r1 = new FirstBusTimeRequest(121, 8894, c);
	}


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.requests.FirstBusTimeRequest#process()}.
	 */
	@Test
	public void testProcess()
	{
		assertEquals( "5:36 AM", r1.process() );
	}
}