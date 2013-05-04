/*
 * @(#)Direction.java	1.0	05/31/09
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
package com.canabang.genietext.transit.model.processor.dbase;

/**
 * A direction that a bus goes to. A direction may be the same a transit station.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class Direction
{
	/** The name of the direction. */
	private String name;


	/**
	 * Creates a direction object with the specified name.
	 * @param name The name value of the direction.
	 */
	public Direction(String name)
	{
		this.name =  name;
	}


	/**
	 * Retrieves the name value of the direction.
	 * @return The name of the direction a bus goes towards.
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Sets the name of this direction to the specified value.
	 * @param name The new direction value.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}