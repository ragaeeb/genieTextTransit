/*
 * @(#)ServerInfoPanel.java	1.0	05/31/09
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
package com.canabang.genietext.core.view.panels.parameters;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;


/**
 * Holds server connection information including the incoming and outgoing mail servers.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ServerInfoPanel extends JPanel
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Stores IMAP server information. */
	private ImapServerPanel imap;

	/** Stores SMTP server information. */
	private SmtpServerPanel smtp;


	/**
	 * Creates a server information panel to use the specified dimensions to position its elements.
	 * @param d The dimensions used to position the elements.
	 */
	public ServerInfoPanel(Dimension d)
	{
		super();

		this.setOpaque(false);

		this.setSize( d.width, d.height/33 );
		this.setLayout( new GridLayout(1,2) );

		this.imap = new ImapServerPanel();
		this.smtp = new SmtpServerPanel();

		this.add(imap);
		this.add(smtp);
	}


	/**
	 * Retrieves the IMAP server panel.
	 * @return The panel containing IMAP server information.
	 */
	public ImapServerPanel getImap()
	{
		return imap;
	}


	/**
	 * Retrieves the SMTP server panel.
	 * @return The panel containing SMTP server information.
	 */
	public SmtpServerPanel getSmtp()
	{
		return smtp;
	}
}