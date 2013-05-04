/*
 * @(#)NextBusTimeRequest.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.processor.requests;

import com.canabang.genietext.core.model.processor.requests.Request;
import com.canabang.genietext.transit.model.processor.dbase.Schedule;
import com.canabang.genietext.transit.model.processor.dbase.TransitTime;
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Transit service request specifying the user wants the very next set of times a bus arrives at
 * a certain station.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class NextBusTimeRequest extends TransitServiceRequest
{
	/** The default number of times to return to the user. */
	private static final int DEFAULT_FREQUENCY = 3;

	/** The number of times to return to the user. */
	private int frequency;

	/** We need to get a value at least equal to this value. */
	private TransitTime time;


	/**
	 * Creates a request specifying the user wants the next set of times the specified bus
	 * comes to the specified station on the specified day.
	 * @param busNumber The bus the user wants the first time for.
	 * @param station The stop number associated with the bus.
	 * @param sentTime The scheduled date associated with the request.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public NextBusTimeRequest(int busNumber, int station, Calendar sentTime) throws ParserConfigurationException, SAXException, IOException
	{
		super(busNumber, station, sentTime);
		this.time = new TransitTime(sentTime);
		this.frequency = DEFAULT_FREQUENCY;
	}


	/**
	 * Creates a request specifying the user wants the next set of times the specified bus
	 * comes to the specified station on the specified day, and to return the specified amount
	 * of results.
	 * @param busNumber The bus the user wants the first time for.
	 * @param station The stop number associated with the bus.
	 * @param sentTime The scheduled date associated with the request.
	 * @param frequency The amount of results that user wants to return.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public NextBusTimeRequest(int busNumber, int station, Calendar sentTime, int frequency) throws ParserConfigurationException, SAXException, IOException
	{
		this(busNumber, station, sentTime);

		if (frequency <= 0)
			throw new IllegalArgumentException("Invalid frequency specified");

		this.frequency = frequency;
	}


	/**
	 * Retrieves the correct schedule if we reached the end of the list of a certain schedule.
	 * @param goe Pointer to set to the correct schedule.
	 * @return The correct following schedule.
	 */
	private Set<TransitTime> fixSchedule(Set<TransitTime> goe)
	{
		switch ( sentTime.get(Calendar.DAY_OF_WEEK) )
		{
			case Calendar.SATURDAY:
				goe = service.getScheduleTimes(requestedBus, Schedule.KEY_SUNDAY);
				break;

			case Calendar.FRIDAY:
				goe = service.getScheduleTimes( requestedBus, Schedule.KEY_SATURDAY);
				break;

			default: // since if it's sunday, we move right to the weekday, & if it's weekday (& not a friday) we move to the next week day
				goe = service.getScheduleTimes( requestedBus, Schedule.KEY_WEEKDAY);
				break;
		}

		return goe;
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.requests.Request#process()
	 */
	public String process()
	{
		String result = new String();

		try {
			TreeSet<TransitTime> t = getRequestedSchedule();

			Set<TransitTime> goe = t.tailSet(time);

			if ( goe.isEmpty() )
				goe = fixSchedule(goe);

			// --------- now start appending time values
			int i = 1;

			for (TransitTime c: goe)
			{
				result += c.toString();

				if (i < frequency)
					result += Request.COMMA_SEPARATOR;

				else
					break;

				i++;
			}
		}

		catch (NullPointerException ex)
		{
			result = "The specified bus does not exist in the database.";
		}

		return result;
	}
}