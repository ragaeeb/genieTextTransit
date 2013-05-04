/*
 * @(#)MainPanel.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.io.ResourceManager;
import com.canabang.genietext.core.view.SmsServicesView;
import com.canabang.genietext.core.view.panels.parameters.ParametersPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Creates the primary panel that can be used to start and stop services.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class MainPanel extends BackgroundImageJPanel
{
	/** Image shown when the service is active. */
	private static final ImageIcon ACTIVE_LOGO = ResourceManager.loadImageIcon("genieText_active.png");

	/** Image shown when the service is inactive. */
	private static final ImageIcon INACTIVE_LOGO = ResourceManager.loadImageIcon("genieText_inactive.png");

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Text value for the start button. */
	private static final String START_BUTTON_TEXT = "Start";

	/** Text value for the stop button. */
	private static final String STOP_BUTTON_TEXT = "Stop";

	/** Container for the activity image. */
	private JLabel imageLabel;

	/** Allows services to be started. */
	private AbstractButton start;

	/** Allows a service to be terminated. */
	private AbstractButton stop;


	/**
	 * Creates a main panel to get its service parameter information from the specified panel and
	 * to report changes to the specified view.
	 * @param p The panel containing the parameters to extract to start the service.
	 * @param view The view portion of the MVC pattern to reflect model's changes to.
	 */
	public MainPanel(ParametersPanel p, SmsServicesView view)
	{
		super();

		ActionListener a = new MainPanelActionListener(this, p, view);
		this.setLayout( new GridLayout(2,1) );
		this.imageLabel = new JLabel();

		inactive();
		initializePanels(a);
	}


	/**
	 * The service is active, show the active logo.
	 */
	public void active()
	{
		imageLabel.setIcon(ACTIVE_LOGO);
	}


	/**
	 * Retrieves the start button.
	 * @return The button that allows the service to start.
	 */
	public AbstractButton getStart()
	{
		return start;
	}


	/**
	 * Retrieves the stop button.
	 * @return The button that allows the service to terminate.
	 */
	public AbstractButton getStop()
	{
		return stop;
	}


	/**
	 * The service is inactive, show the inactive logo.
	 */
	public void inactive()
	{
		imageLabel.setIcon(INACTIVE_LOGO);
	}


	/**
	 * Initializes the subpanels and adds the specified action listener to the right buttons.
	 * @param a Listens to the actions of the associated buttons.
	 */
	private void initializePanels(ActionListener a)
	{
		JPanel p1 = new JPanel( new GridBagLayout() );
		p1.setOpaque(false);
		p1.add(imageLabel, new GridBagConstraints());

		JPanel p2 = new JPanel();
		p2.setOpaque(false);
		this.start = new JButton(START_BUTTON_TEXT);
		this.stop = new JButton(STOP_BUTTON_TEXT);
		this.stop.setEnabled(false);

		start.addActionListener(a);
		stop.addActionListener(a);

		p2.add(start);
		p2.add(stop);

		this.add(p1);
		this.add(p2);	
	}
}