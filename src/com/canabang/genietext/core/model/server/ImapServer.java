/*
 * @(#)ImapServer.java	1.0	05/12/09
 * @(#)ImapServer.java	1.1	05/31/09
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
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;


/**
 * Represents a IMAP server that can be accessed to retrieve messages. The Internet Message
 * Access Protocol or IMAP is one of the two most prevalent Internet standard protocols for
 * e-mail retrieval, the other being POP3. Virtually all modern e-mail clients and servers
 * support both protocols as a means of transferring e-mail messages from a server, such as
 * those used by Gmail, to a client, such as Mozilla Thunderbird, Apple Mail and Microsoft
 * Outlook. Once configured, the client's use of such protocols remains transparent to the user.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009-05-31 Added constructor debug, renamed openAndMonitor to startMonitoring() and made
 * the method public. Updated for changes to the ConnectionInfo class.
 */
public class ImapServer
{
	/** The amount of time in milliseconds to delay before checking for new messages. */
	public static final long IDLE_TIME = 2000;

	/** The store name on the IMAP host that contains all the messages. */
	private static final String IMAP_STORE_NAME = "imaps";

	/** The main folder where all the email requests are stored and are retrieved from. */
	private static final String MAIL_FOLDER_NAME = "INBOX";

	/** The store holding all the messages. */
	private Store imapStore;

	/** Reference to the folder that will constantly be monitored for messages. */
	private Folder inbox;

	/** Should we keep monitoring inbox and keep trying to connect to it. */
	private boolean monitor;


	/**
	 * Creates an IMAP server using the connection information specified to be processed by the
	 * processor specified upon new requests that arrive at its default folder.
	 * @param info Information required to connect to the IMAP server.
	 * @param debug Should debug information be shown.
	 * @throws MessagingException In the event that the server could not authenticate the
	 * connection or the required folder(s) could not be opened/accessed.
	 */
	public ImapServer(ConnectionInfo info, boolean debug) throws MessagingException
	{
		Properties props = System.getProperties();

		// Get a Session object
		Session session = Session.getInstance(props, null);
		session.setDebug(debug);

		imapStore = session.getStore(IMAP_STORE_NAME);
		imapStore.connect( info.getHost(), info.getUsername(), info.getPassword() );
	}


	/**
	 * Closes the folder and store that the server is listening to in the host and ends the
	 * connection to the server as well as dereferencing pointers. Stops monitoring the server.
	 * @throws MessagingException If there are any problems ending the connection.
	 * @see javax.mail.Service#close()
	 */
	public synchronized void close() throws MessagingException
	{
		monitor = false;
		inbox.close(false);
		imapStore.close();
		inbox = null;
		imapStore = null;
	}


	/**
	 * Monitors the default folder for new email requests.
	 * @throws MessagingException If the folder being checked does not respond or is closed and
	 * thus access is not allowed.
	 * @throws InterruptedException If this thread is interrupted while delaying before the next
	 * check for new messages.
	 */
	private void monitorInbox() throws MessagingException, InterruptedException
	{
		while (monitor)
		{
			Thread.sleep(IDLE_TIME); // sleep for freq milliseconds

			if (inbox != null) // This is to force the IMAP server to send us EXISTS notifications. 
				inbox.getMessageCount();
		}
	}


	/**
	 * Opens the default folder where the mail is stored and monitors it for new messages that
	 * have not been processed.
	 * @param processor Responsible for reacting to new message request events.
	 * @throws MessagingException If the folder being checked does not respond or is closed and
	 * thus access is not allowed.
	 * @throws InterruptedException If this thread is interrupted while delaying before the next
	 * check for new messages.
	 */
	public void startMonitoring(MessageCountAdapter processor) throws MessagingException, InterruptedException
	{
		this.monitor = true;
		inbox = imapStore.getFolder(MAIL_FOLDER_NAME);

		try {
			inbox.open(Folder.READ_WRITE);
		}

		catch (IllegalStateException ex) // if the store is not connected
		{
			ex.printStackTrace();
		}

		inbox.addMessageCountListener(processor);
		monitorInbox();
	}
}