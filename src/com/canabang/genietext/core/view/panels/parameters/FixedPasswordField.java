/*
 * @(#)FixedPasswordField.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.io.xml.XmlProperties;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;


/**
 * A non-resizable fixed password field.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class FixedPasswordField extends JPasswordField implements FocusListener
{
	/** Preferred width of this field. */
	private static final int PREFERRED_WIDTH = 150;

	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Was the value for this field previously set manually. */
	private boolean set;


	/**
	 * Creates a non-resizable fixed password field with the specified password value.
	 * @param text The password to display in the field.
	 */
	public FixedPasswordField(String text)
	{
		super(text);

		Dimension s = this.getPreferredSize();
		s.setSize(PREFERRED_WIDTH, s.height);
		this.setPreferredSize(s);

		this.setOpaque(false);
		this.addFocusListener(this);
		this.set = false;
	}


	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent f)
	{
		if (!set)
		{
			this.setText(XmlProperties.BLANK);
			set = true;
		}
	}


	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent f)
	{
	}
}