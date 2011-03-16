package xmlTesting;

import visibilityModifiers.LinkProcessorModifier;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Zjef
 * @version 1.0
 */
public class TestLinkProcessor extends TestCase
{
	private LinkProcessorModifier processor;
	
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestLinkProcessor.class);
		return suite;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		processor=new LinkProcessorModifier();
	}
	
	public void testProcess()
	{
		assertEquals("Value is 100, the other one is 1000",processor.process("Value is §testLinks.value§, the other one is §testLinks.parent.child§"));
	}
}