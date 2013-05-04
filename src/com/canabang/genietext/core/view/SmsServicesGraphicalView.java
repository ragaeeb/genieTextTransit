/*
 * @(#)SmsServicesGraphicalView.java	1.0	05/31/09
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
package com.canabang.genietext.core.view;

import com.canabang.genietext.core.controller.Controller;
import com.canabang.genietext.core.model.SmsServiceProvider;
import com.canabang.genietext.core.model.io.ResourceManager;
import com.canabang.genietext.core.model.structs.Parameters;
import com.canabang.genietext.core.view.panels.AboutFrame;
import com.canabang.genietext.core.view.panels.ConsolePanel;
import com.canabang.genietext.core.view.panels.MainPanel;
import com.canabang.genietext.core.view.panels.SubscribersPanel;
import com.canabang.genietext.core.view.panels.parameters.ParametersPanel;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.mail.MessagingException;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.xml.transform.TransformerConfigurationException;


/**
 * Represents the View component of the MVC pattern on a system allowing swing components to
 * make up the GUI.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SmsServicesGraphicalView extends JFrame implements SmsServicesView
{
	/** The fixed dimensions of this frame. */
	private static final Dimension DIMENSIONS = new Dimension(600, 600);

	/** The icon to display for this window. */
	public static Image ICON = ResourceManager.loadImage("icon.png");

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** The name of this service suite. */
	private static final String SERVICE_SUITE_NAME = "genieText: ";

	/** The console where to display the log. */
	private ConsolePanel consolePanel;

	/** The primary panel that will contain the controls to start and stop the service. */
	private JPanel mainPanel;

	/** Users will be allowed to modify the remote service's connection information here. */
	private ParametersPanel parametersPanel;

	/** The subscribers subscribed to the service will be shown here. */
	private SubscribersPanel subscribersPanel;

	/** Listens to this window for events. */
	private GraphicalViewWindowListener w;


	/**
	 * Creates a window frame to allow users to manipulate and view a service in the system.
	 * @param title The title of this service.
	 * @param controller The controller of the MVC pattern.
	 */
	public SmsServicesGraphicalView(String title, Controller controller)
	{
		super(SERVICE_SUITE_NAME+title);

		this.w = new GraphicalViewWindowListener();
		this.addWindowListener(w);
		this.setIconImage(ICON);
	}


	/**
	 * Initializes this frame by setting its dimensions, properties, and layout.
	 */
	private void initializeFrame()
	{
		this.setResizable(false);

		setSize(DIMENSIONS); // set the correct dimensions
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout( new GridLayout(1,1) );
	}


	/**
	 * Initializes the menu bar of this frame.
	 */
	private void initializeMenuBar()
	{
		JMenuBar menuBar = new SmsMenuBar(this);
		setJMenuBar(menuBar);
	}


	/**
	 * Initializes the panels available in the view and focuses on the default one.
	 */
	private void initializePanels()
	{
		this.subscribersPanel = new SubscribersPanel(DIMENSIONS);
		this.consolePanel = new ConsolePanel(DIMENSIONS);

		this.parametersPanel = new ParametersPanel(DIMENSIONS);
		this.parametersPanel.setSize(DIMENSIONS);

		this.mainPanel = new MainPanel(parametersPanel, this);
		this.mainPanel.setSize(DIMENSIONS);

		showParametersPanel();
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		initializeFrame();
		initializeMenuBar();
		initializePanels();
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#setConsoleLog(java.util.Stack)
	 */
	public void setConsoleLog(DefaultListModel log)
	{
		consolePanel.setConsoleLog(log);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#showAbout()
	 */
	public void showAbout()
	{
		AboutFrame.getInstance().setVisible(true);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#showConsolePanel()
	 */
	public void showConsolePanel()
	{
		showPanel(consolePanel);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#showMainPanel()
	 */
	public void showMainPanel()
	{
		showPanel(mainPanel);
	}


	/**
	 * Focuses the specified container into view.
	 * @param panel The container to focus into view.
	 */
	private void showPanel(Container panel)
	{
		this.setContentPane(panel);
		panel.setVisible(true);
		setVisible(true);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#showParametersPanel()
	 */
	public void showParametersPanel()
	{
		showPanel(parametersPanel);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#showSubscribersPanel()
	 */
	public void showSubscribersPanel()
	{
		showPanel(subscribersPanel);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#startService(com.canabang.genietext.core.model.structs.Parameters)
	 */
	public SmsServiceProvider startService(Parameters params) throws TransformerConfigurationException, MessagingException
	{
		SmsServiceProvider s = Controller.startService(params);
		setConsoleLog( s.getLog() );
		subscribersPanel.setList( s.getSubscribers() );

		this.w.setService(s);
		this.subscribersPanel.setService(s);

		return s;
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#stopService()
	 */
	public void stopService()
	{
		this.w.setService(null);
		this.subscribersPanel.setService(null);
	}
}