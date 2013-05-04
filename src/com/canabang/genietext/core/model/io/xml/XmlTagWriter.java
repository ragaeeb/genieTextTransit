/*
 * @(#)XmlTagWriter.java	1.0	03/29/09
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

import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Facilitates the writing of XML tags and contents to a XML
 * TransformerHandler and Attributes handler.
 *
 * @author rhaq
 * @version 1.00 2009-03-29 Initial submission.
 */
public class XmlTagWriter
{
	/** Index to begin reading from the character array when
	 *  writing to the tag's contents value. */
	public static final int TAG_CONTENTS_START = 0;

	/** Handles the XML file to generate. */
	private TransformerHandler hd;

	/** Handles attributes to be added to a tag. */
	private AttributesImpl atts;


	/**
	 * Sets up the XML tag writer to use the specified handler and the
	 * attribute handler.
	 * @param hd The handler to use when writing XML tags.
	 * @param atts The attribute handler to use when writing the XML data.
	 */
	public XmlTagWriter(TransformerHandler hd, AttributesImpl atts)
	{
		this.hd = hd;
		this.atts = atts;
	}


	/**
	 * Adds the specified attribute value to the specified tag attribute.
	 * @param tagNameAttribute The attribute name to set as the specified value.
	 * @param tagAttributeValue The attribute value to set for the
	 * specified attribute name.
	 */
	public void writeTagAttribute(String tagNameAttribute, String tagAttributeValue)
	{
		atts.addAttribute( XmlProperties.BLANK, XmlProperties.BLANK, tagNameAttribute, XmlProperties.DEFAULT_ATTRIBUTE_TYPE, tagAttributeValue );		
	}


	/**
	 * Writes the specified contents to the open tag.
	 * @param contents The value of the contents to write to
	 * the open tag.
	 * @throws SAXException If there are any problems writing to the output XML file. 
	 */
	public void writeTagContents(String contents) throws SAXException
	{
		hd.characters( contents.toCharArray(), TAG_CONTENTS_START, contents.length() );
	}


	/**
	 * Closes the specified tag.
	 * @param tagName The tag to close.
	 * @throws SAXException If there are any problems writing to the output XML file.
	 */
	public void writeTagEndElement(String tagName) throws SAXException
	{
		hd.endElement( XmlProperties.BLANK, XmlProperties.BLANK, tagName);
	}


	/**
	 * Opens the specified tag.
	 * @param tagName The tag to open.
	 * @throws SAXException If there are any problems writing to the output XML file.
	 */
	public void writeTagStartElement(String tagName) throws SAXException
	{
		hd.startElement( XmlProperties.BLANK, XmlProperties.BLANK, tagName, atts);
	}
}