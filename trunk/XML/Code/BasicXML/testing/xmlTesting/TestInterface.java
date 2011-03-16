package xmlTesting;

import java.io.File;

import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the overall functions; reading a document, writing a document
 * @author Zjef
 * @version 1.0
 */
public class TestInterface extends TestCase
{
	/**
	 * Filename of file used for testing reading
	 */
	final private static String testReadDoc="testRead.xml";
	final private static String testWriteDoc="testWrite.xml";
	
	private XMLDocument docRead;
	private XMLDocument docWrite;
	
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestInterface.class);
		return suite;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		docRead=new XMLDocument(testReadDoc);
		docWrite=new XMLDocument(testWriteDoc);
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		docRead=null;
		docWrite=null;
		new File(testWriteDoc).delete();
	}
	
	public void testReading()
	{
		assertTrue(docRead.load());
		assertNotNull(docRead.getElement("html"));
		assertNotNull(docRead.getElement("parent.child"));
		assertNotNull(docRead.getElement("parent.child1"));
		assertNotNull(docRead.getElement("parent2.child1.child2"));
		assertEquals("1",docRead.getElement("val1").toString());
		assertEquals("",docRead.getElement("parent.child1").toString());
		assertEquals("value",docRead.getElement("parent.child").toString());
		assertEquals("value",docRead.getElement("parent2.child1.child2").toString());
	}
	
	public void testWrite()
	{
		ElementWithValue el1=new ElementWithValue("el1","100");
		ElementWithChildren el2=new ElementWithChildren("el2");
		ElementWithValue el3=new ElementWithValue("el3","value");
		el2.addChild(el3);
		el2.addChild(el1);
		
		docWrite.addElement(el1);
		docWrite.addElement(el2);
		
		assertTrue(docWrite.write());
		assertTrue(docWrite.load());
		assertNotNull(docWrite.getElement("el1"));
		assertNotNull(docWrite.getElement("el2"));
		assertNotNull(docWrite.getElement("el2.el3"));
		assertNotNull(docWrite.getElement("el2.el1"));
		assertEquals("value",docWrite.getElement("el2.el3").toString());
	}
}