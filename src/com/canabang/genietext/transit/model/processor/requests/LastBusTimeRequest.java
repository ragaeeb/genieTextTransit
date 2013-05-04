/*
 * @(#)LastBusTimeRequest.java	1.0	05/31/09
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

import com.canabang.genietext.transit.model.processor.dbase.TransitTime;
import java.io.IOException;
import java.util.Calendar;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Transit service request specifying the user wants the very last time a bus arrives at a certain
 * station.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class LastBusTimeRequest extends TransitServiceRequest
{
	/**
	 * Creates a request specifying the user wants the last time the specified bus comes to the
	 * specified station on the specified day.
	 * @param busNumber The bus the user wants the first time for.
	 * @param station The stop number associated with the bus.
	 * @param sentTime The scheduled date associated with the request.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public LastBusTimeRequest(int busNumber, int station, Calendar sentTime) throws ParserConfigurationException, SAXException, IOException
	{
		super(busNumber, station, sentTime);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.Request#process()
	 */
	public String process()
	{
		String result;

		try {
			TreeSet<TransitTime> t = getRequestedSchedule();
			result = t.last().toString();
		}

		catch (NullPointerException ex)
		{
			result = "Bus specified does not exist in the database";
		}

		return result;
	}
}