/*
 * @(#)SmsServiceProvider.java	1.0	05/31/09
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
package com.canabang.genietext.core.model;

import com.canabang.genietext.core.model.eventHandlers.ProcessorFactory;
import com.canabang.genietext.core.model.server.EmailServer;
import com.canabang.genietext.core.model.structs.AddressSet;
import com.canabang.genietext.core.model.structs.ConnectionInfo;
import com.canabang.genietext.core.model.structs.LogBook;
import com.canabang.genietext.core.model.structs.Time;
import javax.mail.MessagingException;
import javax.swing.DefaultListModel;
import javax.xml.transform.TransformerConfigurationException;


/**
 * A service that the system provides. Every service has a log that has a record of all work that
 * was done on it, a list of subscribers, an associated mail server, a message filter, and a
 * request processor generator.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public abstract class SmsServiceProvider implements Runnable
{
	/** A set of addresses that will be filtered and will not be processed. */
	protected AddressSet filter;

	/** Generates request processors. */
	protected ProcessorFactory generator;

	/** A record of all work that was done on the service. */
	protected DefaultListModel log;

	/** The mail server associated with this service. */
	protected EmailServer server;

	/** Subscribers of this service. */
	protected AddressSet subscription;


	/**
	 * Creates a service with the specified properties.
	 * @param clockFormat The format to display times in. Anything other than 24 assumes the
	 * default 12-hour format.
	 * @param imap Information required to connect to the IMAP mail server.
	 * @param smtp Information required to connect to the SMTP mail server.
	 * @param subsOutputFile Subscription output file path.
	 * @throws MessagingException If there is a problem connecting to the remote service or in the
	 * event that the server could not authenticate the connection or the required folder(s)
	 * could not be opened/accessed.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up to output the subscription list.
	 */
	public SmsServiceProvider(int clockFormat, ConnectionInfo imap, ConnectionInfo smtp, String subsOutputFile) throws MessagingException, TransformerConfigurationException
	{
		super();

		if (clockFormat == Time.MILITARY_CLOCK_FORMAT)
			Time.setMilitaryFormat();

		else
			Time.setDefaultFormat();

		this.server = new EmailServer(imap, smtp);
		this.subscription =  new AddressSet(subsOutputFile);
		this.log = new LogBook();
		this.populateFilter();
	}


	/**
	 * Retrieves the request processor generator for this service.
	 * @return The processor generator for this service.
	 */
	public ProcessorFactory getGenerator()
	{
		return generator;
	}


	/**
	 * Retrieves the log for this service.
	 * @return The log storing all work done on this service.
	 */
	public DefaultListModel getLog()
	{
		return log;
	}


	/**
	 * Retrieves the mail server associated with this service.
	 * @return The mail server this service uses to receive requests and send responses.
	 */
	public EmailServer getServer()
	{
		return server;
	}


	/**
	 * Retrieves the list of subscribers associated with this service.
	 * @return The subscribers that signed up for this service.
	 */
	public DefaultListModel getSubscribers()
	{
		return subscription;
	}


	/**
	 * Populates the filter with a list of addresses to block from being processed.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up to output the subscription list.
	 */
	private void populateFilter() throws TransformerConfigurationException
	{
		this.filter = new AddressSet("./filtered.xml");
		this.filter.addElement("postmaster");
		this.filter.addElement("uucp");
		this.filter.addElement("mailer-daemon");
		this.filter.addElement("maildaemon");
		this.filter.addElement("majordomo");
		this.filter.addElement("mailerdaemon");
		this.filter.addElement("abuse@");
		this.filter.addElement("-relay");
		this.filter.addElement("-request@");
	}


	/**
	 * Stops this service and runs the JVM garbage collection.
	 * @throws MessagingException If there is a problem shutting down the mail server.
	 */
	public void stop() throws MessagingException
	{
		this.server.close();
		this.generator = null;
		this.log = null;
		this.subscription = null;

		System.gc();
	}
}