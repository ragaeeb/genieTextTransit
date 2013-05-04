/*
 * @(#)TransitRequestProcessor.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.processor;

import com.canabang.genietext.core.model.processor.RequestProcessor;
import com.canabang.genietext.core.model.server.SmtpServer;
import com.canabang.genietext.core.model.structs.AddressSet;
import java.io.IOException;
import java.text.ParseException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Processes a request sent for transit service data.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitRequestProcessor extends RequestProcessor
{
	/** Helps parse the complex syntax stored in the request message into actual request objects. */
	private TransitRequestParser parser;


	/**
	 * Creates a transit service request processor instance that will process the specified
	 * message and send a response through the specified SMTP server.
	 * @param received The message request to process.
	 * @param smtpServer The SMTP server that will be used to process the request.
	 * @param subscribers The set of subscribers associated with this service.
	 * @throws MessagingException If there are problems sending the response back.
	 * @throws IOException If there is a problem accessing the external resources to process
	 * the message request.
	 * @throws ParseException If the contents of the requests message are not formatted properly.
	 * @throws SAXException If there are any problems parsing the XML file associated with the transit service. 
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser. 
	 */
	public TransitRequestProcessor(MimeMessage received, SmtpServer smtpServer, AddressSet subscribers) throws MessagingException, IOException, ParseException, ParserConfigurationException, SAXException
	{
		super(received, smtpServer, subscribers);

		this.parser = new TransitRequestParser(received, subscribers);
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			String result = this.parser.getRequest().process();
			this.response.process("Transit", result);
		}

		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}