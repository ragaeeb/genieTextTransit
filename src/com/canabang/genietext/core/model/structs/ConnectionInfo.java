/*
 * @(#)ConnectionInfo.java	1.0	05/31/09
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

/**
 * Stores and maintains connection information to a remote service for later retrieval.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009-05-31 Removed specific smtp and imap fields and changed them into a more generic
 * username, password, host, and port field.
 */
public class ConnectionInfo
{
	/** The host address of the server. */
	private String host;

	/** The port number used to access the server. */
	private int hostPort;

	/** The password used to authenticate to the server. */
	private String password;

	/** The username used to authenticate to the server. */
	private String username;


	/**
	 * Creates a connection information object that will store the specified remote connection
	 * information for later retrieval.
	 * @param password The password used to authenticate to the server.
	 * @param username The username used to authenticate to the server.
	 * @param host The host address of the server.
	 */
	public ConnectionInfo(String username, String password, String host)
	{
		super();
		this.password = password;
		this.username = username;
		this.host = host;
		this.hostPort = 0;
	}


	/**
	 * Creates a connection information object that will store the specified remote connection
	 * information for later retrieval.
	 * @param password The password used to authenticate to the server.
	 * @param username The username used to authenticate to the server.
	 * @param host The host address of the server.
	 * @param hostPort The port number used to access the server.
	 */
	public ConnectionInfo(String username, String password, String host, int hostPort)
	{
		super();
		this.password = password;
		this.username = username;
		this.host = host;
		this.hostPort = hostPort;
	}


	/**
	 * Retrieves the host address for the server.
	 * @return The address required to access the server.
	 */
	public String getHost()
	{
		return host;
	}


	/**
	 * Retrieves the port number needed to access the server.
	 * @return The port number used to connect to the server.
	 */
	public int getHostPort()
	{
		return hostPort;
	}


	/**
	 * Retrieves the password required to authenticate to the server.
	 * @return The password needed to log into both the server.
	 */
	public String getPassword()
	{
		return password;
	}


	/**
	 * Retrieves the username required to authenticate to the server.
	 * @return The username needed to log into the server.
	 */
	public String getUsername()
	{
		return username;
	}


	/**
	 * Sets the hostPort to the specified value.
	 * @param hostPort The hostPort to set.
	 */
	public void setHostPort(int hostPort)
	{
		this.hostPort = hostPort;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "username: "+username+", password: "+password+"\n"
		+"host: "+host+":"+hostPort;
	}
}