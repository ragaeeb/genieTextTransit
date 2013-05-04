/*
 * @(#)Bus.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.io.xml.XmlProperties;


/**
 * The Bus class represents a bus in the transit system. Each bus has a number,
 * a direction, a specific station at which it arrives at specific
 * times.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class Bus
{
	/** The name of the direction this bus goes towards. */
	private Direction direction;

	/** The bus number. */
	private int number;

	/** The station or stop this bus reaches. */
	private Station station;


	/**
	 * Creates a bus object with the specified number and station that it stops at.
	 * @param number The number associated with the bus.
	 * @param station The station associated with this bus.
	 */
	public Bus(int number, int station)
	{
		this(number, station, XmlProperties.BLANK);
	}


	/**
	 * Creates a bus object with the specified number and station that it stops at.
	 * @param number The number associated with the bus.
	 * @param station The station associated with this bus.
	 * @param direction The direction this bus goes towards.
	 */
	public Bus(int number, int station, String direction)
	{
		this.number = number;
		this.station = new Station(station);
		this.direction = new Direction(direction);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;

		try {
			Bus b = (Bus)obj;
			result = (b.number == this.number) && ( b.getStation().equals(station) );
		}

		catch (ClassCastException ce)
		{
		}

		return result;
	}


	/**
	 * Retrieves the direction this bus goes towards.
	 * @return The direction this bus goes towards.
	 */
	public Direction getDirection()
	{
		return direction;
	}


	/**
	 * Retrieves the number associated with this bus.
	 * @return The number associated with this bus.
	 */
	public int getNumber()
	{
		return number;
	}


	/**
	 * Retrieves the station this bus stops at.
	 * @return The stop this bus goes to.
	 */
	public Station getStation()
	{
		return station;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return number+station.getNumber();
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.transit.model.processor.dbase.Direction#setName(java.lang.String)
	 */
	public void setDirection(String dirName)
	{
		direction.setName(dirName);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return number+" "+direction.getName();
	}
}