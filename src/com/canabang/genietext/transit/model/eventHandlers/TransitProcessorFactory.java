/*
 * @(#)TransitProcessorFactory.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.eventHandlers;

import com.canabang.genietext.core.model.eventHandlers.ProcessorFactory;
import com.canabang.genietext.core.model.processor.RequestProcessor;
import com.canabang.genietext.core.model.structs.AddressSet;
import com.canabang.genietext.transit.model.processor.TransitRequestProcessor;
import java.io.IOException;
import java.text.ParseException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Generates the worker threads responsible for processing the transit service time mail requests.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009-05-31 Renamed to TransitProcessorFactory from PrayerProcessorGenerator. Modified
 * constructor so that a SMTP server does not need to be specified during construction.
 */
public class TransitProcessorFactory extends ProcessorFactory
{
	/**
	 * Creates an instance of the generator that will create the worker threads necessary to
	 * process the transit service requests specified in the emails.
	 * @param subService The subscription service.
	 * @param log Used to log incoming requests.
	 * @param filter The set of addresses that will be filtered and not processed.
	 */
	public TransitProcessorFactory(AddressSet subService, DefaultListModel log, AddressSet filter)
	{
		super(subService, log, filter);
	}


	/* (non-Javadoc)
	 * @see model.eventHandlers.ProcessorGenerator#generateProcessor(javax.mail.internet.MimeMessage)
	 */
	public RequestProcessor generateProcessor(MimeMessage m) throws MessagingException,	IOException, ParseException, NumberFormatException, ParserConfigurationException, SAXException
	{
		return new TransitRequestProcessor( m, server, subService );
	}
}