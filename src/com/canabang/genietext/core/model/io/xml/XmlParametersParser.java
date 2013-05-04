/*
 * @(#)XmlParametersParser.java	1.0	05/31/09
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

import com.canabang.genietext.core.model.structs.ConnectionInfo;
import com.canabang.genietext.core.model.structs.Parameters;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Parses the parameters used to start a service stored from a XML file.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class XmlParametersParser extends XmlParser
{
	/** The parsed parameters. */
	private Parameters p;


	/**
	 * Creates an instance of this class so that parameters for a service can be extracted
	 * from a XML file.
	 * @throws ParserConfigurationException If there is any problem setting up the XML parser.
	 */
	public XmlParametersParser() throws ParserConfigurationException
	{
		super();
		this.p = new Parameters();
	}


	/**
	 * Parses the specified file and retrieves the parameters contained within.
	 * @param file The XML file containing the parameters to parse.
	 * @return The parameters contained within the file.
	 * @throws SAXException If there are any problems parsing the XML file.
	 * @throws IOException If there is a problem accessing the resource file.
	 */
	public Parameters parse(File file) throws SAXException, IOException
	{
		// parse using builder to get DOM representation of the XML file
		dom = db.parse(file);
		parseDocument();

		return p;
	}


	/**
	 * Parses the clock format stored in the XML file.
	 * @param n The list of tags that contains tags with the clock format attributes.
	 */
	private void parseClockSetup(NodeList n)
	{
		for (int i = 0; i < n.getLength(); i++)
		{
			Element e = getElement(n, i);

			String clockFormat = e.getAttribute(XmlProperties.CLOCK_FORMAT_KEY);
			p.setClockFormat( Integer.parseInt(clockFormat) );
		}
	}


	/**
	 * Parses the document and populates the parameters object.
	 */
	private void parseDocument()
	{
		// get the root element: smsServices
		Element root = dom.getDocumentElement();

		NodeList servers = root.getElementsByTagName(XmlProperties.SERVER_KEY);
		NodeList subscription = root.getElementsByTagName(XmlProperties.SUBSCRIPTION_KEY);
		NodeList clock = root.getElementsByTagName(XmlProperties.CLOCK_KEY);

		parseServers(servers);
		parseSubscriptionSetup(subscription);
		parseClockSetup(clock);
	}


	/**
	 * Parses the server information stored in the XML file.
	 * @param n The list of tags that contains tags with the IMAP and SMTP server information.
	 */
	private void parseServers(NodeList n)
	{
		for (int i = 0; i < n.getLength(); i++)
		{
			Element e = getElement(n, i); // gets the <servers> tag

			String protocol = e.getAttribute(XmlProperties.PROTOCOL_KEY);

			if ( protocol.equals(XmlProperties.IMAP_VALUE) )
				processIMAP(e);

			else if ( protocol.equals(XmlProperties.SMTP_VALUE) )
				processSMTP(e);
		}
	}


	/**
	 * Parses the subscription output file path stored in the XML file.
	 * @param n The list of tags that contains tags with the subscription output attributes.
	 */
	private void parseSubscriptionSetup(NodeList n)
	{
		for (int i = 0; i < n.getLength(); i++)
		{
			Element e = getElement(n, i);

			String outputPath = e.getAttribute(XmlProperties.SUBSCRIPTION_OUTPUT_KEY);
			p.setSubscriptionOutput(outputPath);
		}
	}


	/**
	 * Parses the IMAP server information stored in the XML file.
	 * @param e The list of tags that contains tags with the IMAP server information.
	 */
	private void processIMAP(Element e)
	{
		String username = getTextValue(e, XmlProperties.USERNAME_KEY);
		String password = getTextValue(e, XmlProperties.PASSWORD_KEY);
		String host = getTextValue(e, XmlProperties.HOST_KEY);

		ConnectionInfo c = new ConnectionInfo(username, password, host);
		p.setImap(c);
	}


	/**
	 * Parses the SMTP server information stored in the XML file.
	 * @param e The list of tags that contains tags with the SMTP server information.
	 */
	private void processSMTP(Element e)
	{
		String username = getTextValue(e, XmlProperties.USERNAME_KEY);
		String password = getTextValue(e, XmlProperties.PASSWORD_KEY);
		String host = getTextValue(e, XmlProperties.HOST_KEY);
		String port = getTextValue(e, XmlProperties.PORT_KEY);

		ConnectionInfo c = new ConnectionInfo( username, password, host, Integer.parseInt(port) );
		p.setSmtp(c);
	}
}