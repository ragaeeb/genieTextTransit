/*
 * @(#)SmsServicesView.java	1.0	05/31/09
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
package com.canabang.genietext.core.view;

import com.canabang.genietext.core.model.SmsServiceProvider;
import com.canabang.genietext.core.model.structs.Parameters;
import javax.mail.MessagingException;
import javax.swing.DefaultListModel;
import javax.xml.transform.TransformerConfigurationException;


/**
 * Represents the View object in the MVC pattern.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public interface SmsServicesView extends Runnable
{
	/**
	 * Sets the console to show the specified log.
	 * @param log Reference to the log that the console needs to display.
	 */
	public void setConsoleLog(DefaultListModel log);


	/**
	 * Brings the About window into focus.
	 */
	public void showAbout();


	/**
	 * Brings the console into focus.
	 */
	public void showConsolePanel();


	/**
	 * Brings the main panel into focus.
	 */
	public void showMainPanel();


	/**
	 * Brings the parameters panel into focus.
	 */
	public void showParametersPanel();


	/**
	 * Brings the subscribers panel into focus.
	 */
	public void showSubscribersPanel();


	/**
	 * Starts the target service with the specified parameters.
	 * @param params The parameters required to start the service.
	 * @return An instance of the service started.
	 * @throws MessagingException If there is a problem connecting to the remote service or in the
	 * event that the server could not authenticate the connection or the required folder(s)
	 * could not be opened/accessed.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up to output the subscription list.
	 */
	public SmsServiceProvider startService(Parameters params) throws TransformerConfigurationException, MessagingException;


	/**
	 * Terminates the target service.
	 */
	public void stopService();
}