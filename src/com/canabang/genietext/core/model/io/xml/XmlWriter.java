/*
 * @(#)XmlWriter.java	1.0	05/31/09
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Allows writing data to a XML file to the file system.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public abstract class XmlWriter
{
	/** Handles attributes to be added to a tag. */
	protected AttributesImpl atts;

	/** Handles the file to output the results to. */
	protected Writer fileWriter;

	/** Handles the XML file to generate. */
	protected TransformerHandler hd;

	/** Handles the output stream to store the results to. */
	protected PrintWriter out;

	/** Handles the XML output generation. */
	protected Transformer serializer;

	/** Holds a markup language transformation results. */
	protected StreamResult streamResult;

	/** Assists in writing XML tags to the XML object. */
	protected XmlTagWriter tagWriter;

	/** Generates a XML file generator object. */
	protected SAXTransformerFactory tf;


	/**
	 * Creates an instance of this class so that a XML file can be written to the file system.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up. 
	 */
	protected XmlWriter() throws TransformerConfigurationException
	{
		streamResult = new StreamResult();
		tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		hd = tf.newTransformerHandler();
		serializer = hd.getTransformer();
		atts = new AttributesImpl();
		tagWriter = new XmlTagWriter(hd, atts);

		serializer.setOutputProperty(OutputKeys.ENCODING, XmlProperties.OUTPUT_ENCODING);
		serializer.setOutputProperty(OutputKeys.INDENT, XmlProperties.INDENT_VALUE);
		serializer.setOutputProperty(OutputKeys.VERSION, XmlProperties.VERSION);
		serializer.setOutputProperty(OutputKeys.METHOD, XmlProperties.METHOD_VALUE);
		serializer.setOutputProperty(OutputKeys.MEDIA_TYPE, XmlProperties.MEDIA_TYPE_VALUE);

		// initialise indenting capabilities
		tf.setAttribute(XmlProperties.INDENT_PROPERTY, XmlProperties.INDENT_WHITE_SPACES);
		serializer.setOutputProperty(XmlProperties.INDENT_XALAN_URL, XmlProperties.INDENT_XALAN_VALUE);
		serializer.setOutputProperty(XmlProperties.INDENT_XSLT_URL, XmlProperties.INDENT_XSLT_VALUE);
	}


	/**
	 * Shuts down used resources and frees up memory.
	 * @throws SAXException If there are any problems closing the output XML file.
	 * @throws IOException If the output streams cannot be closed.
	 */
	protected void cleanUp() throws SAXException, IOException
	{
		hd.endDocument();
		out.close();
		fileWriter.close();
	}


	/**
	 * Initializes the system so that the specified output file can be generated.
	 * @param outputFile The file to output the specified data to.
	 * @throws SAXException If there are any problems writing an output XML file.
	 * @throws IOException If the output file cannot be opened to write.
	 */
	protected void initializeOutput(String outputFile) throws IOException, SAXException
	{
		fileWriter = new FileWriter(outputFile, false); // append to file
		out = new PrintWriter(fileWriter);
		streamResult.setWriter(out);
		hd.setResult(streamResult);

		atts.clear();
		hd.startDocument();
	}
}