/*
 * @(#)SmtpServer.java	1.0	05/12/09
 * @(#)SmtpServer.java	1.1	05/31/09
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
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;


/**
 * Responsible for sending messages to a list of addresses. Simple Mail Transfer Protocol (SMTP)
 * is an Internet standard for electronic mail (e-mail) transmission across Internet Protocol (IP)
 * networks. SMTP was first defined in RFC 821 (STD 10), and last updated by RFC 5321 (2008)
 * which describes extended SMTP (ESMTP), the protocol in widespread use today.<br>
 *<br>
 * While electronic mail server software uses SMTP to send and receive mail messages, user-level
 * client mail applications typically only use SMTP for sending messages to a mail server for
 * relaying. For receiving messages, client applications usually use either the Post Office
 * Protocol (POP) or the Internet Message Access Protocol (IMAP) to access their mail box
 * accounts on a mail server.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009-05-31 Modified connect() to deal with changes to ConnectionInfo. Added message breaking
 * features. Added debug control variable in constructor. "ok" variable support in case connection
 * was intentionally closed.
 */
public class SmtpServer
{
	/** The subject-index of the first message. */
	private static final int FIRST_MESSAGE_SUBJECT_INDEX = 0;

	/** Breaks messages into smaller ones if the content exceeds a limit. */
	private MessageBreaker breaker;

	/** Used to remember the server connection information in the event of the socket timing out. */
	private ConnectionInfo info;

	/** The session received from the host to allow us to send messages. */
	private Session mailSession;

	/** Is it OK to try to connect to the remote host (used in case service is terminated). */
	private boolean ok;

	/** The mechanism used to transfer data from local machine to remote machine. */
	private Transport transport;


	/**
	 * Creates a SMTP server using the connection information specified and sets up the channels
	 * required to send data to destinations.
	 * @param info The server connection information.
	 * @param debug Should debug information be shown on the console.
	 * @throws MessagingException If there is a problem connecting to the remote service.
	 */
	public SmtpServer(ConnectionInfo info, boolean debug) throws MessagingException
	{
		this.info = info;
		this.ok = true;

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put( "mail.smtps.host", info.getHost() );
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "true");

		this.mailSession = Session.getDefaultInstance(props);
		this.mailSession.setDebug(debug);
		this.breaker = new MessageBreaker(mailSession);

		this.transport = mailSession.getTransport();
		connect();
	}


	/**
	 * Closes the connection to the host as well as dereferencing pointers. Stops trying to connect
	 * to the host.
	 * @throws MessagingException If there are any problems ending the connection.
	 */
	public synchronized void close() throws MessagingException
	{
		transport.close();
		info = null;
		mailSession = null;
		breaker = null;
		this.ok = false;
	}


	/**
	 * Connects to the remote service that allows us to send messages.
	 * @throws MessagingException If there is a problem getting a connection from the host.
	 */
	private void connect() throws MessagingException
	{
		this.transport.connect( info.getHost(), info.getHostPort(), info.getUsername(), info.getPassword() );
	}


	/**
	 * Sends a message with the specified subject and body to the destinations specified in the message.
	 * @param m The message to send.
	 * @throws MessagingException If there is a problem obtaining a channel to send the required
	 * data to the remote service.
	 */
	private synchronized void sendMessage(Message m) throws MessagingException
	{
		try {
			transport.sendMessage( m, m.getRecipients(Message.RecipientType.TO) );
		}

		catch (MessagingException se)
		{
			if (ok)
			{
				// possibly the socket was closed by server
				connect();
				transport.sendMessage( m, m.getRecipients(Message.RecipientType.TO) );
			}
		}
	}


	/**
	 * Sends a message with the specified subject and body to the destinations specified. If the
	 * message needs to be broken, then that is done and each sub-message is sent one after the
	 * other.
	 * @param subject The subject of the message to be sent.
	 * @param body The actual content of the message to be sent.
	 * @param destinations The email addresses to send the message to.
	 * @throws MessagingException If there is a problem obtaining a channel to send the required
	 * data to the remote service.
	 */
	public void sendMessage(String subject, String body, Address[] destinations) throws MessagingException
	{
		if ( MessageBreaker.needsBreaking(body) )
		{
			for ( Message m: breaker.breakUp(subject, body, destinations) ) // for all the messages broken up
				sendMessage(m);
		}

		else
			sendMessage( breaker.createMessage(FIRST_MESSAGE_SUBJECT_INDEX, body, subject, destinations) );
	}
}