/*
 * @(#)SmsTransitServiceProvider.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model;

import com.canabang.genietext.core.model.SmsServiceProvider;
import com.canabang.genietext.core.model.structs.ConnectionInfo;
import com.canabang.genietext.transit.model.eventHandlers.TransitProcessorFactory;
import javax.mail.MessagingException;
import javax.xml.transform.TransformerConfigurationException;


/**
 * A transit SMS service that the system provides.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SmsTransitServiceProvider extends SmsServiceProvider
{
	/**
	 * Creates a transit SMS service with the specified properties.
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
	public SmsTransitServiceProvider(int clockFormat, ConnectionInfo imap, ConnectionInfo smtp, String subsOutputFile) throws MessagingException, TransformerConfigurationException
	{
		super(clockFormat, imap, smtp, subsOutputFile);

		this.generator = new TransitProcessorFactory(subscription, log, filter);
		this.generator.setServer( server.getSmtpServer() );
	}


	/* (non-Javadoc)
	 * @see model.SmsServiceProvider#run()
	 */
	public void run()
	{
		try
		{
			this.server.getImapServer().startMonitoring(generator);
		}

		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}