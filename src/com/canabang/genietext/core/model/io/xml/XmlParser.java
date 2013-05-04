/*
 * @(#)XmlParser.java	1.0	05/31/09
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Parses a XML file.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public abstract class XmlParser
{
	/** Allows a parser to be obtained to produce DOM object trees from XML documents. */
	protected static DocumentBuilderFactory dbf;

	/** Obtains DOM Document instances from an XML document. */
	protected DocumentBuilder db;

	/** The entire XML document. */
	protected Document dom;


	/**
	 * Initializes the XML parser.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 */
	protected XmlParser() throws ParserConfigurationException
	{
		dbf = DocumentBuilderFactory.newInstance();
		this.db = dbf.newDocumentBuilder();
	}


	/**
	 * Gets the element stored at the specified index of the list of tags.
	 * @param n The list of tags to search for the requested element.
	 * @param i The index to retrieve the element from.
	 * @return The element stored at the specified index of the tags list.
	 */
	protected static Element getElement(NodeList n, int i)
	{
		return (Element)n.item(i);
	}


	/**
	 * Retrieves the text content stored in the specified tag name of the specified element.
	 * @param e The element to retrieve the text from.
	 * @param tagName The tag name of the element to extract the text from.
	 * @return The text value stored in the specified tag name of the specified element.
	 */
	protected static String getTextValue(Element e, String tagName)
	{
		String textVal = null;
		NodeList nl = e.getElementsByTagName(tagName);

		if ( (nl != null) && ( nl.getLength() > 0 ) )
		{
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
}