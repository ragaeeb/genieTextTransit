/*
 * @(#)ConsolePanel.java	1.0	05/31/09
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

import com.canabang.genietext.core.view.structs.WrappingList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * The console where to display the log of actions recorded on a service.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ConsolePanel extends JPanel
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Container storing the list of actions recorded on a service. */
	private JList consoleList;


	/**
	 * Creates a scrollable console panel that will store service log information.
	 * @param dimensions The dimensions of this console panel.
	 */
	public ConsolePanel(Dimension dimensions)
	{
		super();

		this.setLayout( new GridLayout(1,1) );

		this.consoleList = new WrappingList();
		this.consoleList.setSize(dimensions);
		this.consoleList.setPreferredSize(dimensions);
		this.consoleList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollPane = new JScrollPane(consoleList);
		this.add(scrollPane, BorderLayout.CENTER);	
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.view.SmsServicesView#setConsoleLog(javax.swing.DefaultListModel)
	 */
	public void setConsoleLog(DefaultListModel log)
	{
		consoleList.setModel(log); 
	}
}