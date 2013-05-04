/*
 * @(#)HelpRequest.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.processor.requests;

import com.canabang.genietext.core.model.processor.requests.Request;


/**
 * In charge of all the clarification of the program to the user. It will display the appropriate
 * help messages.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class HelpRequest implements Request
{
	/**
	 * Creates a HelpRequest object allowing the help to be accessed.
	 */
	public HelpRequest()
	{
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.Request#process()
	 */
	public String process()
	{
		return "Examples:\n"
		+"121 8894: Time the next 121 is coming to stop 8894.\n"
		+"121 8894 5: Next five times the next 121 is coming to stop 8894.\n"
		+"121 8894 1615: Time the next 121 is coming to stop 8894 after 4:15pm\n"
		+"121 8894 1615 5: Next five times the next 121 is coming to stop 8894 after 4:15pm\n"
		+"121 8894 u: Get the full schedule for the 84 at stop 8894.\n"
		+"121 8894 l: Get the last time the 121 is coming to stop 8894.\n"
		+"121 8894 f: Get the first time the 121 is coming to stop 8894.\n"
		+"121 8894 s f: First time 121 comes to 8894 on Saturdays.\n"
		+"121 8894 s u: Full schedule for 121 for Saturdays.\n"
		+"121 8894 1615 s 5: Next five times the next 121 is coming to stop 8894 after 4:15pm on a Saturday.\n"
		+"Walkley.Jasper: Bus stops numbers near the intersection of Walkley/Jasper and their respective buses.\n"
		+"Billings Bridge Stop: Bus stop number for the Billings Bridge station and the respective buses they contain.\n"
		+"1120 Merivale Rd to 1100 Bank St: How to get from 1120 Merivale Road to 1100 Bank Street.\n"
		+"8894: Buses that are in the 8894 stop.\n"
		+"sub: Subscribe for transit service updates."
		+"unsub: Unsubscribe from transit service updates.";
	}
}