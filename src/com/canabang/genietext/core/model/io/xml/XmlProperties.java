/*
 * @(#)XmlProperties.java	1.0	03/14/09
 * @(#)XmlProperties.java	1.1	05/31/09
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


/**
 * Contains the constant properties and values that will be used when parsing and generating
 * XML files.
 *
 * @author rhaq
 * @version 1.00 2009-03-14 Initial submission.
 * @version 1.10 2009-05-31 Modified to handle addresses, stripped away all unnecessary constants. Changed
 * this to an interface.
 */
public interface XmlProperties
{
	/** Contains the timestamp of a list of searches performed. */
	public static final String ADDRESS_KEY = "address";

	/** Contains all the results of a list of searches performed. */
	public static final String ADDRESSES_KEY = "addresses";

	/** Value for empty fields. */
	public static final String BLANK = new String();

	/** Indicates what format the time is displayed in. */
	public static final String CLOCK_FORMAT_KEY = "format";

	/** A tag used for time display options. */
	public static final String CLOCK_KEY = "clock";

	/** Default type of attribute being written to the file. */
	public static final String DEFAULT_ATTRIBUTE_TYPE = "CDATA";

	/** Indicates a server host tag. */
	public static final String HOST_KEY = "host";

	/** Indicates a IMAP protocol type. */
	public static final String IMAP_VALUE = "IMAP";

	/** The property used to indent tags. */
	public static final String INDENT_PROPERTY = "indent-number";

	/** Should we indent the output XML file. */
	public static final String INDENT_VALUE = "yes";

	/** The reference of the number of whitespace values that should be used when indenting. */
	public static final int INDENT_WHITE_SPACES = 4;

	/** Xalan library reference used for indenting. */
	public static final String INDENT_XALAN_URL = "{http://xml.apache.org/xalan}indent-amount";

	/** Amount of white-spaces to indent by for Xalan library. */
	public static final String INDENT_XALAN_VALUE = "2";

	/** XSLT library reference used for indenting. */
	public static final String INDENT_XSLT_URL = "{http://xml.apache.org/xslt}indent-amount";

	/** Amount of white-spaces to indent by for XSLT library. */
	public static final String INDENT_XSLT_VALUE = "2";

	/** The type of output being produced. */
	public static final String MEDIA_TYPE_VALUE = "text/xml";

	/** The type of mark-up language that will be written. */
	public static final String METHOD_VALUE = "xml";

	/** Encoding format for the output XML file. */
	public static final String OUTPUT_ENCODING = "ISO-8859-1";

	/** The tag indicating that this is a password field. */
	public static final String PASSWORD_KEY = "password";

	/** Indicates the port used when connecting to a server. */
	public static final String PORT_KEY = "port";

	/** Indicates a protocol type attribute. */
	public static final String PROTOCOL_KEY = "protocol";

	/** The tag indicating a mail server. */
	public static final String SERVER_KEY = "server";

	/** Indicates a SMTP protocol type. */
	public static final String SMTP_VALUE = "SMTP";

	/** Tag representing subscription data. */
	public static final String SUBSCRIPTION_KEY = "subscription";

	/** Indicates the path subscriptions should backed up to. */
	public static final String SUBSCRIPTION_OUTPUT_KEY = "output";

	/** Contains the timestamp of a list of searches performed. */
	public static final String SYSTEM_TIMESTAMP = "timestamp";

	/** The tag indicating a username field is being read. */
	public static final String USERNAME_KEY = "username";

	/** The revision of the XML document format generator. */
	public static final String VERSION = "1.0";
}