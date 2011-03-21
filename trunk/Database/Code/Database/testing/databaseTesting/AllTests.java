package databaseTesting;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite of all tests
 * @author Zjef
 * @version 1.0
 */
public class AllTests
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTest(TestExtraction.suite());
		suite.addTest(TestDatabase.suite());
		//$JUnit-END$
		return suite;
	}
}