/*
 * @(#)SubscribeRequest.java	1.0	05/31/09
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
package com.canabang.genietext.core.model.processor.requests;

import com.canabang.genietext.core.model.structs.AddressSet;
import javax.mail.Address;


/**
 * Represents a request for a user to subscribe to the service's subscription list.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class SubscribeRequest extends SubscriptionRequest
{
	/** Response sent to the user once the request was successfully processed. */
	private static final String RESPONSE = "Added";

	/**
	 * Creates a subscription request to add the specified addresses to the set of subscribers
	 * if they don't already exist.
	 * @param subscription The list of current subscribers for the service.
	 * @param sender The addresses to add to the list of subscribers if they don't already exist.
	 */
	public SubscribeRequest(AddressSet subscription, Address[] sender)
	{
		super(subscription, sender);
	}


	/* (non-Javadoc)
	 * @see com.canabang.genietext.core.model.processor.requests.Request#process()
	 */
	public String process()
	{
		this.subscription.addElements(sender);

		return RESPONSE;
	}
}