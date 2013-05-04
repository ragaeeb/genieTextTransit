/*
 * @(#)AddressSet.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.structs;

import com.canabang.genietext.core.model.io.xml.XmlSubscriptionWriter;
import java.io.IOException;
import java.sql.Timestamp;
import javax.mail.Address;
import javax.swing.DefaultListModel;
import javax.xml.transform.TransformerConfigurationException;
import org.xml.sax.SAXException;


/**
 * Represents a set of email addresses. Supports serializing capabilities.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class AddressSet extends DefaultListModel
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;

	/** Allows this address set to be saved to the file system. */
	private XmlSubscriptionWriter io;

	/** The output file to save this set of addresses to. */
	private String outputFile;


	/**
	 * Creates a blank set of addresses.
	 * @param outputPath The file to output the address entries to.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up to output the subscription list.
	 */
	public AddressSet(String outputPath) throws TransformerConfigurationException
	{
		super();
		this.outputFile = outputPath;
		io = new XmlSubscriptionWriter();
	}


	/**
	 * Adds an address to this set if it doesn't already exist.
	 * @param s The address to add as a String.
	 */
	public synchronized void addElement(String s)
	{
		if ( !contains(s) )
			super.addElement(s);
	}


	/**
	 * Adds the specified addresses to this set if they don't already exist.
	 * @param addresses The addresses to add.
	 */
	public void addElements(Address[] addresses)
	{
		for (Address a: addresses)
			this.addElement( a.toString() );
	}


	/**
	 * Backs up the current set of addresses to the file system.
	 * @throws SAXException If there are any problems writing an output XML file.
	 * @throws IOException If the output file cannot be opened to write.
	 */
	public synchronized void backUp() throws SAXException, IOException
	{
		Timestamp ts = new Timestamp( System.currentTimeMillis() );
		io.output(this, outputFile, ts);
	}


	/**
	 * Determines if the specified address exists in the current set.
	 * @param a The address to check if it exists in this set or not.
	 * @return true if the address exists in the set, false otherwise.
	 */
	public boolean partialMatch(Address a)
	{
		String address = a.toString().toLowerCase();
		boolean result = false;

		for (int i = 0; i < this.size(); i++)
		{
			String current = (String)this.get(i);

			if ( address.contains(current) )
			{
				result = true;
				break;
			}
		}

		return result;
	}


	/**
	 * Determines if the specified addresses exist in the current set.
	 * @param addresses The addresses to check if they exist in this set or not.
	 * @return true If at least one of the addresses exist in this set, false otherwise.
	 */
	public boolean partialMatch(Address[] addresses)
	{
		boolean result = false;

		for (Address a: addresses)
		{
			if ( partialMatch(a) )
			{
				result = true;
				break;
			}
		}

		return result;
	}


	/**
	 * Removes the specified address from this set if it exists.
	 * @param a The address to remove from this set.
	 * @return true If this set contained the specified address and was removed, false otherwise.
	 */
	public synchronized boolean removeElement(String a)
	{
		return super.removeElement(a);
	}



	/**
	 * Removes the specified addresses from this set if they exist.
	 * @param addresses The address to remove from this set.
	 * @return true If all the addresses were removed, false otherwise (if any of the addresses
	 * did not exist in this set).
	 */
	public boolean removeElements(Address[] addresses)
	{
		boolean result = true;

		for (Address a: addresses)
			result &= this.removeElement( a.toString() );

		return result;
	}
}