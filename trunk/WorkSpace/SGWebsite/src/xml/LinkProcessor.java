package xml;

import java.util.Vector;

/**
 * Fetches linked documents (with the {@link XMLTag#link link}) tag.<br>
 * This keeps track of the loaded documents, and if multiple requests are done for the same document, it does not load that document multiple times, but returns the stored version.
 * @author Zjef
 * @version 3.0
 */
public class LinkProcessor
{
	private Vector<XMLDocument> docs;
	
	protected LinkProcessor()
	{
		docs=new Vector<XMLDocument>(0);
	}
	
	/**
	 * @return a string with all the links replaced by their value
	 */
	protected String process(String text)
	{
		text=text.replaceAll(XMLTag.linkDelimiterChange,XMLTag.linkDelimiter);
		while (text.contains("§"))
		{
			int index=text.indexOf(XMLTag.linkDelimiter);
			String link=text.substring(index,text.indexOf(XMLTag.linkDelimiter,index+1)+1);
			int index2=link.indexOf('.');
			XMLDocument doc=getLinkedXMLDocument(link.substring(1,index2));
			XMLElement element=doc.getElement(link.substring(index2+1,link.length()-1));
			if (element instanceof ElementWithValue)
			{
				text=text.replace(link,element.toString());
			}
			else
			{
				text=text.replace(link,XMLTag.errorLoadingLink);
			}
		}
		return text;
	}
	
	private XMLDocument getLinkedXMLDocument(String filename)
	{
		for (XMLDocument i:docs)
		{
			if (i.getFileName().equals(filename))
			{
				return i;
			}
		}
		XMLDocument d=new XMLDocument(filename);
		d.load();
		docs.add(d);
		return d;
	}
}