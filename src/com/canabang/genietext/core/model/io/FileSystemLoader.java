/*
 * @(#)FileSystemLoader.java	1.0	05/31/09
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.ImageIcon;


/**
 * Allows the loading of resources from the file system.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class FileSystemLoader implements Loader
{
	/** The prefix to add to access the resource folder. */
	private static final String PREFIX = "./res/";

	/**
	 * Creates a new instance of this class so that resources from the file system may be extracted.
	 */
	public FileSystemLoader()
	{
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadFile(java.lang.String)
	 */
	public InputStream loadFile(String file) throws FileNotFoundException
	{
		return new FileInputStream( resolvePath(file) );
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadImage(java.lang.String)
	 */
	public Image loadImage(String image)
	{
		return loadImageIcon(image).getImage();
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.io.Loader#loadImageIcon(java.lang.String)
	 */
	public ImageIcon loadImageIcon(String imageIcon)
	{
		return new ImageIcon( resolvePath(imageIcon) );
	}


	/**
	 * Gets the correct path to the specified resource.
	 * @param file The file to retrieve the correct path for.
	 * @return The correct path of the specified resource.
	 */
	private static final String resolvePath(String file)
	{
		return PREFIX+file;
	}
}