/*
 * @(#)ReverseStopLookupRequestTest.java	1.0	05/31/09
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
import com.canabang.genietext.core.model.processor.requests.Request;
import com.canabang.genietext.transit.model.processor.requests.ReverseStopLookupRequest;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the methods of the ReverseStopLookup class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ReverseStopLookupRequestTest
{
	/** An instance of the class to test. */
	private Request r1;

	/** An instance of the class to test. */
	private Request r2;


	/**
	 * Sets up the environment for testing.
	 * @throws java.lang.Exception If there are any problems setting up the environment.
	 */
	@Before
	public void setUp() throws Exception
	{
		r1 = new ReverseStopLookupRequest(8894);
		r2 = new ReverseStopLookupRequest(1234);
	}


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.requests.ReverseStopLookupRequest#process()}.
	 */
	@Test
	public void testProcess()
	{
		assertEquals( "Station 8894: 121 St. Laurent, 825 Hurdman, 114 Hurdman, 95 Orleans, 125 Hurdman, 191 Hurdman", r1.process() );
		assertEquals( "Station 1234: does not exist in database.", r2.process() );
	}
}