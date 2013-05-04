/*
 * @(#)ParametersPanel.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.io.xml.XmlParametersParser;
import com.canabang.genietext.core.model.structs.ConnectionInfo;
import com.canabang.genietext.core.model.structs.Parameters;
import com.canabang.genietext.core.view.panels.BackgroundImageJPanel;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Panel where the user can specify the settings associated with the service being started.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ParametersPanel extends BackgroundImageJPanel implements ActionListener
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Allows browsing for parameters XML file. */
	private AbstractButton browse;

	/** Extra settings can be configured in this panel. */
	private ExtraSettingsPanel extra;

	/** Allows a file to be chosen. */
	private final JFileChooser fc = new JFileChooser();

	/** Input panel to allow loading data from an outside file. */
	private JPanel input;

	/** Holds input file path information. */
	private JTextComponent inputFileField;

	/** Will hold server information. */
	private ServerInfoPanel servers;

	/** Parses input XML file. */
	private XmlParametersParser xmlParametersParser;


	/**
	 * Creates a parameters panel to use the specified dimensions to position its elements.
	 * @param d The dimensions used to position the elements.
	 */
	public ParametersPanel(Dimension d)
	{
		super();

		initializeXmlParser();
		setLayout( new GridLayout(3,1) );

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addActionListener(this);

		initializeInput(d);
		initializeOthers(d);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object j = e.getSource();

		if ( j.equals(browse) )
			browseClicked();
	}


	/**
	 * Reaction when browse button is clicked.
	 */
	private void browseClicked()
	{
		try {
			int returnVal = fc.showDialog(this, "Select");

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File selected = fc.getSelectedFile();
				inputFileField.setText( selected.getAbsolutePath() );

				Parameters p = xmlParametersParser.parse(selected);
				servers.getSmtp().setFields( p.getSmtp() );
				servers.getImap().setFields( p.getImap() );
				extra.setFields(p);
			}
		}

		catch (Exception ex)
		{
			JOptionPane.showMessageDialog( this, ex.getMessage() );
		}
	}


	/**
	 * Retrieves the values stored in the fields of this panel and its subpanels.
	 * @return The parameter values stored in the fields of this panel and its subpanels required
	 * to start a service.
	 */
	public Parameters getValues()
	{
		ConnectionInfo imap = servers.getImap().getFields();
		ConnectionInfo smtp = servers.getSmtp().getFields();
		int selectedClock = extra.getClockSelected();
		String subsBackup = extra.getBackup().getText();

		Parameters para = new Parameters();
		para.setClockFormat(selectedClock);
		para.setImap(imap);
		para.setSmtp(smtp);
		para.setSubscriptionOutput(subsBackup);

		return para;
	}


	/**
	 * Initializes the input panel.
	 * @param d The dimensions used to position the elements of the input panel.
	 */
	private void initializeInput(Dimension d)
	{
		input = new JPanel();
		input.setSize( d.width, d.height/33 );
		input.setOpaque(false);
		input.setLayout( new GridBagLayout() );

		input.add( new JLabel("Input File:") );

		this.inputFileField = new FixedTextField("Select input file");
		input.add(inputFileField);

		browse = new JButton("Browse");
		browse.addActionListener(this);
		input.add(browse);

		add(input);
	}

	/**
	 * Initializes the server information panel and the extra settings panel.
	 * @param d The dimensions used to position the elements of the panels.
	 */
	private void initializeOthers(Dimension d)
	{
		servers = new ServerInfoPanel(d);
		add(servers);

		extra = new ExtraSettingsPanel(d);
		add(extra);
	}


	/**
	 * Initializes the XML parser used to parse the input file.
	 */
	private void initializeXmlParser()
	{
		try
		{
			this.xmlParametersParser = new XmlParametersParser();
		}

		catch (ParserConfigurationException ex)
		{
			JOptionPane.showMessageDialog( this, ex.getMessage() );
		}
	}
}