package com.canabang.genietext.transit.tests.model.processor;

import static org.junit.Assert.assertEquals;
import com.canabang.genietext.transit.model.processor.dbase.TransitTime;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the methods of the TransitTime class.
 *
 * @author rhaq
 * @version 1.00 2009-05-31 Initial submission.
 */
public class TransitTimeTest
{
	/** An instance of the class to test. */
	private TransitTime t1;

	/** An instance of the class to test. */
	private TransitTime t2;

	/** An instance of the class to test. */
	private TransitTime t3;

	/** An instance of the class to test. */
	private TransitTime t4;


	/**
	 * Sets up the environment for testing.
	 * @throws java.lang.Exception If there are any problems setting up the environment.
	 */
	@Before
	public void setUp() throws Exception
	{
		t1 = new TransitTime(1033);
		t2 = new TransitTime(2311);
		t3 = new TransitTime(0011);
		t4 = new TransitTime(359);
	}


	/**
	 * Test method for {@link com.canabang.genietext.transit.model.processor.dbase.TransitTime#compareTo(com.canabang.genietext.transit.model.processor.dbase.TransitTime)}.
	 */
	@Test
	public void testCompareToTransitTime()
	{
		assertEquals(true, t1.compareTo(t2) < 0 );
		assertEquals(true, t2.compareTo(t1) > 0 );
		assertEquals(true, t3.compareTo(t2) > 0 );
		assertEquals(true, t4.compareTo(t3) > 0 );
		assertEquals(true, t4.compareTo(t2) > 0 );
		assertEquals(true, t4.compareTo(t1) > 0 );
		assertEquals(true, t1.compareTo(t3) < 0 );
		assertEquals(true, t1.compareTo(t4) < 0 );
	}
}