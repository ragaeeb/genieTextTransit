/*
 * @(#)Schedule.java	1.0	05/31/09
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

import java.util.Calendar;
import java.util.Hashtable;
import java.util.TreeSet;


/**
 * The schedule associated with a bus in the transit service. Transit service schedules usually
 * have three types of schedules: Weekday, Saturday, and Sunday. Each of these schedules maintains
 * an independent list of times when a bus arrives at a certain stop.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class Schedule extends Hashtable<Integer, TreeSet<TransitTime> >
{
	/** The key associated with the Saturday schedule. */
	public static final int KEY_SATURDAY = Calendar.SATURDAY;

	/** The key associated with the Sunday schedule. */
	public static final int KEY_SUNDAY = Calendar.SUNDAY;

	/** The key associated with the Weekday schedule. */
	public static final int KEY_WEEKDAY = Calendar.MONDAY;

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a Schedule object so that its specific schedules may be set.
	 */
	public Schedule()
	{
		super();
	}


	/**
	 * Sets the Saturday schedule to contain the specified one.
	 * @param satTimes The new Saturday schedule.
	 */
	public void setSaturdaySchedule(TreeSet<TransitTime> satTimes)
	{
		put(KEY_SATURDAY, satTimes);
	}


	/**
	 * Sets the Sunday schedule to contain the specified one.
	 * @param sunTimes The new Sunday schedule.
	 */
	public void setSundaySchedule(TreeSet<TransitTime> sunTimes)
	{
		put(KEY_SUNDAY, sunTimes);
	}


	/**
	 * Sets the weekday schedule to contain the specified one.
	 * @param weekTimes The new weekday schedule.
	 */
	public void setWeekdaySchedule(TreeSet<TransitTime> weekTimes)
	{
		put(KEY_WEEKDAY, weekTimes);
	}
}