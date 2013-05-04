/*
 * @(#)Parameters.java	1.0	05/31/09
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


/**
 * Parameters required to start a service.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class Parameters
{
	/** The desired time format for the service. */
	private int clockFormat;

	/** The connection information required to connect to the IMAP server. */
	private ConnectionInfo imap;

	/** The connection information required to connect to the SMTP server. */
	private ConnectionInfo smtp;

	/** The path to output the subscription data for the service. */
	private String subscriptionOutput;


	/**
	 * Creates an empty set of parameters.
	 */
	public Parameters()
	{
	}


	/**
	 * Retrieves the desired time format for a service. 
	 * @return The format to display time information for a service.
	 */
	public int getClockFormat()
	{
		return clockFormat;
	}


	/**
	 * Retrieves the IMAP server information.
	 * @return The information required to connect to the IMAP server.
	 */
	public ConnectionInfo getImap()
	{
		return imap;
	}


	/**
	 * Retrieves the SMTP server information.
	 * @return The information required to connect to the SMTP server.
	 */
	public ConnectionInfo getSmtp()
	{
		return smtp;
	}


	/**
	 * Retrieves the path to output the subscription data for the service.
	 * @return The path where the subscription data should be submitted.
	 */
	public String getSubscriptionOutput()
	{
		return subscriptionOutput;
	}


	/**
	 * Sets the clockFormat to the specified value.
	 * @param clockFormat The time format (ie: 24 means 24-hour clock, 12 means 12-hour clock).
	 */
	public void setClockFormat(int clockFormat)
	{
		this.clockFormat = clockFormat;
	}


	/**
	 * Sets the IMAP server information to the specified value.
	 * @param imap The IMAP server information.
	 */
	public void setImap(ConnectionInfo imap)
	{
		this.imap = imap;
	}


	/**
	 * Sets the SMTP server information to the specified value.
	 * @param smtp The SMTP server information.
	 */
	public void setSmtp(ConnectionInfo smtp)
	{
		this.smtp = smtp;
	}


	/**
	 * Sets the subscription output path to the specified value.
	 * @param subscriptionOutput The path of the subscription output.
	 */
	public void setSubscriptionOutput(String subscriptionOutput)
	{
		this.subscriptionOutput = subscriptionOutput;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "imap:\n"+imap.toString()
		+"\n\nsmtp:\n"+smtp.toString()
		+"\n\nclock: "+clockFormat
		+"\nsubscription output file: "+subscriptionOutput;
	}
}