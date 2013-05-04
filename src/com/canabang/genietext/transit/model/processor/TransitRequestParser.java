/*
 * @(#)TransitRequestParser.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.processor;

import com.canabang.genietext.core.model.io.xml.XmlProperties;
import com.canabang.genietext.core.model.processor.requests.Request;
import com.canabang.genietext.core.model.processor.requests.SubscribeRequest;
import com.canabang.genietext.core.model.processor.requests.UnsubscribeRequest;
import com.canabang.genietext.core.model.structs.AddressSet;
import com.canabang.genietext.transit.model.processor.requests.FirstBusTimeRequest;
import com.canabang.genietext.transit.model.processor.requests.FullScheduleRequest;
import com.canabang.genietext.transit.model.processor.requests.HelpRequest;
import com.canabang.genietext.transit.model.processor.requests.LastBusTimeRequest;
import com.canabang.genietext.transit.model.processor.requests.NextBusTimeRequest;
import com.canabang.genietext.transit.model.processor.requests.ReverseLocationLookupRequest;
import com.canabang.genietext.transit.model.processor.requests.ReverseStopLookupRequest;
import com.canabang.genietext.transit.model.processor.requests.TravelPlannerRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Helps parse the complex syntax stored in the request message into actual request objects.
 * 
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitRequestParser
{
	/** Once this character is encountered, it marks the end of the request. */
	private static final String MESSAGE_TERMINATING_PATTERN = "\n";

	/** Indicates user wants the first time a bus that arrives at the specified station (ie: 84 8894 f). */
	private static final String PATTERN_BUS_STATION_FIRST = "\\d{1,3}+ \\d{4}+ f";

	/** Indicates user wants the full list of times associated with a bus that arrives at the specified station (ie: 84 8894 a). */
	private static final String PATTERN_BUS_STATION_FULL = "\\d{1,3}+ \\d{4}+ a";

	/** Indicates user wants the last time a bus that arrives at the specified station (ie: 84 8894 l). */
	private static final String PATTERN_BUS_STATION_LAST = "\\d{1,3}+ \\d{4}+ l";

	/** Indicates user wants the next time a bus is arriving at the specified station (ie: 84 8894). */
	private static final String PATTERN_BUS_STATION_NEXT_TIME = "\\d{1,3}+ \\d{4}+";

	/** Indicates user wants the next time a bus is arriving at the specified station with the specific amount of maximum results (ie: 84 8894 3). */
	private static final String PATTERN_BUS_STATION_NEXT_TIME_FREQUENCY = "\\d{1,3}+ \\d{4}+ \\d{1}";

	/** Indicates user wants the first time a bus that arrives at the specified station on the given schedule (ie: 84 8894 s f). */
	private static final String PATTERN_BUS_STATION_SCHEDULE_FIRST = "\\d{1,3}+ \\d{4}+ [sdw] f";

	/** Indicates user wants the full list of times associated with a bus that arrives at the specified station for the given schedule (ie: 84 8894 s a). */
	private static final String PATTERN_BUS_STATION_SCHEDULE_FULL = "\\d{1,3}+ \\d{4}+ [sdw] a";

	/** Indicates user wants the last time a bus that arrives at the specified station for the given schedule (ie: 84 8894 s l). */
	private static final String PATTERN_BUS_STATION_SCHEDULE_LAST = "\\d{1,3}+ \\d{4}+ [sdw] l";

	/** Indicates user wants the next time a bus is arriving at the specified station after the specified time (ie: 84 8894 1615). */
	private static final String PATTERN_BUS_STATION_TIME = "\\d{1,3}+ \\d{4}+ \\d{3,4}+";

	/** Indicates user wants the next time a bus is arriving at the specified station after the specified time, and to return the specified amount of results (ie: 84 8894 1615 3). */
	private static final String PATTERN_BUS_STATION_TIME_FREQUENCY = "\\d{1,3}+ \\d{4}+ \\d{3,4}+ \\d{1}";

	/** Indicates user wants the next time a bus is arriving at the specified station after the specified time for the specified schedule (ie: 84 8894 1615 s). */
	private static final String PATTERN_BUS_STATION_TIME_SCHEDULE = "\\d{1,3}+ \\d{4}+ \\d{3,4}+ [sdw]";

	/** Indicates user wants the next time a bus is arriving at the specified station after the specified time for the specified schedule, and to return the specified amount of results (ie: 84 8894 1615 s 3). */
	private static final String PATTERN_BUS_STATION_TIME_SCHEDULE_FREQUENCY = "\\d{1,3}+ \\d{4}+ \\d{3,4}+ [sdw] \\d{1}";

	/** Indicates a reverse location bus stop lookup request (ie: Walkley.Jasper). */
	private static final String PATTERN_REVERSE_LOCATION_LOOKUP = ".";

	/** Indicates a reverse stop lookup request (ie: 8894). */
	private static final String PATTERN_REVERSE_STOP_LOOKUP = "\\d{4}+";

	/** Indicates an subscribe request (ie: sub). */
	private static final String PATTERN_SUBSCRIBE = "sub";

	/** Indicates a travel planner request (ie: Walkley.Jasper to 1010 Merivale Rd). */
	private static final String PATTERN_TRAVEL_PLANNER = " to ";

	/** Indicates an unsubscribe request (ie: unsub). */
	private static final String PATTERN_UNSUBSCRIBE = "unsub";

	/** Separates one token from another in the request message. */
	private static final String TOKEN_SEPARATOR = " ";

	/** The request actually specified by the user. */
	private Request request;

	/** The subscription list associated with the service. */
	private AddressSet subscription;


	/**
	 * Creates a transit request message parser to parse the specified message.
	 * @param received The message request to process.
	 * @param subscription The set of subscribers associated with this service.
	 * @throws MessagingException If there are problems sending the response back.
	 * @throws IOException If there is a problem accessing the external resources to process
	 * the message request.
	 * @throws ParseException If the contents of the requests message are not formatted properly.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service. 
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 */
	public TransitRequestParser(MimeMessage received, AddressSet subscription) throws IOException, MessagingException, ParserConfigurationException, SAXException
	{
		this.subscription = subscription;
		String message = (String)received.getContent(); // safe conversion
		String match;

		if ( message.toLowerCase().startsWith(PATTERN_SUBSCRIBE) )
			this.request = new SubscribeRequest( subscription, received.getFrom() );

		else if ( message.toLowerCase().startsWith(PATTERN_UNSUBSCRIBE) )
			this.request = new UnsubscribeRequest( subscription, received.getFrom() );

		else if ( message.toLowerCase().contains(PATTERN_TRAVEL_PLANNER) )
		{
			int start = message.indexOf(PATTERN_TRAVEL_PLANNER);
			String src = message.substring(0, start);

			String dest;
			int finish = message.indexOf(MESSAGE_TERMINATING_PATTERN);

			if ( finish != -1 )
				dest = message.substring( start+PATTERN_TRAVEL_PLANNER.length(), finish-1 );

			else
				dest = message.substring( start+PATTERN_TRAVEL_PLANNER.length() );

			this.request = new TravelPlannerRequest(src, dest);
		}

		else if ( message.contains(PATTERN_REVERSE_LOCATION_LOOKUP) ) // Walkley.Jasper
		{
			String[] tokens = message.split(MESSAGE_TERMINATING_PATTERN);
			this.request = new ReverseLocationLookupRequest( tokens[0] );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_TIME_SCHEDULE_FREQUENCY, message) ) ) // 8 8894 1635 d 3
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( tokens[3], tokens[2] );
			this.request = new NextBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar(), Integer.parseInt( tokens[4] ) );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_TIME_SCHEDULE, message) ) ) // 8 8894 1635 d
		{
			String[] tokens = getElements(match);
			UnformattedDateTimeParser u = new UnformattedDateTimeParser( tokens[3], tokens[2] );
			this.request = new NextBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_SCHEDULE_FULL, message) ) ) // 8 8894 d f
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( tokens[2] );
			this.request = new FullScheduleRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_FULL, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate() );
			this.request = new FullScheduleRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_LAST, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate() );
			this.request = new LastBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_SCHEDULE_LAST, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( tokens[2] );
			this.request = new LastBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_FIRST, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate() );
			this.request = new FirstBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_SCHEDULE_FIRST, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( tokens[2] );
			this.request = new FirstBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_TIME_FREQUENCY, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate(), tokens[2] );
			this.request = new NextBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar(), Integer.parseInt( tokens[3] ) );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_TIME, message) ) ) // 8 8894 1635
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate(), tokens[2] );
			this.request = new NextBusTimeRequest( Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_NEXT_TIME, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate() );
			this.request = new NextBusTimeRequest( Integer.parseInt( tokens[0] ), Integer.parseInt( tokens[1] ), u.getCalendar() );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_BUS_STATION_NEXT_TIME_FREQUENCY, message) ) )
		{
			String[] tokens = getElements(match);

			UnformattedDateTimeParser u = new UnformattedDateTimeParser( received.getSentDate() );
			this.request = new NextBusTimeRequest( Integer.parseInt(tokens[0]), Integer.parseInt( tokens[1] ), u.getCalendar(), Integer.parseInt( tokens[3] ) );
		}

		else if ( validMatch( match = matchedRegion(PATTERN_REVERSE_STOP_LOOKUP, message) ) )
			this.request = new ReverseStopLookupRequest( Integer.parseInt(match) );

		else
			this.request = new HelpRequest();
	}


	/**
	 * Retrieves the actual request specified by the user. 
	 * @return The request actually specified by the user.
	 */
	public Request getRequest()
	{
		return request;
	}


	/**
	 * Retrieves the subscription list associated with the service..
	 * @return The subscription list associated with the service.
	 */
	public AddressSet getSubscription()
	{
		return subscription;
	}


	/**
	 * Gets the elements of the request specified by the user.
	 * @param request The request text specified by the user.
	 * @return The elements of the request.
	 */
	private static final String[] getElements(String request)
	{
		return request.split(TOKEN_SEPARATOR);
	}


	/**
	 * Gets the region that matches the pattern specified from the given string.
	 * @param pattern The pattern to look for in the message.
	 * @param message The message that may potentially contain the pattern specified.
	 * @return The region that matches the pattern specified from the given message text.
	 */
	private static final String matchedRegion(String pattern, String message)
	{
		Pattern patt = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = patt.matcher(message);
		String result = XmlProperties.BLANK;

		if ( m.find() )
			result = message.substring( m.start(), m.end() );

		return result;
	}


	/**
	 * Determines if the specified string is empty.
	 * @param matchedRegion The text to check for emptiness.
	 * @return true if the specified string is not empty, false otherwise.
	 */
	private static final boolean validMatch(String matchedRegion)
	{
		return !(matchedRegion).isEmpty();
	}
}