/*
 * @(#)ResourceManager.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.io;

import com.canabang.genietext.core.model.server.EmailServer;
import java.awt.Image;
import java.io.InputStream;
import javax.swing.ImageIcon;

/**
 * Loads resources from the system on demand.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class ResourceManager
{
	/** The instance of the right loader to use. This will vary depending on whether debugging is on or not. */
	private static Loader loader;


	/**
	 * Constructor should never be used.
	 */
	private ResourceManager()
	{
	}


	/**
	 * Creates an instance of the right loader to use.
	 */
	private static final void instantiate()
	{
		if (loader == null)
		{
			if (EmailServer.DEBUG)
				loader = new FileSystemLoader();

			else
				loader = new JarLoader();
		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadFile(java.lang.String)
	 */
	public static InputStream loadFile(String fileName)
	{
		instantiate();
		InputStream is = null;

		try {
			is = loader.loadFile(fileName);
		}

		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return is;
	}


	/*
	 * (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadImage(java.lang.String)
	 */
	public static Image loadImage(String imageName)
	{
		instantiate();

		return loader.loadImage(imageName);
	}


	/*
	 * (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadImageIcon(java.lang.String)
	 */
	public static ImageIcon loadImageIcon(String imageIcon)
	{
		instantiate();

		return loader.loadImageIcon(imageIcon);
	}
}