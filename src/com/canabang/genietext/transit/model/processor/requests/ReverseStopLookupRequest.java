/*
 * @(#)ReverseStopLookupRequest.java	1.0	05/31/09
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
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Transit service request specifying the user wants to know all the buses that stop at a certain
 * bus stop number.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ReverseStopLookupRequest extends TransitServiceRequest
{
	/**
	 * Creates a request indicating that the user wants to know all the buses that stop at the
	 * specified stop number.
	 * @param station The station we wish to find out what buses it contains.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public ReverseStopLookupRequest(int station) throws ParserConfigurationException, SAXException, IOException
	{
		super(-1, station, null);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.requests.Request#process()
	 */
	public String process()
	{
		int number = requestedBus.getStation().getNumber();
		String result = new String();

		result += "Station "+number+": ";
		Collection<Bus> buses = service.reverseStopLookup(number);

		if (buses == null)
			result += "does not exist in database.";

		else
		{
			Iterator<Bus> i = buses.iterator();

			while ( i.hasNext() )
			{
				result += i.next().toString();

				if ( i.hasNext() ) // last element
					result += Request.COMMA_SEPARATOR;
			}
		}

		return result;
	}
}