/*
 * @(#)PatternReplace.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.io;

import java.util.Hashtable;


/**
 * Maps special character patterns with their closest English look-alike.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public final class PatternReplace extends Hashtable<String, String>
{
	/** Used if this class were to be serialized. */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates an instance of this class which populates the necessary patterns and their
	 * closest English look-alike so they can be mapped on demand.
	 */
	public PatternReplace()
	{
		super();

		this.put("[… »À]", "E");
		this.put("[¿¡¬√ƒ≈]", "A");
		this.put("[«]", "C");
		this.put("[›]", "Y");
		this.put("[Ÿ⁄€‹]", "U");
		this.put("[“”‘’÷]", "O");
	}
}