/*
 * @(#)ProcessorFactory.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.eventHandlers;

import com.canabang.genietext.core.model.processor.RequestProcessor;
import com.canabang.genietext.core.model.server.SmtpServer;
import com.canabang.genietext.core.model.structs.AddressSet;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Generates the worker threads responsible for processing the mail request.
 * 
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009-05-31 Moved SMTP server parameter from constructor and moved it to a set()
 * method so that a SMTP server does not have to be specified during construction. Also renamed
 * the class from ProcessorGenerator to ProcessorFactory. Added address filter.
 */
public abstract class ProcessorFactory extends MessageCountAdapter
{
	/** The body-type of the message sent by the user we can parse. */
	public static final String EXPECTED_BODY_TYPE = "text/plain";

	/** Displayed before the address of a filtered message. */
	private static final String FILTERED_MESSAGE_PREFIX = "Blocked potential spam from: ";

	/** A set of addresses that will be filtered and will not be processed. */
	private AddressSet filter;

	/** The log that will be used to log incoming requests. */
	private DefaultListModel log;

	/** The pool of threads that are performing requests. */
	private ExecutorService pool;

	/** The worker that will process the request specified in the email. */
	protected RequestProcessor processor;

	/** Server used to send the response to the user. */
	protected SmtpServer server;

	/** The sub service. */
	protected AddressSet subService;


	/**
	 * Creates an instance of the generator that will create the worker threads necessary to
	 * process the requests specified in the emails.
	 * @param subService The subscription service.
	 * @param log Used to log incoming requests.
	 * @param filter The set of addresses that will be filtered and not processed.
	 */
	public ProcessorFactory(AddressSet subService, DefaultListModel log, AddressSet filter)
	{
		this.pool = Executors.newCachedThreadPool();
		this.subService = subService;
		this.log = log;
		this.filter = filter;
	}


	/**
	 * Copies the specified message (for offline use) and gives to to the processor to process.
	 * @param m The message containing the request.
	 * @param addressValues The list of address the message was sent from as a String.
	 * @throws MessagingException If there is a problem sending the response message.
	 * @throws IOException If there is a problem accessing the external resources to process
	 * the message request.
	 * @throws ParseException If the contents of the requests message are not formatted properly.
	 * @throws SAXException If there are any problems writing to the output XML file for subscriptions.
	 * @throws ParserConfigurationException If there is a problem setting up a XML parser.
	 */
	private void copyAndProcessMessage(Message m, String addressValues) throws MessagingException, IOException, ParseException, ParserConfigurationException, SAXException
	{
		MimeMessage copy; // a copy is needed since a constant connection is needed to server directly to message in order to work with it

		if ( m.isMimeType(EXPECTED_BODY_TYPE) )
		{
			log.addElement( getLogRequestSignature(m, addressValues) );
			copy = new MimeMessage( (MimeMessage)m );
			pool.execute( this.generateProcessor(copy) );
		}

		m.setFlag(Flags.Flag.DELETED, true); // delete the message, no need to save it
		//m.setFlag(Flags.Flag.SEEN, true);
	}


	/**
	 * All subclasses must implement this to specify the processor that will handle the
	 * message request.
	 * @param m The message that needs to be processed by the worker threads (must be a copy).
	 * @return The processor that will handle the request message.
	 * @throws MessagingException If there is a problem sending the response message.
	 * @throws IOException If there is a problem accessing the external resources to process
	 * the message request.
	 * @throws ParseException If the contents of the requests message are not formatted properly.
	 * @throws SAXException If there are any problems writing to the output XML file for subscriptions.
	 * @throws ParserConfigurationException If there is a problem setting up a XML parser.
	 */
	public abstract RequestProcessor generateProcessor(MimeMessage m) throws MessagingException, IOException, ParseException, ParserConfigurationException, SAXException;


	/* (non-Javadoc)
	 * @see javax.mail.event.MessageCountAdapter#messagesAdded(javax.mail.event.MessageCountEvent)
	 */
	public void messagesAdded(MessageCountEvent e)
	{
		try {
			Message[] messages = e.getMessages(); // new messages

			for (Message m: messages)
			{
				Address[] from = m.getFrom();
				boolean potentialBlocked = filter.partialMatch(from);
				String addressValues = getAddressValues(from);

				if (!potentialBlocked)
					copyAndProcessMessage(m, addressValues);

				else
					log.addElement(FILTERED_MESSAGE_PREFIX+addressValues);
			}
		}

		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}


	/**
	 * Sets the SMTP server to the specified one.
	 * @param server The SMTP server that will be used to send the response back.
	 */
	public void setServer(SmtpServer server)
	{
		this.server = server;
	}


	/**
	 * Stops the creation of any more request processors and shuts down any currently executing ones.
	 */
	public void stop()
	{
		this.pool.shutdownNow();
	}


	/**
	 * Assembles the list of addresses specified as a string separated by a comma.
	 * @param addresses The addresses to assemble and separate by commas.
	 * @return The addresses specified separated by commas (ie: arg1, arg2, arg3, arg4).
	 */
	private static final String getAddressValues(Address[] addresses)
	{
		String result = new String();

		for (int i = 0; i < addresses.length; i++)
		{
			result += addresses[i].toString();

			if (i != addresses.length-1)
				result += ", ";
		}

		return result;
	}


	/**
	 * Gets the log request signature.
	 * @param m The message containing the request.
	 * @param addressValues The list of address the message was sent from as a String. 
	 * @return the log request signature
	 * @throws MessagingException If there is a problem sending the response message.
	 * @throws IOException If there is a problem accessing the external resources to process
	 * the message request.
	 */
	private static final String getLogRequestSignature(Message m, String addressValues) throws MessagingException, IOException
	{
		return "Received new request that was sent at: "+m.getSentDate().toString()+", from: "+addressValues+" with contents: "+(String)m.getContent();
	}
}