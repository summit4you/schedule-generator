package xmlTesting;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite of all tests
 * @author Zjef
 * @version 1.0
 */
public class AllTests extends TestSuite
{

	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for xmlTesting");
		//$JUnit-BEGIN$
		suite.addTest(TestXMLElement.suite());
		suite.addTest(TestXMLDocument.suite());
		suite.addTest(TestLinkProcessor.suite());
		suite.addTest(TestXMLParser.suite());
		suite.addTest(TestInterface.suite());
		//$JUnit-END$
		return suite;
	}
}