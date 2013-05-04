/*
 * @(#)SmsMenuBar.java	1.0	05/31/09
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * The menu bar associated with the main window.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SmsMenuBar extends JMenuBar implements ActionListener
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Clicking this item will open the about frame. */
	private JMenuItem about;

	/** Clicking this will terminate the view. */
	private JMenuItem close;

	/** Clicking this item will bring focus to the console. */
	private JMenuItem console;

	/** Clicking this will bring focus to the main panel. */
	private JMenuItem control;

	/** Clicking this will bring focus to the parameters panel. */
	private JMenuItem parameters;

	/** The view portion of the MVC pattern to reflect model's changes to. */
	private SmsServicesView parent;

	/** Clicking this will bring focus to the subscribers panel. */
	private JMenuItem subscribers;


	/**
	 * Creates an instance of this menu bar to report changes to the specified parent window.
	 * @param parent The view portion of the MVC pattern to reflect model's changes to.
	 */
	public SmsMenuBar(SmsServicesView parent)
	{
		super();

		this.parent = parent;

		JMenu fileMenu = new JMenu("File");
		this.close = new JMenuItem("Close");
		close.addActionListener(this);
		fileMenu.add(close);

		JMenu editMenu = new JMenu("Edit");
		this.parameters = new JMenuItem("Parameters");
		parameters.addActionListener(this);
		editMenu.add(parameters);

		// -----------------------------------
		JMenu viewMenu = new JMenu("View");

		this.control = new JMenuItem("Control");
		viewMenu.add(control);
		control.addActionListener(this);

		this.console = new JMenuItem("Console");
		viewMenu.add(console);
		console.addActionListener(this);

		this.subscribers = new JMenuItem("Subscribers");
		viewMenu.add(subscribers);
		subscribers.addActionListener(this);
		// -----------------------------------

		JMenu helpMenu = new JMenu("Help");
		this.about = new JMenuItem("About");
		about.addActionListener(this);
		helpMenu.add(about);

		this.add(fileMenu);
		this.add(editMenu);
		this.add(viewMenu);
		this.add(helpMenu);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object j = e.getSource();

		if ( j.equals(about) )
			parent.showAbout();

		else if ( j.equals(subscribers) )
			parent.showSubscribersPanel();

		else if ( j.equals(console) )
			parent.showConsolePanel();

		else if ( j.equals(parameters) )
			parent.showParametersPanel();

		else if ( j.equals(control) )
			parent.showMainPanel();

		else if ( j.equals(close) )
		{
			parent.stopService();
			JFrame k = (JFrame)parent;
			k.dispose();
			System.exit(0);
		}
	}
}