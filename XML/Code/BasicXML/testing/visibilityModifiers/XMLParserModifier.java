package visibilityModifiers;

import java.util.Vector;

import xml.XMLElement;
import xml.XMLParser;

/**
 * @author Zjef
 * @version 1.0
 */
public class XMLParserModifier extends XMLParser
{
	@Override
	public Vector<XMLElement> parseText(String text)
	{
		return super.parseText(text);
	}
}