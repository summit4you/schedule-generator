package visibilityModifiers;

import java.util.Vector;
import xml.XMLElement;
import xml.XMLTag;

/**
 * @author Zjef
 * @version 1.0
 */
public abstract class XMLElementModifier extends XMLElement
{
	public static Vector<XMLTag> extractTags(String text)
	{
		return XMLElement.extractTags(text);
	}
	
	public static String extractName(String text)
	{
		return XMLElement.extractName(text);
	}
}