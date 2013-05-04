/*
 * @(#)Intersection.java	1.0	05/31/09
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
import com.canabang.genietext.transit.model.io.PatternReplace;
import java.util.Map;


/**
 * A location. If this location is an intersection then it needs two streets populated. If it is
 * a location, then only a single street value is populated and the other is blank.
 * 
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public abstract class Intersection
{
	/** The keyword we need to strip out because this will be complicated for users to input. */
	private static final String ARRET = "ARRÊT";

	/** Maps special character patterns with their closest English look-alike. */
	protected static final Map<String, String> patternMap = new PatternReplace();

	/** The first street or location. */
	protected String street1;

	/** The intersecting street. */
	protected String street2;


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;

		try {
			Intersection i = (Intersection)obj;
			result = i.street1.equals(street1) && i.street2.equals(street2);
		}

		catch (ClassCastException ce)
		{
		}

		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return street1.hashCode()+street2.hashCode();
	}


	/**
	 * Populate the specified unformatted location data according to the specified pattern.
	 * @param formatted The unformatted location data.
	 * @param delimiter The delimiter pattern used to parse the location data.
	 */
	protected void populate(String formatted, String delimiter)
	{
		String[] split = formatted.split(delimiter);
		street1 = split[0];
		street2 = split[1];

		if ( street2.toUpperCase().contains(ARRET) )
			street2 = XmlProperties.BLANK;

		street1 = street1.trim();
		street2 = street2.trim();

		for ( String key: patternMap.keySet() )
		{
			street1 = street1.replaceAll( key, patternMap.get(key) );
			street2 = street2.replaceAll( key, patternMap.get(key) );
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String result = street1;

		if ( !street2.isEmpty() )
			result += SlashedIntersection.DELIMITER1+street2;

		return result;
	}
}