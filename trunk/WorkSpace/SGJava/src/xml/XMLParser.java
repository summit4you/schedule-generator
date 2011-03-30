package xml;

import java.util.Vector;

/**
 * Parses text, from an XML file, to create a list of XMLElements declared in that file.
 * @author Zjef
 * @version 3.0
 */
class XMLParser
{	
	private LinkProcessor linkProcessor;
	
	public XMLParser()
	{
		linkProcessor=new LinkProcessor();
	}
	
	public void finish()
	{
		linkProcessor=null;
	}
	
	public LinkProcessor getLinkProcessor()
	{
		return linkProcessor;
	}
	
	/**
	 * @param text - String of the text in the XML file
	 * @return - vector of <code>XMLElement</code> described in the text
	 */
	protected Vector<XMLElement> parseText(String text)
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
			String name=XMLElement.extractName(elementText);
			Vector<XMLTag> tags=XMLElement.extractTags(elementText);
			
			if (tags.contains(XMLTag.link))
			{
				elementText=linkProcessor.process(elementText);
			}
			
			if ((!tags.contains(XMLTag.code))&&elementText.substring(0,elementText.length()-part.length()).contains("</"))
			{
				element=new ElementWithChildren(name);
			}
			else
			{
				element=new ElementWithValue(name,"");
			}
			element.addTags(tags);
			element.processText(elementText,this);
			elements.add(element);
		}
		return elements;
	}
	
//	/**
//	 * @param text - String of the text in the XML file
//	 * @return - vector of <code>XMLElement</code> described in the text
//	 */
//	protected static Vector<XMLElement> parseText(String text)
//	{
//		Vector<XMLElement> elements=new Vector<XMLElement>();
//		
//		int startIndex;
//		while ((startIndex=text.indexOf('<'))!=-1)
//		{
//			text=text.substring(startIndex);
//			String part;
//			String elementText=text.substring(0,text.indexOf(part=("</"+text.substring(1,text.indexOf(">")+1)))+part.length());
//			text=text.substring(elementText.length(),text.length());
//			
//			XMLElement element;
//			String name=XMLElement.extractName(elementText);
//			
//			Vector<XMLTag> tags=XMLElement.extractTags(elementText);
//			if ((!tags.contains(XMLTag.code))&&elementText.substring(0,elementText.length()-part.length()).contains("</"))
//			{
//				element=new ElementWithChildren(name);
//			}
//			else
//			{
//				element=new ElementWithValue(name,"");
//			}
//			element.addTags(tags);
//			element.processText(elementText);
//			elements.add(element);
//		}
//		return elements;
//	}
}