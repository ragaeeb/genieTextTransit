/*
 * @(#)Time.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.structs;

import com.canabang.genietext.core.model.io.xml.XmlProperties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Represents a time. Times can be compared (ie: 11am is smaller than 12pm, and 11:01am is smaller
 * than 11:02am). The smallest time is 12am, and the largest time is 11:59pm. Unformatted times
 * are considered to be the 24-hour time value as an integer without the colon separating the
 * hour and the minute values (ie: 11:59pm <=> 23:59 <=> 2359, 12am <=> 00:00 <=> 0).
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class Time implements Comparable<Time>
{
	/** Formats raw time values to human-friendly formats. */
	private static DateFormat formatter;

	/** The number of hours in the military clock format. */
	public static final int MILITARY_CLOCK_FORMAT = 24;

	/** Dividing an unformatted time value by this calculates the hour value, and the remainder gives the minute value. */
	private static final int MINUTE_HOUR_DIVISOR = 100;

	/** The number of hours in the standard clock format. */
	public static final int STANDARD_CLOCK_FORMAT = 12;

	/** Stores raw time data. */
	protected Calendar c;


	/**
	 * Constructs a time object retaining the minute, hour, and day values from the specified
	 * calendar. Sets the default time format.
	 * @param c1 The calendar to use as a reference for this time object.
	 */
	public Time(Calendar c1)
	{
		this.c = constructCalendarFrom( getUnformattedIntegerValue( c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE) ) );
		initializeFormat();
	}


	/**
	 * Constructs a time object from the specified unformatted time value. Sets the default
	 * time format.
	 * @param t The unformatted time value to use as a reference for this time object.
	 */
	public Time(int t)
	{
		this.c = constructCalendarFrom(t);
		initializeFormat();
	}


	/**
	 * Constructs a time object from the specified unformatted time value stored as a string.
	 * Sets the default time format.
	 * @param src The unformatted time value to use as a reference for this time object.
	 */
	public Time(String src)
	{
		this( Integer.parseInt(src) );
	}


	/**
	 * Copy constructor.
	 * @param t The time object to reference this time's values for.
	 */
	public Time(Time t)
	{
		this ( t.getUnformattedValue() );
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Time o)
	{
		return getUnformattedIntegerValue()-o.getUnformattedIntegerValue();
	}


	/**
	 * Gets the hour value of this time.
	 * @return The hour of day value of this time.
	 */
	public int getHour()
	{
		return c.get(Calendar.HOUR_OF_DAY);
	}


	/**
	 * Gets the minute value of this time.
	 * @return The minute within the hour of this time.
	 */
	public int getMinute()
	{
		return c.get(Calendar.MINUTE);
	}


	/**
	 * Gets an integer value of the unformatted value of this time.
	 * @return An integer unformatted time value (ie: 2359 for 11:59pm, 0 for 12am, 1000 for 10:00am, etc.).
	 */
	public int getUnformattedIntegerValue()
	{
		return getUnformattedIntegerValue( getHour(), getMinute() );
	}


	/**
	 * Gets a String unformatted time representation of this time.
	 * @return An unformatted time String value of this time.
	 */
	public String getUnformattedValue()
	{
		return XmlProperties.BLANK+getUnformattedIntegerValue();
	}


	/**
	 * If the raw time formatter has not been initialized yet, the default format is set.
	 */
	private void initializeFormat()
	{
		if (formatter == null)
			setDefaultFormat();		
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return formatter.format( c.getTime() );
	}


	/**
	 * Constructs a Calendar object from the specified unformatted time value retaining its hour
	 * of day and minutes.
	 * @param t The unformatted time value to construct the Calendar from.
	 */
	public static Calendar constructCalendarFrom(int t)
	{
		Calendar c = new CanadianGregorianCalendar();

		int hr = t/MINUTE_HOUR_DIVISOR;
		int mins = t%MINUTE_HOUR_DIVISOR;

		c.set(Calendar.HOUR_OF_DAY, hr);
		c.set(Calendar.MINUTE, mins);

		return c;
	}


	/**
	 * Constructs an unformatted time value given the specified hour and minutes.
	 * @param hr The hour of day value (in 24-hour format).
	 * @param mins The minute of the hour value.
	 * @return An unformatted time value from the specified hour and minutes.
	 */
	protected static final int getUnformattedIntegerValue(int hr, int mins)
	{
		return (hr*MINUTE_HOUR_DIVISOR)+mins;
	}


	/**
	 * Sets the default time format to be the 12-hour format (ie: 12:00 PM, 5:35 AM, etc.).
	 */
	public static final void setDefaultFormat()
	{
		formatter = new SimpleDateFormat("h:mm a");
	}


	/**
	 * Sets the default time format to be the 24-hour format (ie: 12:00, 5:35, etc.).
	 */
	public static final void setMilitaryFormat()
	{
		formatter = new SimpleDateFormat("hh:mm");
	}
}