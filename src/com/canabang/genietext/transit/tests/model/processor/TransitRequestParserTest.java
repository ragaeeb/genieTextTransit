/*
 * @(#)TransitRequestParserTest.java	1.0	05/31/09
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
import com.canabang.genietext.core.model.processor.requests.SubscribeRequest;
import com.canabang.genietext.core.model.processor.requests.UnsubscribeRequest;
import com.canabang.genietext.core.model.server.MessageBreaker;
import com.canabang.genietext.core.model.structs.AddressSet;
import com.canabang.genietext.transit.model.processor.TransitRequestParser;
import com.canabang.genietext.transit.model.processor.requests.FirstBusTimeRequest;
import com.canabang.genietext.transit.model.processor.requests.FullScheduleRequest;
import com.canabang.genietext.transit.model.processor.requests.LastBusTimeRequest;
import com.canabang.genietext.transit.model.processor.requests.NextBusTimeRequest;
import com.canabang.genietext.transit.model.processor.requests.ReverseLocationLookupRequest;
import com.canabang.genietext.transit.model.processor.requests.ReverseStopLookupRequest;
import com.canabang.genietext.transit.model.processor.requests.TravelPlannerRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;


/**
 * Tests the methods of the TransitRequestParser class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitRequestParserTest
{
	/** An instance of the class to test. */
	private TransitRequestParser t1;


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.TransitRequestParser#TransitRequestParser(javax.mail.internet.MimeMessage, com.canabang.genietext.core.model.structs.AddressSet)}.
	 * @throws MessagingException If there are problems sending the response back.
	 * @throws IOException If there is a problem accessing the external resources to process
	 * the message request.
	 * @throws ParseException If the contents of the requests message are not formatted properly.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service. 
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser. 
	 */
	@Test
	public void testTransitRequestParser() throws MessagingException, IOException, TransformerConfigurationException, ParserConfigurationException, SAXException
	{
		AddressSet s = new AddressSet("");
		Session mailSession = Session.getDefaultInstance( System.getProperties() );
		MimeMessage m = new MimeMessage(mailSession);
		m.setSubject("");
		m.setSender( new InternetAddress("test@hotmail.com") );
		m.setSentDate( new Date(System.currentTimeMillis()) );

		m.setContent("121 8894 l", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof LastBusTimeRequest );

		m.setContent("121 8894 w l", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof LastBusTimeRequest );
		assertEquals( "8:57 PM", t1.getRequest().process() );

		m.setContent("121 8894 d l", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof LastBusTimeRequest );
		assertEquals( "7:07 AM", t1.getRequest().process() );

		m.setContent("121 8894 s l", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof LastBusTimeRequest );
		assertEquals( "6:31 PM", t1.getRequest().process() );

		m.setContent("121 8894 f", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FirstBusTimeRequest );

		m.setContent("121 8894 w f", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FirstBusTimeRequest );
		assertEquals( "5:36 AM", t1.getRequest().process() );

		m.setContent("121 8894 d f", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FirstBusTimeRequest );
		assertEquals( "5:55 AM", t1.getRequest().process() );

		m.setContent("121 8894 s f", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FirstBusTimeRequest );
		assertEquals( "6:01 AM", t1.getRequest().process() );

		m.setContent("121 8894", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof NextBusTimeRequest );

		m.setContent("121 8894 1615", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof NextBusTimeRequest );

		m.setContent("121 8894 1615 w", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof NextBusTimeRequest );
		assertEquals( "4:15 PM, 4:35 PM, 4:55 PM", t1.getRequest().process() );

		m.setContent("121 8894 3", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof NextBusTimeRequest );

		m.setContent("121 8894 d a", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FullScheduleRequest );
		assertEquals( "5:55 AM, 6:06 AM, 7:07 AM", t1.getRequest().process() );

		m.setContent("121 8894 w a", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FullScheduleRequest );
		assertEquals( "5:36 AM, 6:06 AM, 6:26 AM, 6:45 AM, 7:05 AM, 7:25 AM, 7:45 AM, 8:05 AM, 8:25 AM, 8:45 AM, 9:05 AM, 9:25 AM, 9:55 AM, 10:25 AM, 10:55 AM, 12:25 PM, 12:55 PM, 1:25 PM, 1:55 PM, 2:25 PM, 2:55 PM, 3:15 PM, 3:35 PM, 3:55 PM, 4:15 PM, 4:35 PM, 4:55 PM, 5:15 PM, 5:35 PM, 5:55 PM, 6:15 PM, 6:35 PM, 7:25 PM, 7:55 PM, 8:25 PM, 8:57 PM", t1.getRequest().process() );

		m.setContent("121 8894 s a", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof FullScheduleRequest );
		assertEquals( "6:01 AM, 6:31 AM, 7:01 AM, 7:31 AM, 8:01 AM, 8:31 AM, 9:01 AM, 9:31 AM, 10:01 AM, 10:31 AM, 11:01 AM, 11:31 AM, 12:01 PM, 12:31 PM, 1:01 PM, 1:31 PM, 2:01 PM, 2:31 PM, 3:01 PM, 3:31 PM, 4:01 PM, 4:31 PM, 5:01 PM, 5:31 PM, 6:01 PM, 6:31 PM", t1.getRequest().process() );

		// -------- subscription test
		m.setContent("sub", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof SubscribeRequest );
		t1.getRequest().process();
		assertEquals( 1, t1.getSubscription().size() );
		t1.getRequest().process();
		assertEquals( 1, t1.getSubscription().size() );

		m.setFrom( new InternetAddress("bbb@hotmail.com") );
		t1 = new TransitRequestParser(m, s);
		t1.getRequest().process();
		assertEquals( 2, t1.getSubscription().size() );
		// ----------------------------

		m.setContent("unsub", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof UnsubscribeRequest );
		t1.getRequest().process();
		assertEquals( 1, t1.getSubscription().size() );

		m.setContent("Walkley.Jasper to 1010 Merivale Rd", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof TravelPlannerRequest );

		m.setContent("8894", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof ReverseStopLookupRequest );
		assertEquals( "Station 8894: 121 St. Laurent, 825 Hurdman, 114 Hurdman, 95 Orleans, 125 Hurdman, 191 Hurdman", t1.getRequest().process() );

		m.setContent("1234", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof ReverseStopLookupRequest );
		assertEquals( "Station 1234: does not exist in database.", t1.getRequest().process() );

		m.setContent("Wellington.Gilchrist", MessageBreaker.MESSAGE_TYPE);
		t1 = new TransitRequestParser(m, s);
		assertEquals( true, t1.getRequest() instanceof ReverseLocationLookupRequest );
		assertEquals( "WELLINGTON/GILCHRIST\nStation 7396 contains the following buses: 2", t1.getRequest().process() );

		//		m.setContent("121 8894", MessageBreaker.MESSAGE_TYPE); // on a weekday
		//		t1 = new TransitRequestParser(m, s);
		//		assertEquals( true, t1.getRequest() instanceof NextBusTimeRequest );
		//		System.out.println(t1.getRequest().process());
	}
}