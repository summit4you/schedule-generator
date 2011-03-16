package xmlTesting;

import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Zjef
 * @version 1.0
 */
public class TestXMLDocument extends TestCase
{
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestXMLDocument.class);
		return suite;
	}
	
	public void testGetElement()
	{
		XMLDocument doc=new XMLDocument("doc");
		doc.addElement(new ElementWithValue("element","100"));
		assertNotNull(doc.getElement("element"));
		assertEquals("100",doc.getElement("element").toString());
		
		ElementWithChildren parent=new ElementWithChildren("parent");
		parent.addChild(new ElementWithValue("child","200"));
		doc.addElement(parent);
		assertNotNull(doc.getElement("parent.child"));
		assertEquals("200",doc.getElement("parent.child").toString());
	}
	
	public void testFileName()
	{
		XMLDocument doc1=new XMLDocument("test");
		XMLDocument doc2=new XMLDocument("test.xml");
		assertEquals(doc1.getFileName(),doc2.getFileName());
	}
}