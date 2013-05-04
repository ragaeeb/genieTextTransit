/*
 * @(#)TransitTime.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.processor.dbase;

import com.canabang.genietext.core.model.structs.Time;
import java.util.Calendar;


/**
 * Represents a time in the transit service. Unlike normal time objects, the largest transit time
 * is when the last bus is dispatched, and the smallest is when the first bus is dispatched. Thus
 * the smallest transit time is 4:00 am, and the latest time is 3:59 am. Therefore when comparing
 * transit times an offset is needed.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitTime extends Time
{
	/** The latest time that will be affected by the offset. */
	private static final Time EARLIEST_OFFSET = new Time(0);

	/** The earliest time that will be affected by the offset. */
	private static final Time LATEST_OFFSET = new Time(359);

	/** Offset on hours required to maintain time order. */
	private static final int OFFSET_HOURS = 24;


	/**
	 * Constructs a transit time object retaining the minute, hour, and day values from the specified
	 * calendar. Sets the default time format.
	 * @param c1 The calendar to use as a reference for this time object.
	 */
	public TransitTime(Calendar c1)
	{
		super(c1);
	}


	/**
	 * Constructs a transit time object from the specified unformatted time value. Sets the default
	 * time format.
	 * @param src The unformatted time value to use as a reference for this time object.
	 */
	public TransitTime(int src)
	{
		super(src);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.transit.model.processor.Time#compareTo(com.canabang.genietext.transit.model.processor.Time)
	 */
	public int compareTo(TransitTime o)
	{
		int myHr = correctNecessaryOffsetHours(this);
		int myMins = getMinute();
		int oHr = correctNecessaryOffsetHours(o);
		int oMins = o.getMinute();

		int me = getUnformattedIntegerValue(myHr, myMins);
		int other = getUnformattedIntegerValue(oHr, oMins);

		return me-other;
	}


	/**
	 * Corrects the specified time's hours with the associated offset if necessary.
	 * @param t The time to correct with an offset.
	 * @return The offsetted hours.
	 */
	private static final int correctNecessaryOffsetHours(Time t)
	{
		boolean goeEarliest = t.compareTo(EARLIEST_OFFSET) >= 0;
		boolean loeLatest = t.compareTo(LATEST_OFFSET) <= 0;
		int result = t.getHour();

		if ( goeEarliest && loeLatest )
			result += OFFSET_HOURS;

		return result;
	}
}