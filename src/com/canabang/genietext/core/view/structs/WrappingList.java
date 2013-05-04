/*
 * @(#)WrappingList.java	1.0	05/31/09
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
package com.canabang.genietext.core.view.structs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.text.View;


/**
 * A JList that wraps its text. Whenever the list's layout (e.g. size) changes the list's UI is
 * instructed to update its layout too.
 * 
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class WrappingList extends JList
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;


	/**
	 * List cell renderer that uses the list's width to alter its preferred size
	 * TODO - override bound properties in the same way as DefaultListCellRenderer.
	 */
	private static class WrappingListCellRenderer extends JTextPane implements ListCellRenderer
	{
		/** Used if this class were to be serialized. */
		private static final long serialVersionUID = 1L;


		/**
		 * Instantiates a new wrapping list cell renderer.
		 */
		public WrappingListCellRenderer()
		{
			setBorder( new EmptyBorder(1, 1, 1, 1) );
		}


		/* (non-Javadoc)
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean hasFocus)
		{
			setBackground(selected ? list.getSelectionBackground() : list.getBackground());
			setForeground(selected ? list.getSelectionForeground() : list.getForeground());

			setText(String.valueOf(value));

			float hspan = list.getWidth();
			View view = ((TextUI) getUI()).getRootView(this);
			view.setSize(hspan, Float.MAX_VALUE);
			float vspan = view.getPreferredSpan(View.Y_AXIS);

			Insets insets = getInsets();
			setPreferredSize(new Dimension(insets.left + insets.right + (int) hspan, insets.top + insets.bottom + (int) vspan));

			return this;
		}
	}


	/**
	 * ListUI implementation that exposes the method for updating its layout.
	 */
	private static class WrappingListUI extends BasicListUI
	{
		/* (non-Javadoc)
		 * @see javax.swing.plaf.basic.BasicListUI#updateLayoutState()
		 */
		public void updateLayoutState()
		{
			super.updateLayoutState();
		}
	}


	/**
	 * Instantiates a new wrapping list.
	 */
	public WrappingList()
	{
		super();
		setUI( new WrappingListUI() );
		setCellRenderer( new WrappingListCellRenderer() );
	}


	/* (non-Javadoc)
	 * @see java.awt.Container#doLayout()
	 */
	public void doLayout()
	{
		( (WrappingListUI) getUI() ).updateLayoutState();
		super.doLayout();
	}


	/* (non-Javadoc)
	 * @see javax.swing.JList#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth()
	{
		return true;
	}
}