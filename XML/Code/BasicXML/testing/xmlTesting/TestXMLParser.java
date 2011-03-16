package xmlTesting;

import java.util.Vector;

import visibilityModifiers.XMLParserModifier;
import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLElement;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Zjef
 * @version 1.0
 */
public class TestXMLParser extends TestCase
{
	private XMLParserModifier parser;
	
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestXMLParser.class);
		return suite;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		parser=new XMLParserModifier();
	}
	
	public void testParseText()
	{
		Vector<XMLElement> elements=parser.parseText("<el1><el2>value</el2></el1> <el3>value</el3>");
		
		assertTrue(elements.get(0) instanceof ElementWithChildren);
		assertTrue(elements.get(1) instanceof ElementWithValue);
		assertEquals("el1",elements.get(0).getName());
		assertEquals("el3",elements.get(1).getName());
		assertEquals("value",elements.get(1).toString());
		assertEquals("value",((ElementWithChildren)elements.get(0)).getChild("el2").toString());
	}
}