package xmlTesting;

import java.util.Vector;

import visibilityModifiers.XMLElementModifier;
import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLTag;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Zjef
 * @version 1.0
 */
public class TestXMLElement extends TestCase
{
	private ElementWithValue val;
	private ElementWithChildren parent;
	
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestXMLElement.class);
		return suite;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		val=new ElementWithValue("test","5");
		parent=new ElementWithChildren("test");
	}
	
	/**
	 * Test if incompatible tags are discarded when trying to add them
	 */
	public void testAddTag()
	{
		val.addTag(XMLTag.code);
		parent.addTag(XMLTag.code);
		assertEquals(XMLTag.code.forElementWithValue,val.hasTag(XMLTag.code));
		assertEquals(XMLTag.code.forElementWithChildren,parent.hasTag(XMLTag.code));
		
		val.removeTag(XMLTag.code);
		parent.removeTag(XMLTag.code);
	}
	
	public void testExtractName()
	{
		assertEquals("name",XMLElementModifier.extractName("<name tag=code>blabla</name tag=code>"));
	}
	
	public void testExtractTags()
	{
		Vector<XMLTag> tags=XMLElementModifier.extractTags("<name tag=code tag=link>blabla</name tag=code tag=link>");
		assertEquals(XMLTag.code,tags.get(0));
		assertEquals(XMLTag.link,tags.get(1));
	}
}