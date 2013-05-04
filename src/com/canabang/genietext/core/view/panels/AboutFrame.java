/*
 * @(#)AboutFrame.java	1.0	05/31/09
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
import com.canabang.genietext.core.view.SmsServicesGraphicalView;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 * Shows copyright and ownership information about this product. There can be at maximum one
 * about frame per instance of the service.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class AboutFrame extends JFrame
{
	/** The fixed dimensions of this frame. */
	private static final Dimension DIMENSIONS = new Dimension(300, 300);

	/** The font used to display the text. */
	private static final String FONT = "verdana bold";

	/** The single instance of this factory to create. */
	private static AboutFrame instance;

	/** The icon to display for this window. */
	private static ImageIcon LOGO = ResourceManager.loadImageIcon("logo.png");

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** The text that is displayed. */
	private static final String TEXT = "genieText v1.00\n(c) 2009 canabang inc.\nall rights reserved.";

	/** The title of this frame. */
	private static final String TITLE = "About genieText";

	/** The container for the text. */
	private JTextPane textPane;


	/**
	 * Creates an instance of this frame and initializes its contents.
	 */
	private AboutFrame()
	{
		super(TITLE);

		initializeFrame();
		initializePanels();
		initializeStyleSheet();
	}


	/**
	 * Initializes this frame's dimensions, layout, icon, and properties.
	 */
	private void initializeFrame()
	{
		this.setSize(DIMENSIONS);
		this.setResizable(false);
		this.setLayout( new GridLayout(2,1) );
		this.setIconImage(SmsServicesGraphicalView.ICON);
	}


	/**
	 * Initializes the panels within this frame.
	 */
	private void initializePanels()
	{
		this.textPane = new JTextPane();
		this.textPane.setText(TEXT);
		textPane.setAlignmentX(CENTER_ALIGNMENT);
		textPane.setEditable(false);
		textPane.setOpaque(false);
	}


	/**
	 * Initializes the style sheet for the text.
	 */
	private void initializeStyleSheet()
	{
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet bSet = new SimpleAttributeSet();
		StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontFamily(bSet, FONT);
		doc.setParagraphAttributes(0, 104, bSet, false);
		textPane.setHighlighter(null);

		add( new JLabel(LOGO) );
		add(textPane);
	}


	/**
	 * Retrieves a single instance of this class.
	 * @return a single instance of this class.
	 */
	public static AboutFrame getInstance()
	{
		if (instance == null)
			instance = new AboutFrame();

		return instance;
	}
}