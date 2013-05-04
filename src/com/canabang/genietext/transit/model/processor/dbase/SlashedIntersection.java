/*
 * @(#)SlashedIntersection.java	1.0	05/31/09
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
 * A location represented using the slash notation (ie: Walkley/Jasper or Walkley\Jasper means
 * the intersection of Walkey street and Jasper street). 
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SlashedIntersection extends Intersection
{
	/** A pattern used to separate one street from the other. */
	public static final String DELIMITER1 = "/";

	/** A pattern used to separate one street from the other. */
	public static final String DELIMITER2 = "\\";


	/**
	 * Populates this class with the location data.
	 * @param formatted The location information using the slash notation.
	 */
	public SlashedIntersection(String formatted)
	{
		if ( formatted.contains(DELIMITER1) )
			super.populate(formatted, DELIMITER1);

		else if ( formatted.contains(DELIMITER2) )
			super.populate(formatted, DELIMITER2+DELIMITER2);

		else // not an intersection, just a location
		{
			street1 = formatted;
			street1 = street1.trim();
			street2 = XmlProperties.BLANK;

			for ( String key: patternMap.keySet() )
				street1 = street1.replaceAll( key, patternMap.get(key) );
		}
	}
}