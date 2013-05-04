/*
 * @(#)BackgroundImageJPanel.java	1.0	05/31/09
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
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


/**
 * A JPanel with a background image.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class BackgroundImageJPanel extends JPanel
{
	/** The background image to show. */
	private static final Image BACKGROUND_IMAGE = ResourceManager.loadImage("background.png");

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a JPanel that is not opaque and has a pre-set background image.
	 */
	public BackgroundImageJPanel()
	{
		this.setOpaque(false);
	}


	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g)
	{
		g.drawImage(BACKGROUND_IMAGE, 0, 0, this);
		super.paint(g);
	}
}