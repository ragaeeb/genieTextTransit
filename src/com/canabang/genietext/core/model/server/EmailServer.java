/*
 * @(#)EmailServer.java	1.0	05/12/09
 * @(#)EmailServer.java	1.1	05/31/09
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
package com.canabang.genietext.core.model.server;

import com.canabang.genietext.core.model.structs.ConnectionInfo;
import javax.mail.MessagingException;


/**
 * The email server used for retrieving messages from a host, and also sending messages.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009=05-31 No longer needs to know about ConnectionInfo. Now this class only holds
 * the IMAP server and the SMTP server and nothing else. Added debug control variable.
 */
public class EmailServer
{
	/** Should we display debug information for server connections. */
	public static final boolean DEBUG = false;

	/** Retrieves messages from a host. */
	private ImapServer imapServer;

	/** Used to send messages to addresses. */
	private SmtpServer smtpServer;


	/**
	 * Initiates an email server connection with the specified host information.
	 * @param imap The connection information needed to connect to the IMAP server.
	 * @param smtp The connection information needed to connect to the SMTP server.
	 * @throws MessagingException If there is a problem connecting to the remote service or in the
	 * event that the server could not authenticate the connection or the required folder(s)
	 * could not be opened/accessed.
	 */
	public EmailServer(ConnectionInfo imap, ConnectionInfo smtp) throws MessagingException
	{
		smtpServer = new SmtpServer(smtp, DEBUG);
		imapServer = new ImapServer(imap, DEBUG);
	}


	/**
	 * Closes the socket connections to the mail servers and deferences them.
	 * @throws MessagingException If there are any problems closing the connection.
	 */
	public void close() throws MessagingException
	{
		imapServer.close();
		smtpServer.close();
		imapServer = null;
		smtpServer = null;
	}


	/**
	 * Retrieves the IMAP server.
	 * @return The server used to receive emails.
	 */
	public ImapServer getImapServer()
	{
		return imapServer;
	}


	/**
	 * Retrieves the SMTP server.
	 * @return The server used to send emails.
	 */
	public SmtpServer getSmtpServer()
	{
		return smtpServer;
	}
}