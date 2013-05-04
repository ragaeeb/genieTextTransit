/*
 * @(#)TravelPlannerRequest.java	1.0	05/31/09
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


/**
 * Transit service request specifying the user wants information on how to get from one location
 * to another using a travel planner.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TravelPlannerRequest implements Request
{
	/** The user's initial location. */
	private String dest;

	/** The destination the user wants to get to. */
	private String src;


	/**
	 * Creates a request specifying the user wants to get from their initial location to their
	 * destination using the travel planner.
	 * @param src The user's initial location.
	 * @param dest The user's destination.
	 */
	public TravelPlannerRequest(String src, String dest)
	{
		this.src = src;
		this.dest = dest;
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.requests.Request#process()
	 */
	public String process()
	{
		String result = null;

		String[] srcStreets = src.split("[./]");
		String[] destStreets = dest.split("[./]");

		src = intersectionSpecifier(srcStreets, src);
		dest = intersectionSpecifier(destStreets, dest);
		result = "To get from "+src+" to "+dest+", take the ...";

		return result;
	}


	/**
	 * Converts the specified intersection to a human-readable format.
	 * @param srcStreets The intersection of a collection of streets.
	 * @param ori The original string specified by the user without the formatting removed.
	 * @return The human-readable intersection value.
	 */
	private static final String intersectionSpecifier(String[] srcStreets, String ori)
	{
		String src = "the ";

		if (srcStreets.length > 1)
		{	
			for (int i = 0; i < srcStreets.length; i++)
				src += srcStreets[i]+" ";

			src += "intersection";
		}

		else
			src = ori;

		return src;
	}
}