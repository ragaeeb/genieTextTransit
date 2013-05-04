/*
 * @(#)Station.java	1.0	05/31/09
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * The station or stop number associated with a bus. A stop number may have a more descriptive
 * name if necessary. Two stations may be identical despite the fact that one of them may have
 * a name, while the other one doesn't as long as they have equivalent stop numbers.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class Station
{
	/** The set of bus numbers that stop at this station. */
	private Set<Integer> buses;

	/** The descriptive name for this stop number. */
	private String name;

	/** The station number. */
	private int number;


	/**
	 * Creates a station with the specified station number.
	 * @param stationNumber The stop number associated with this station.
	 */
	public Station(int stationNumber)
	{
		this.number = stationNumber;
		this.buses = new HashSet<Integer>();
	}


	/**
	 * Adds a bus to the specified station.
	 * @param number The bus number that arrives at this bus stop.
	 */
	public void addBus(int number)
	{
		this.buses.add(number);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;

		try {
			Station s = (Station)obj;

			if ( (name != null) && (s.name != null) )
				result = s.name.equals(name);

			else
				result = (s.number == number);
		}

		catch (ClassCastException ce)
		{
		}

		return result;
	}


	/**
	 * Retrieves the name of this station.
	 * @return The descriptive name associated with this station.
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Retrieves the station number of this stop.
	 * @return The number associated with this stop.
	 */
	public int getNumber()
	{
		return number;
	}


	/**
	 * Sets the descriptive name of this station to the specified value.
	 * @param name The name of this station.
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String result = "Station "+number+" contains the following buses: ";
		Iterator<Integer> i = buses.iterator();

		while ( i.hasNext() )
		{
			result += i.next();

			if ( i.hasNext() )
				result += ", ";
		}

		return result;
	}
}