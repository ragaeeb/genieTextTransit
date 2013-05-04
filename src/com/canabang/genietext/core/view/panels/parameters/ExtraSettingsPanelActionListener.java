/*
 * @(#)ExtraSettingsPanelActionListener.java	1.0	05/31/09
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;


/**
 * Listens to the ExtraSettingsPanel for events such as the browse button being pressed
 * so the user may selected the output directory.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ExtraSettingsPanelActionListener implements ActionListener
{
	/** Button text to display when selecting the output path for the subscription backup file. */
	private static final String SELECT_TEXT = "Select";

	/** Allows a file to be chosen. */
	private final JFileChooser fc = new JFileChooser();

	/** Reacts to actions onto this panel. */
	private ExtraSettingsPanel p;


	/**
	 * Creates a listener to listen to the browse button being clicked for the specified panel.
	 * @param p Reacts to actions onto this panel.
	 */
	public ExtraSettingsPanelActionListener(ExtraSettingsPanel p)
	{
		this.p = p;

		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.addActionListener(this);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object j = e.getSource();

		if ( j.equals( p.getBrowse() ) )
			browseClicked();
	}


	/**
	 * Reaction when browse button is clicked.
	 */
	private void browseClicked()
	{
		int returnVal = fc.showDialog(p, SELECT_TEXT);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File selected = fc.getSelectedFile();
			p.getBackup().setText( selected.getAbsolutePath() );
		}
	}
}