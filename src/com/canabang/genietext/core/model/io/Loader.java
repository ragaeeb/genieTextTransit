/*
 * @(#)Loader.java	1.0	05/31/09
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.ImageIcon;

/**
 * Allows the loading of resources from the system using a unique method.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public interface Loader
{
	/**
	 * Loads the specified file from the system.
	 * @param file The file to load.
	 * @return The file specified by the user.
	 * @throws FileNotFoundException If the specified resource file can not be found.
	 */
	public InputStream loadFile(String file) throws FileNotFoundException;


	/**
	 * Loads the specified image from the system.
	 * @param imageName The image to load.
	 * @return The image specified by the user.
	 */
	public Image loadImage(String image);


	/**
	 * Loads the specified image icon from the system.
	 * @param imageName The image icon to load.
	 * @return The image icon specified by the user.
	 */
	public ImageIcon loadImageIcon(String imageIcon);
}