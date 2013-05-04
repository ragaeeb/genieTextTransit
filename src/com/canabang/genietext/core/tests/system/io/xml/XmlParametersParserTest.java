/*
 * @(#)XmlParserTest.java	1.0	05/31/09
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
package com.canabang.genietext.core.tests.system.io.xml;

import static org.junit.Assert.assertEquals;
import com.canabang.genietext.core.model.io.xml.XmlParametersParser;
import com.canabang.genietext.core.model.structs.ConnectionInfo;
import com.canabang.genietext.core.model.structs.Parameters;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


/**
 * Tests the methods of the XmlParametersParser class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class XmlParametersParserTest
{
	/** The instance of the class to test. */
	private XmlParametersParser parser;


	/**
	 * Sets up the environment for testing.
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception
	{
		this.parser = new XmlParametersParser();
	}


	/**
	 * Test method for {@link com.canabang.genietext.core.model.io.xml.XmlParametersParser#parse(java.io.File)}.
	 * @throws SAXException If there are any problems parsing the XML file.
	 * @throws IOException If there is a problem accessing the resource file.
	 */
	@Test
	public void testRead() throws SAXException, IOException
	{
		Parameters z = parser.parse( new File("./src/com/canabang/genietext/core/tests/sampleRead.xml") );

		Parameters p = new Parameters();
		ConnectionInfo i = new ConnectionInfo("username@gmail.com", "pass", "imap.gmail.com");
		ConnectionInfo s = new ConnectionInfo("username@gmail.com", "pass", "smtp.gmail.com", 465);
		p.setImap(i);
		p.setSmtp(s);
		p.setClockFormat(12);
		p.setSubscriptionOutput("C:/sampleOutput.xml");

		assertEquals( p.toString(), z.toString() );
	}
}