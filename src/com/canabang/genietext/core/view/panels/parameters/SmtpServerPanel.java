/*
 * @(#)SmtpServerPanel.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.io.xml.XmlProperties;
import com.canabang.genietext.core.model.structs.ConnectionInfo;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.JTextComponent;


/**
 * Contains fields that can be accessed and manipulated to connect to the SMTP server.
 * 
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SmtpServerPanel extends ImapServerPanel
{
	/** The default host to display. */
	private static final String DEFAULT_HOST = "smtp.host.com";

	/** The default port number to display. */
	private static final String DEFAULT_PORT = "465";

	/** The title of this panel. */
	private static final String DEFAULT_TITLE = "SMTP Server:";

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** The port required to connect to the SMTP server. */
	protected JTextComponent port;


	/**
	 * Creates a panel so that IMAP server information can be viewed and manipulated.
	 */
	public SmtpServerPanel()
	{
		super();

		this.setOpaque(false);

		this.port = new JFormattedTextField(  NumberFormat.getIntegerInstance()  );
		this.port.setOpaque(false);
		this.port.setText(DEFAULT_PORT);
		this.host.setText(DEFAULT_HOST);
		this.title.setText(DEFAULT_TITLE);

		add(port);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.panels.parameters.ImapServerPanel#getFields()
	 */
	public ConnectionInfo getFields()
	{
		ConnectionInfo c = super.getFields();
		c.setHostPort( Integer.parseInt( port.getText() ) );
		return c;
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.panels.parameters.ImapServerPanel#setFields(com.canabang.genietext.core.model.structs.ConnectionInfo)
	 */
	public void setFields(ConnectionInfo c)
	{
		super.setFields(c);
		this.port.setText( XmlProperties.BLANK+c.getHostPort() );
	}
}