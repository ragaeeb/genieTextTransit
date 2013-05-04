/*
 * @(#)Controller.java	1.0	05/12/09
 * @(#)Controller.java	1.1	05/31/09
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
package com.canabang.genietext.core.controller;

import com.canabang.genietext.core.model.SmsServiceProvider;
import com.canabang.genietext.core.model.io.xml.XmlParametersParser;
import com.canabang.genietext.core.model.structs.Parameters;
import com.canabang.genietext.core.view.SmsServicesGraphicalView;
import com.canabang.genietext.core.view.SmsServicesView;
import com.canabang.genietext.transit.model.SmsTransitServiceProvider;
import java.io.File;
import javax.mail.MessagingException;
import javax.xml.transform.TransformerConfigurationException;


/**
 * Entry-point in the system. Initializes service. Services can be started either by passing
 * input XML files into the constructor as an array, or by calling the startService() method
 * on demand. The view of this MVC controller should call the startService() method to start
 * a service with the parameters given by the user.
 *
 * @author rhaq
 * @version 1.00 2009-05-12 Initial submission.
 * @version 1.10 2009-05-31 Now only deals with SmsServiceProvider objects so that multiple services can
 * be instantiated simultaneously in a more object oriented manner. Time military format control
 * feature added. No longer works with ConnectionInfo, but rather Parameters objects and input
 * files.
 */
public class Controller
{
	/**
	 * Starts the view reference of this MVC model and waits for inputs from the view to start
	 * the desired services.
	 */
	public Controller()
	{
		try {
			SmsServicesView view = new SmsServicesGraphicalView("Transit", this);
			new Thread(view).start();
		}

		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}


	/**
	 * Creates an instance of this class and processes the input files specified by calling
	 * the model reference of the MVC pattern. This method of starting the program does not
	 * interact with the view at all except to dump out error messages.
	 * @param inputFiles The input files containing the parameters needed to start services to
	 * be processed by the model.
	 */
	public Controller(String[] inputFiles)
	{
		for (String inputFile: inputFiles)
		{
			try
			{
				XmlParametersParser x = new XmlParametersParser();
				Parameters p = x.parse( new File(inputFile) );
				startService(p);
			}

			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}


	/**
	 * Starts the service with the specified parameters.
	 * @param p The parameters required to start the service.
	 * @return An instance of the service started.
	 * @throws MessagingException If there is a problem connecting to the remote service or in the
	 * event that the server could not authenticate the connection or the required folder(s)
	 * could not be opened/accessed.
	 * @throws TransformerConfigurationException If an output XML file could not be properly
	 * configured and set up to output the subscription list.
	 */
	public static SmsServiceProvider startService(Parameters p) throws TransformerConfigurationException, MessagingException
	{
		SmsTransitServiceProvider smsTransit = new SmsTransitServiceProvider( p.getClockFormat(), p.getImap(), p.getSmtp(), p.getSubscriptionOutput() );
		new Thread(smsTransit).start();

		return smsTransit;
	}


	/**
	 * Primary-entry point in the system.
	 * @param args List of input files containing the parameters of the services to start.
	 */
	public static void main(String[] args)
	{
		if (args.length > 0)
			new Controller(args);

		else // user provided no inputs
			new Controller();
	}
}