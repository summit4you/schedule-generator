package xml;

import java.util.Vector;

/**
 * Provides a static method {@link #parseText} to load an xml file created with this jar
 * @author Zjef
 * @version 1.0
 */
class XMLParser
{
	/**
	 * @param text - String of the text in the XML file
	 * @return - vector of <code>XMLElement</code> described in the text
	 */
	protected static Vector<XMLElement> parseText(String text)
	{
		Vector<XMLElement> elements=new Vector<XMLElement>();
		
		int startIndex;
		while ((startIndex=text.indexOf('<'))!=-1)
		{
			text=text.substring(startIndex);
			String part;
			String elementText=text.substring(0,text.indexOf(part=("</"+text.substring(1,text.indexOf(">")+1)))+part.length());
			text=text.substring(elementText.length(),text.length());
			
			XMLElement element;
			if (elementText.substring(0,elementText.length()-part.length()).contains("</"))
			{
				element=new ElementWithChildren();
			}
			else
			{
				element=new ElementWithValue();
			}
			element.processText(elementText);
			elements.add(element);
		}
		return elements;
	}
}