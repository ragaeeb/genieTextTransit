/*
 * @(#)MainPanelActionListener.java	1.0	05/31/09
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
import com.canabang.genietext.core.model.structs.Parameters;
import com.canabang.genietext.core.view.SmsServicesView;
import com.canabang.genietext.core.view.panels.parameters.ParametersPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;


/**
 * Reacts to buttons pushed in the primary panel.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class MainPanelActionListener implements ActionListener
{
	/** The primary panel containing the buttons we are monitoring. */
	private MainPanel m;

	/** Contains the information required to start the service. */
	private ParametersPanel p;

	/** The actual service to start/terminate. */
	private SmsServiceProvider service;

	/** The view portion of the MVC pattern to reflect model's changes to. */
	private SmsServicesView view;


	/**
	 * Creates an instance of this class to listen to the primary panel's buttons.
	 * @param m The primary panel containing the buttons we are monitoring.
	 * @param p The panel containing the parameters to extract to start the service.
	 * @param view The view portion of the MVC pattern to reflect model's changes to.
	 */
	public MainPanelActionListener(MainPanel m, ParametersPanel p, SmsServicesView view)
	{
		this.m = m;
		this.p = p;
		this.view = view;
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object j = e.getSource();

		if ( j.equals( m.getStart() ) )
			startServicePressed();

		else if ( j.equals( m.getStop() ) )
			stopServicePressed();
	}


	/**
	 * Displays the appropriate error message if connection fails.
	 * @param ex The exception associated with the error.
	 */
	private void showErrorMessage(Exception ex)
	{
		String message;

		if (ex instanceof AuthenticationFailedException)
			message = "Username/password combination invalid.";

		else if (ex instanceof MessagingException)
			message = "Could not connect, please recheck connection information";

		else
			message = ex.getMessage();

		JOptionPane.showMessageDialog(m, message);
		view.showParametersPanel();
	}


	/**
	 * Actions to perform when the start service button was pressed.
	 */
	private void startServicePressed()
	{
		Parameters params = p.getValues();

		if ( params == null )
			view.showParametersPanel();

		else
		{
			try {
				service = view.startService(params);
				m.getStart().setEnabled(false);
				m.getStop().setEnabled(true);
				m.active();
			}

			catch (Exception ex)
			{
				showErrorMessage(ex);
			}
		}
	}


	/**
	 * Actions to perform when the stop service button was pressed.
	 */
	private void stopServicePressed()
	{
		boolean startEnabled = !m.getStart().isEnabled();
		boolean serviceObtained = service != null;

		if (startEnabled && serviceObtained)
		{
			try {
				view.stopService();
				service.stop();

				m.getStart().setEnabled(true);
				m.getStop().setEnabled(false);
				m.inactive();
			}

			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(m, "Could not terminate connection");
			}
		}
	}
}