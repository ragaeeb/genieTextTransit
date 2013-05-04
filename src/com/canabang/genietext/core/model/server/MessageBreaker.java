/*
 * @(#)MessageBreaker.java	1.0	05/31/09
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

import java.util.ArrayList;
import java.util.List;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;


/**
 * Breaks a message into multiple submessages if the total length of the message exceeds a fixed
 * amount.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class MessageBreaker
{
	/** The maximum number of characters allowed in the message. */
	public static final int MAXIMUM_CHARACTERS = 160;

	/** The format of the body of the message being sent. */
	public static final String MESSAGE_TYPE = "text/plain";

	/** The session received from the host to allow us to send messages. */
	private Session mailSession;


	/**
	 * Creates an instance of this class to allow for message size restriction capabilities.
	 * @param mailSession The session received from the host to allow us to send messages.
	 */
	public MessageBreaker(Session mailSession)
	{
		super();
		this.mailSession = mailSession;
	}


	/**
	 * Breaks up the specified message if necessary into a list of several messages as required.
	 * @param subject The subject of all the messages (will append the index of the message being
	 * sent).
	 * @param message The actual message being sent that will be checked for size.
	 * @return A list of submessages containing parts of the data in the original message.
	 * @throws MessagingException If there is a problem obtaining the data from the message. 
	 */
	public List<Message> breakUp(String subject, String message, Address[] destinations) throws MessagingException
	{
		int i = 0;
		List<Message> messages = new ArrayList<Message>();

		while ( needsBreaking(message) )
		{
			String segment = message.substring(0, MAXIMUM_CHARACTERS);
			Message m = createMessage(i, segment, subject, destinations);
			messages.add(m);

			message = message.substring(MAXIMUM_CHARACTERS);

			i++;
		}

		if ( !message.isEmpty() )
		{
			i++;
			Message m = createMessage(i, message, subject, destinations);
			messages.add(m);
		}

		return messages;
	}


	/**
	 * Creates a message with the specified properties.
	 * @param index The index of the sub-message to append to the subject.
	 * @param segment The message segment to store in the body.
	 * @param subject The subject of all the sub-messages.
	 * @param destinations The destinations to send the message to.
	 * @return The message object created with the specified properties.
	 * @throws MessagingException If there is a problem obtaining the data from the message or
	 * creating it.
	 */
	public Message createMessage(int index, String segment, String subject, Address[] destinations) throws MessagingException
	{
		MimeMessage m = new MimeMessage(mailSession);
		m.setSubject(subject+index);
		m.setContent(segment, MESSAGE_TYPE);
		m.addRecipients(Message.RecipientType.TO, destinations);
		m.saveChanges();

		return m;
	}


	/**
	 * Determines if the specified message needs to be broken or not.
	 * @param message The message to check if it exceeds the maximum amount of characters allowed
	 * per message.
	 * @return true if the message's length exceeds the maximum amount of characters allowed,
	 * false otherwise.
	 */
	public static final boolean needsBreaking(String message)
	{
		return message.length() > MAXIMUM_CHARACTERS;
	}
}