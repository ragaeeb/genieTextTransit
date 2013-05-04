/*
 * @(#)DottedIntersectionTest.java	1.0	05/31/09
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
package com.canabang.genietext.transit.tests.model.io;

import static org.junit.Assert.assertEquals;
import com.canabang.genietext.transit.model.processor.dbase.DottedIntersection;
import com.canabang.genietext.transit.model.processor.dbase.Intersection;
import org.junit.Test;


/**
 * Tests the methods of the DottedIntersection class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class DottedIntersectionTest
{
	/** The instance of the class to test. */
	private Intersection i1;


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.dbase.Intersection#toString()}.
	 */
	@Test
	public void testToString()
	{
		i1 = new DottedIntersection("Walkley.Jasper");
		assertEquals( "Walkley/Jasper", i1.toString() );

		i1 = new DottedIntersection("Walkley.ARR T");
		assertEquals( "Walkley", i1.toString() );

		i1 = new DottedIntersection("Walkley");
		assertEquals( "Walkley", i1.toString() );

		i1 = new DottedIntersection("Walkley….Jasper");
		assertEquals( "WalkleyE/Jasper", i1.toString() );
	}
}