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

import java.util.Comparator;


/**
 * Allows transit times to be compared to see which one is greater, smaller, or equal to another.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitTimeComparator implements Comparator<TransitTime>
{
	/** The single instance of this factory to create. */
	private static TransitTimeComparator instance;


	/**
	 * Creates an instance of this class so that two transit times may be compared.
	 */
	private TransitTimeComparator()
	{
	}


	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(TransitTime t1, TransitTime t2)
	{
		return t1.compareTo(t2);
	}


	/**
	 * Retrieves a single instance of this class.
	 * @return A single instance of this class.
	 */
	public static TransitTimeComparator getInstance()
	{
		if (instance == null)
			instance = new TransitTimeComparator();

		return instance;
	}
}