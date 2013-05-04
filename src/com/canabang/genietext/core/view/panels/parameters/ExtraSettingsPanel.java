/*
 * @(#)ExtraSettingsPanel.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.structs.Parameters;
import com.canabang.genietext.core.model.structs.Time;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Stores extra settings for the system such as time display format and path to backup
 * subscription service.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ExtraSettingsPanel extends JPanel
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Stores the subscription backup path. */
	private JTextField backup;

	/** Allows selection of path to store subscription file. */
	private JButton browse;

	/** 12-hour clock format. */
	private JCheckBox twelve;

	/** 24-hour clock format. */
	private JCheckBox twentyFour;


	/**
	 * Creates the extra settings panel with the specified dimensions to position its elements.
	 * @param d The dimensions used to position the elements.
	 */
	public ExtraSettingsPanel(Dimension d)
	{
		super();

		ActionListener l = new ExtraSettingsPanelActionListener(this);
		this.setLayout( new GridLayout(2,3) );
		this.setSize(d.width, d.height/33);
		this.setOpaque(false);

		//----------------------------------
		JPanel upper = new JPanel();
		upper.setOpaque(false);

		this.backup = new FixedTextField("Choose output directory");
		this.browse = new JButton("Browse");
		this.browse.addActionListener(l);

		upper.add( new JLabel("Subscription Backup File:") );
		upper.add(backup);
		upper.add(browse);

		add(upper);
		//----------------------------------

		JPanel lower = new JPanel();
		lower.setOpaque(false);

		lower.add( new JLabel("Time Format:") );

		this.twelve = new JCheckBox("12-Hour", true);
		twelve.setOpaque(false);
		this.twentyFour = new JCheckBox("24-Hour", false);
		twentyFour.setOpaque(false);

		ButtonGroup g = new ButtonGroup();
		g.add(twelve);
		g.add(twentyFour);

		lower.add(twelve);
		lower.add(twentyFour);
		add(lower);
	}


	/**
	 * Retrieves the subscription backup path.
	 * @return The path where the subscription data will be backed up to.
	 */
	public JTextField getBackup()
	{
		return backup;
	}


	/**
	 * Retrieves the button allowing the user to select the path to store the subscription
	 * backup file.
	 * @return The browse button allowing the  user to select the path to output the subscription
	 * backup file.
	 */
	public JButton getBrowse()
	{
		return browse;
	}


	/**
	 * Retrieves the clock format selected (will either be the military 24-hour format or the
	 * standard 12-hour format).
	 * @return The time format selected.
	 */
	public int getClockSelected()
	{
		int result = Time.STANDARD_CLOCK_FORMAT;

		if ( twentyFour.isSelected() )
			result = Time.MILITARY_CLOCK_FORMAT;

		return result;
	}


	/**
	 * Sets the fields of this panel to match the ones in the parameter.
	 * @param p The parameters that will hold what the values of these fields should be.
	 */
	public void setFields(Parameters p)
	{
		this.backup.setText( p.getSubscriptionOutput() );

		if ( p.getClockFormat() == 12 )
			this.twelve.setSelected(true);

		else if ( p.getClockFormat() == 24 )
			this.twentyFour.setSelected(true);

		else
			throw new IllegalArgumentException("Invalid time format specified");
	}
}