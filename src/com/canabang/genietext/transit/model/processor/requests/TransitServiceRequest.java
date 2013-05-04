/*
 * @(#)TransitServiceRequest.java	1.0	05/31/09
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
import com.canabang.genietext.transit.model.processor.dbase.Bus;
import com.canabang.genietext.transit.model.processor.dbase.TransitService;
import com.canabang.genietext.transit.model.processor.dbase.TransitTime;
import java.io.IOException;
import java.util.Calendar;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Transit service request specifying the user wants some information from the transit service
 * database.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public abstract class TransitServiceRequest implements Request
{
	/** The bus requested by the user. */
	protected Bus requestedBus;

	/** The requested time specified by the user. */
	protected Calendar sentTime;

	/** The transit service database. */
	protected TransitService service;


	/**
	 * Creates a request specifying the user wants some sort of information from the transit
	 * service database.
	 * @param busNumber The bus the user wants the first time for.
	 * @param station The stop number associated with the bus.
	 * @param sentTime The scheduled date associated with the request.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public TransitServiceRequest(int busNumber, int station, Calendar sentTime) throws ParserConfigurationException, SAXException, IOException
	{
		this.requestedBus = new Bus(busNumber, station);
		this.service = TransitService.getInstance();
		this.sentTime = sentTime;
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.requests.Request#process()
	 */
	protected TreeSet<TransitTime> getRequestedSchedule()
	{
		int dayOfWeek = sentTime.get(Calendar.DAY_OF_WEEK);
		boolean weekDay = (dayOfWeek != Calendar.SUNDAY) && (dayOfWeek != Calendar.SATURDAY);

		if (weekDay)
			dayOfWeek = Calendar.MONDAY;

		return service.getScheduleTimes(requestedBus, dayOfWeek);
	}
}