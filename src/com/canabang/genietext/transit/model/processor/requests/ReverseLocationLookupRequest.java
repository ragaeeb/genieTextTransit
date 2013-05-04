/*
 * @(#)ReverseLocationLookupRequest.java	1.0	05/31/09
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
import com.canabang.genietext.transit.model.processor.dbase.DottedIntersection;
import com.canabang.genietext.transit.model.processor.dbase.Intersection;
import com.canabang.genietext.transit.model.processor.dbase.Station;
import com.canabang.genietext.transit.model.processor.dbase.TransitService;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Transit service request specifying the user wants to know all the bus stops that exist at a
 * certain location. The location must be in the dotted notation format (ie: Walkley.Jasper if
 * Walkley street and Jasper street intersect).
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ReverseLocationLookupRequest implements Request
{
	/** Separates one stop information from another. */
	private static final String SEPARATOR = "\n";

	/** The actual intersection information. */
	private Intersection location;

	/** The transit service database. */
	private TransitService service;



	/**
	 * Creates a request specifying the user wants the what bus stops exist at a certain location.
	 * @param dottedLocations The location in dotted-format (ie: Walkley.Jasper) if Walkey Street
	 * intersects with Jasper street. A location can simply also just be a single location (ie:
	 * Billings Bridge Stop).
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service.
	 * @throws IOException If there is a problem accessing the resource file for the transit service.
	 */
	public ReverseLocationLookupRequest(String dottedLocations) throws ParserConfigurationException, SAXException, IOException
	{
		this.service = TransitService.getInstance();
		location = new DottedIntersection( dottedLocations.toUpperCase() );
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.requests.Request#process()
	 */
	public String process()
	{
		Set<Station> stops = service.reverseLocationLookup(location);
		String result = location.toString();

		if (stops == null)
			result += " not found in database.";

		else
		{
			result += SEPARATOR;
			Iterator<Station> i = stops.iterator();

			while ( i.hasNext() )
			{
				result += i.next().toString();

				if ( i.hasNext() )
					result += SEPARATOR;
			}
		}

		return result;
	}
}