/*
 * @(#)SubscribersPanel.java	1.0	05/31/09
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
package com.canabang.genietext.core.view.panels;

import com.canabang.genietext.core.model.SmsServiceProvider;
import com.canabang.genietext.core.view.structs.WrappingList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;


/**
 * Displays the current list of subscribers to the user. An event message can be sent to the
 * users in the list.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SubscribersPanel extends BackgroundImageJPanel implements ActionListener
{
	/** Text value for the send button. */
	private static final String SEND_BUTTON_TEXT = "Send";

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Allows a message event to be sent to subscribers. */
	private AbstractButton sendButton;

	/** The actual service containing subscription data. */
	private SmsServiceProvider service;

	/** The list of subscribers of the service that can be displayed to the user. */
	private JList subscribersList;

	/** Allows for text-input to be sent to subscribers as an event. */
	private JTextArea textArea;


	/**
	 * Creates a subscriber panel to use the specified dimensions to position its elements.
	 * @param dimensions The dimensions used to position the elements.
	 */
	public SubscribersPanel(Dimension dimensions)
	{
		super();

		this.setLayout( new GridLayout(2,1) );

		this.subscribersList = new WrappingList();
		this.subscribersList.setSize(dimensions);
		this.subscribersList.setPreferredSize(dimensions);
		this.subscribersList.setLayoutOrientation(JList.VERTICAL);
		subscribersList.setOpaque(false);

		JScrollPane scrollPane = new JScrollPane(subscribersList);
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel bottom = new JPanel( new GridBagLayout() );
		bottom.setOpaque(false);
		this.add(bottom);

		textArea = new JTextArea(15, 35);
		textArea.setOpaque(true);
		bottom.add(textArea);
		bottom.add(new JPanel());
		textArea.setBorder( BorderFactory.createLineBorder(Color.GRAY, 2) );
		textArea.setSize(400, 200);

		sendButton = new JButton(SEND_BUTTON_TEXT);
		bottom.add(sendButton);

		sendButton.addActionListener(this);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object j = e.getSource();

		if ( j.equals(sendButton) )
		{
			if (service == null)
				JOptionPane.showMessageDialog(this, "A service needs to be active to send a message");

			else
			{
				String message = textArea.getText();
				ListModel d = subscribersList.getModel();
				int size = d.getSize();

				if (size <= 0)
					JOptionPane.showMessageDialog(null, "There are no subscribers to send a message to");

				else
					sendMessage(message, d, size);
			}
		}
	}


	/**
	 * Sends the specified message to the addresses contained within the specified list model.
	 * @param message The message to send to the subscribers.
	 * @param d The subscribers list that will receive the message sent.
	 * @param size The size of the list model.
	 */
	private void sendMessage(String message, ListModel d, int size)
	{
		Address[] destinations = new InternetAddress[size];

		try
		{
			service.getLog().addElement( "Sending: \""+message+"\" to: "+convert(d, destinations) );
			service.getServer().getSmtpServer().sendMessage("genieText Event", message, destinations);
		}

		catch (MessagingException ex)
		{
			JOptionPane.showMessageDialog(this, "Problem sending message: "+ex.getMessage() );
		}
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#setConsoleLog(javax.swing.DefaultListModel)
	 */
	public void setList(DefaultListModel log)
	{
		subscribersList.setModel(log); 
	}


	/**
	 * Sets the service containing the subscription data to the specified one.
	 * @param service The service containing the subscription information.
	 */
	public void setService(SmsServiceProvider service)
	{
		this.service = service;
	}


	/**
	 * Converts the textual addresses specified in the list into actual address objects and stores
	 * them in the array.
	 * @param d The list of subscribers of the service.
	 * @param destinations The array that will store the actual address values.
	 * @return The value of addresses contained within the subscribers list separated by a comma (ie:
	 * address1@domain.com, address2@domain.com, etc.).
	 * @throws AddressException If a wrongly formatted address is encountered.
	 */
	private static final String convert(ListModel d, Address[] destinations) throws AddressException
	{
		String result = new String();

		for (int i = 0; i < d.getSize(); i++)
		{
			result += d.getElementAt(i);
			destinations[i] = new InternetAddress( (String)d.getElementAt(i) );

			if ( i != d.getSize()-1 )
				result += ", ";
		}

		return result;
	}
}