/*
 * @(#)UnformattedDateTimeParser.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.structs.CanadianGregorianCalendar;
import com.canabang.genietext.core.model.structs.Time;
import java.util.Calendar;
import java.util.Date;


/**
 * Converts an unformatted time and schedule into a Calendar object.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class UnformattedDateTimeParser
{
	/** The letter code to specify a Saturday schedule. */
	private static final String CODE_SATURDAY_SCHEDULE = "s";

	/** The letter code to specify a Sunday schedule. */
	private static final String CODE_SUNDAY_SCHEDULE = "d";

	/** The raw time data associated with the specified schedule and time. */
	private Calendar calendar;


	/**
	 * Parses the specified date object and retains the necessary properties.
	 * @param date The raw date to parse.
	 */
	public UnformattedDateTimeParser(Date date)
	{
		instantiateCalendar();
		this.calendar.setTime(date);
	}


	/**
	 * Parses the specified date object along with the specified unformatted time value and
	 * retains the necessary properties.
	 * @param date The raw date to parse.
	 * @param timeValue The requested unformatted time to parse.
	 */
	public UnformattedDateTimeParser(Date date, String timeValue)
	{
		populateTime(timeValue);

		Calendar c = new CanadianGregorianCalendar();
		c.setTime(date);

		this.calendar.set( Calendar.DAY_OF_WEEK, c.get(Calendar.DAY_OF_WEEK) );
	}


	/**
	 * Parses the specified schedule code to populate the requested date along with necessary fields.
	 * @param scheduleCode The schedule code to parse (must be s, d, or w).
	 */
	public UnformattedDateTimeParser(String scheduleCode)
	{
		instantiateCalendar();
		populateSchedule(scheduleCode);
	}


	/**
	 * Parses the specified schedule code and unformatted time value to populate the requested
	 * date along with necessary fields.
	 * @param scheduleCode The schedule code to parse (must be s, d, or w).
	 * @param timeValue The requested unformatted time to parse.
	 */
	public UnformattedDateTimeParser(String scheduleCode, String timeValue)
	{
		populateTime(timeValue);
		populateSchedule(scheduleCode);
	}


	/**
	 * Retrieves the parsed calendar data.
	 * @return The raw calendar data that was parsed from the specified parameters.
	 */
	public Calendar getCalendar()
	{
		return calendar;
	}


	/**
	 * Creates a new instance of the calendar.
	 */
	private void instantiateCalendar()
	{
		this.calendar = new CanadianGregorianCalendar();
	}


	/**
	 * Populates the calendar with the necessary fields according to the specified schedule code.
	 * @param scheduleCode The schedule code to parse (must be s, d, or w).
	 */
	private void populateSchedule(String scheduleCode)
	{
		if ( scheduleCode.equals(CODE_SUNDAY_SCHEDULE) )
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		else if ( scheduleCode.equals(CODE_SATURDAY_SCHEDULE) )
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

		else // weekday
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	}


	/**
	 * Populates the calendar with the necessary fields according to the specified unformatted
	 * time value. 
	 * @param timeValue The requested unformatted time to parse.
	 */
	private void populateTime(String timeValue)
	{
		this.calendar = Time.constructCalendarFrom( Integer.parseInt(timeValue) );
	}
}