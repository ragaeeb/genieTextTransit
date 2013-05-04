/*
 * @(#)LogBook.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.structs;

import java.sql.Timestamp;
import javax.swing.DefaultListModel;


/**
 * A log storing recorded actions for a service.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class LogBook extends DefaultListModel
{
	/** The header message for any log. */
	private static final String HEADER = "Service started";

	/** Separates the time-stamp from the textual value associated with it. */
	private static final String SEPARATOR = ": ";

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates an empty log and appends the header message to it.
	 */
	public LogBook()
	{
		super();
		this.addElement(HEADER);
	}


	/* (non-Javadoc)
	 * @see javax.swing.DefaultListModel#addElement(java.lang.Object)
	 */
	public void addElement(Object entry)
	{
		Timestamp t = new Timestamp( System.currentTimeMillis() );

		super.addElement( t.toString()+SEPARATOR+entry.toString() );
	}
}