/*
 * @(#)RequestProcessor.java	1.0	05/12/09
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
package com.canabang.genietext.core.model.processor;

import com.canabang.genietext.core.model.server.SmtpServer;
import com.canabang.genietext.core.model.structs.AddressSet;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * Processes an email request sent.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 */
public abstract class RequestProcessor implements Runnable
{
	/** The processed respond that will be sent back. */
	protected Response response;


	/**
	 * Creates a request processor instance that will process the specified message and send a
	 * response through the specified SMTP server.
	 * @param received The message request to process.
	 * @param smtpServer The SMTP server that will be used to process the request.
	 * @param subscription The list of subscribers to the service we are processing a request for.
	 * @throws MessagingException If there are problems sending the response back.
	 */
	public RequestProcessor(MimeMessage received, SmtpServer smtpServer, AddressSet subscription) throws MessagingException
	{
		this.response = new Response( smtpServer, received.getFrom() );
	}
}