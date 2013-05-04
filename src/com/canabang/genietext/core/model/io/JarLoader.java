/*
 * @(#)JarLoader.java	1.0	05/31/09
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

import java.awt.Image;
import java.io.InputStream;
import java.net.URL;
import javax.swing.ImageIcon;


/**
 * Allows the loading of resources from a JAR file.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class JarLoader implements Loader
{

	/** The prefix to add to access the root of the JAR file. */
	private static final String PREFIX = "/";


	/**
	 * Creates a new instance of this class so that resources from a JAR file may be extracted.
	 */
	public JarLoader()
	{
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadFile(java.lang.String)
	 */
	public InputStream loadFile(String fileName)
	{
		return getClass().getResourceAsStream(PREFIX+fileName);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadImage(java.lang.String)
	 */
	public Image loadImage(String imageName)
	{
		URL imgURL = getResourceURL(imageName);
		return java.awt.Toolkit.getDefaultToolkit().getImage(imgURL);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadImageIcon(java.lang.String)
	 */
	public ImageIcon loadImageIcon(String imageName)
	{
		return new ImageIcon( loadImage(imageName) );
	}


	/**
	 * Gets the URL path to the specified resource.
	 * @param resourceName The resource to retrieve the path for.
	 * @return The URL of the resource specified.
	 */
	private static URL getResourceURL(String resourceName)
	{
		return JarLoader.class.getResource(PREFIX+resourceName);
	}
}