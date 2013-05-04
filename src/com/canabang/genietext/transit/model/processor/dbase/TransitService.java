/*
 * @(#)TransitService.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.io.ResourceManager;
import com.canabang.genietext.transit.model.io.XmlAllStopsParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * A simulator for a transit service database. Every transit service database has a collection of
 * buses and their respective schedules for certain locations they stop at.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitService
{
	/** The single instance of this factory to create. */
	private static TransitService instance;

	/** Database to extract location stop information from. */
	private static final InputStream locationsSrc = ResourceManager.loadFile("allstops.xml");

	/** Locations that contain a set of stops. */
	private Map< Intersection, Set<Station> > locations;

	/** The schedules associated with the buses for specific stops. */
	private Map<Bus, Schedule> schedules;

	/** Stop information and the set of buses that stop at those locations. */
	private Map< Integer, Set<Bus> > stops;


	/**
	 * Initializes the database and loads up the dependent data.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	private TransitService() throws ParserConfigurationException, SAXException, IOException
	{
		this.schedules = new Hashtable<Bus, Schedule>();
		this.stops = new Hashtable< Integer, Set<Bus> >();
		populate();

		XmlAllStopsParser parser = new XmlAllStopsParser();
		locations = parser.parse(locationsSrc);
	}


	/**
	 * Gets the schedule data associated with the specified bus for the specified schedule key.
	 * @param b The bus to locate the schedule for.
	 * @param schedule The schedule key to locate which day to retrieve the schedule information for.
	 * @return A set of times associated with the specified bus for the specified schedule key.
	 */
	public TreeSet<TransitTime> getScheduleTimes(Bus b, int schedule)
	{
		Schedule s = schedules.get(b);

		return s.get(schedule);
	}


	/**
	 * Populates this database with dummy values.
	 */
	private void populate()
	{
		Bus b = new Bus(121, 8894, "St. Laurent");
		Bus c = new Bus(95, 8894, "Orleans");
		Bus d = new Bus(114, 8894, "Hurdman");
		Bus e = new Bus(125, 8894, "Hurdman");
		Bus f = new Bus(191, 8894, "Hurdman");
		Bus g = new Bus(825, 8894, "Hurdman");

		Bus h = new Bus(8, 5880, "Billings Bridge");
		Bus i = new Bus(82, 5880, "Tunney's Pasture");
		Bus j = new Bus(112, 5880, "Elmvale");

		Set<Bus> buses2 = new HashSet<Bus>();
		buses2.add(h);
		buses2.add(i);
		buses2.add(j);

		Set<Bus> buses = new HashSet<Bus>();
		buses.add(b);
		buses.add(c);
		buses.add(d);
		buses.add(e);
		buses.add(f);
		buses.add(g);

		stops.put(8894, buses);
		stops.put(5880, buses2);

		Schedule s = new Schedule();
		TreeSet<TransitTime> times = new TreeSet<TransitTime>( TransitTimeComparator.getInstance() );
		times.add( new TransitTime(601) );
		times.add( new TransitTime(701) );
		times.add( new TransitTime(801) );
		times.add( new TransitTime(901) );
		times.add( new TransitTime(1001) );
		times.add( new TransitTime(1101) );
		times.add( new TransitTime(1201) );
		times.add( new TransitTime(1301) );
		times.add( new TransitTime(1401) );
		times.add( new TransitTime(1501) );
		times.add( new TransitTime(1601) );
		times.add( new TransitTime(1701) );
		times.add( new TransitTime(1801) );
		times.add( new TransitTime(631) );
		times.add( new TransitTime(731) );
		times.add( new TransitTime(831) );
		times.add( new TransitTime(931) );
		times.add( new TransitTime(1031) );
		times.add( new TransitTime(1131) );
		times.add( new TransitTime(1231) );
		times.add( new TransitTime(1331) );
		times.add( new TransitTime(1431) );
		times.add( new TransitTime(1531) );
		times.add( new TransitTime(1631) );
		times.add( new TransitTime(1731) );
		times.add( new TransitTime(1831) );
		s.setSaturdaySchedule(times);

		TreeSet<TransitTime> weekTimes = new TreeSet<TransitTime>( TransitTimeComparator.getInstance() );
		weekTimes.add( new TransitTime(536) );
		weekTimes.add( new TransitTime(606) );
		weekTimes.add( new TransitTime(626) );
		weekTimes.add( new TransitTime(645) );
		weekTimes.add( new TransitTime(705) );
		weekTimes.add( new TransitTime(725) );
		weekTimes.add( new TransitTime(745) );
		weekTimes.add( new TransitTime(805) );
		weekTimes.add( new TransitTime(825) );
		weekTimes.add( new TransitTime(845) );
		weekTimes.add( new TransitTime(905) );
		weekTimes.add( new TransitTime(925) );
		weekTimes.add( new TransitTime(955) );
		weekTimes.add( new TransitTime(1025) );
		weekTimes.add( new TransitTime(1055) );
		weekTimes.add( new TransitTime(1225) );
		weekTimes.add( new TransitTime(1255) );
		weekTimes.add( new TransitTime(1325) );
		weekTimes.add( new TransitTime(1355) );
		weekTimes.add( new TransitTime(1425) );
		weekTimes.add( new TransitTime(1455) );
		weekTimes.add( new TransitTime(1515) );
		weekTimes.add( new TransitTime(1535) );
		weekTimes.add( new TransitTime(1555) );
		weekTimes.add( new TransitTime(1615) );
		weekTimes.add( new TransitTime(1635) );
		weekTimes.add( new TransitTime(1655) );
		weekTimes.add( new TransitTime(1715) );
		weekTimes.add( new TransitTime(1735) );
		weekTimes.add( new TransitTime(1755) );
		weekTimes.add( new TransitTime(1815) );
		weekTimes.add( new TransitTime(1835) );
		weekTimes.add( new TransitTime(1925) );
		weekTimes.add( new TransitTime(1955) );
		weekTimes.add( new TransitTime(2025) );
		weekTimes.add( new TransitTime(2057) );
		s.setWeekdaySchedule(weekTimes);

		TreeSet<TransitTime> sunTimes = new TreeSet<TransitTime>( TransitTimeComparator.getInstance() );
		sunTimes.add( new TransitTime(555) );
		sunTimes.add( new TransitTime(606) );
		sunTimes.add( new TransitTime(707) );
		s.setSundaySchedule(sunTimes);

		schedules.put(b, s);
	}


	/**
	 * Retrieves a set of stops that are available at the specified location.
	 * @param i The location that contains potential stops.
	 * @return The set of stops that are available at the specified location.
	 */
	public Set<Station> reverseLocationLookup(Intersection i)
	{
		Set<Station> busStops = locations.get(i);

		return busStops;
	}


	/**
	 * Retrieves a set of buses that stop at the specified stop number.
	 * @param stop The stop number that potentially contains a set of buses associated with it.
	 * @return The set of stops that are available at the specified stop.
	 */
	public Set<Bus> reverseStopLookup(int stop)
	{
		Set<Bus> buses = stops.get(stop);

		return buses;
	}


	/**
	 * Retrieves a single instance of this class.
	 * @return A single instance of this class.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public static TransitService getInstance() throws ParserConfigurationException, SAXException, IOException
	{
		if (instance == null)
			instance = new TransitService();

		return instance;
	}
}