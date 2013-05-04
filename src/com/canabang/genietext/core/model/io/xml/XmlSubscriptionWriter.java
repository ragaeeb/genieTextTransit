/*
 * @(#)XmlSubscriptionWriter.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.io.xml;

import java.io.IOException;
import java.sql.Timestamp;
import javax.swing.DefaultListModel;
import javax.xml.transform.TransformerConfigurationException;
import org.xml.sax.SAXException;


/**
 * Deals with all XML subscription outputs to the file system.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class XmlSubscriptionWriter extends XmlWriter
{
	/**
	 * Sets up the XML generator and its dependencies so an output file can be generated.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up. 
	 */
	public XmlSubscriptionWriter() throws TransformerConfigurationException
	{
		super();
	}


	/**
	 * Outputs the specified address entries to the specified file.
	 * @param map The list of addresses to output to the file system.
	 * @param outputFile The file to output the specified address entries to.
	 * @param ts The time-stamp of the submission.
	 * @throws SAXException If there are any problems writing an output XML file.
	 * @throws IOException If the output file cannot be opened to write.
	 */
	public void output(DefaultListModel map, String outputFile, Timestamp ts) throws SAXException, IOException
	{
		// initialise
		initializeOutput(outputFile);

		// write initial header values to XML
		tagWriter.writeTagAttribute(XmlProperties.SYSTEM_TIMESTAMP, ts.toString() );
		tagWriter.writeTagStartElement(XmlProperties.ADDRESSES_KEY);

		for (int i = 0; i < map.size(); i++)
			output( map.get(i) ); // write address out

		tagWriter.writeTagEndElement(XmlProperties.ADDRESSES_KEY); // close initial tags
		cleanUp();
	}


	/**
	 * Writes the specified log entry to the output file.
	 * @param a The text element to submit to the output file.
	 * @throws SAXException If there are any problems writing to the output XML file.
	 */
	private void output(Object a) throws SAXException
	{
		atts.clear();

		tagWriter.writeTagStartElement(XmlProperties.ADDRESS_KEY);
		tagWriter.writeTagContents( a.toString() );
		tagWriter.writeTagEndElement(XmlProperties.ADDRESS_KEY);
	}
}