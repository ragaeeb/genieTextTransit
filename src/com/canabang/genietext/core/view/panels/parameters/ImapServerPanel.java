/*
 * @(#)ImapServerPanel.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.structs.ConnectionInfo;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;


/**
 * Contains fields that can be accessed and manipulated to connect to the IMAP server.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ImapServerPanel extends JPanel
{
	/** The default host to display. */
	private static final String DEFAULT_HOST = "imap.host.com";

	/** The default password to display. */
	private static final String DEFAULT_PASSWORD = "password";

	/** The title of this panel. */
	private static final String DEFAULT_TITLE = "IMAP Server:";

	/** The default username to display. */
	private static final String DFEAULT_USERNAME = "username";

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** The host address required to connect to the IMAP server. */
	protected JTextComponent host;

	/** The password required to connect to the IMAP server. */
	protected JTextComponent password;

	/** Title of these set of fields. */
	protected final JLabel title;

	/** The username required to connect to the IMAP server. */
	protected JTextComponent username;


	/**
	 * Creates a panel so that IMAP server information can be viewed and manipulated.
	 */
	public ImapServerPanel()
	{
		super();

		this.setOpaque(false);
		this.setLayout( new GridLayout(5,1) );

		title = new JLabel(DEFAULT_TITLE);
		username = new FixedTextField(DFEAULT_USERNAME);
		password = new FixedPasswordField(DEFAULT_PASSWORD);
		host = new FixedTextField(DEFAULT_HOST);

		add(title);
		add(username);
		add(password);
		add(host);
	}


	/**
	 * Gets the values of the fields contained within this panel.
	 * @return The connection information values of the fields contained within this panel.
	 */
	public ConnectionInfo getFields()
	{
		return new ConnectionInfo( username.getText(), password.getText(), host.getText() );
	}


	/**
	 * Sets the values of the fields contained within this panel to match the ones specified.
	 * @param c The actual value that these fields should be equal to.
	 */
	public void setFields(ConnectionInfo c)
	{
		username.setText( c.getUsername() );
		password.setText( c.getPassword() );
		host.setText( c.getHost() );
	}
}