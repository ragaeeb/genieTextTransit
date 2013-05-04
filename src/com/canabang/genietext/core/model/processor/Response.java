/*
 * @(#)Response.java	1.0	05/12/09
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
import javax.mail.Address;
import javax.mail.MessagingException;


/**
 * The response to a request that will be sent back to the source.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 */
public class Response
{
	/** The methodology used to send this response. */
	private SmtpServer smtpServer;

	/** The addresses that he response should be sent to. */
	private Address[] destinations;


	/**
	 * Sets up a response to be sent to the specified destination addresses by using the specified
	 * server.
	 * @param smtpServer The protocol used to send this response.
	 * @param destinations The email addresses that this response will be sent to.
	 */
	public Response(SmtpServer smtpServer, Address[] destinations)
	{
		this.smtpServer = smtpServer;
		this.destinations = destinations;
	}


	/**
	 * Sends this response to its recipients with the specified subject and body content.
	 * @param subject The subject of the message to send.
	 * @param result The body text that should be sent in the message.
	 * @throws MessagingException If there are any problems sending the message through the
	 * channel.
	 */
	public void process(String subject, String result) throws MessagingException
	{
		smtpServer.sendMessage(subject, result, destinations);
	}
}