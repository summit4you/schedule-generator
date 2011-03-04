package xml;

import java.io.Serializable;
import java.util.Vector;

/**
 * Element of a xml file.<br>
 * Use the subclasses {@link ElementWithChildren} or {@link ElementWithValue} as elements for an {@link XMLDocument}
 * @author Zjef
 * @version 2.0
 */
abstract class XMLElement implements Serializable
{	
	private String name;
	protected Vector<XMLTag> tags;
	
	protected XMLElement(String name)
	{
		setName(name);
		tags=new Vector<XMLTag>(0);
	}
	
	protected XMLElement()
	{
		tags=new Vector<XMLTag>(0);
	}
	
	/**
	 * Adds an {@link XMLTag} to this XMLElement.<br>
	 * If this tag cannot be added to this type of XMLElement (set by {@link XMLTag#forElementWithChildren} and {@link XMLTag#forElementWithValue}), then this method ignores the command.
	 * @param tag - tag to be added to this XMLElement
	 */
	public void addTag(XMLTag tag)
	{
		if (tag==null)
		{
			return;
		}
		if (((this instanceof ElementWithValue)&&tag.forElementWithValue)||((this instanceof ElementWithChildren)&&tag.forElementWithChildren))
		{
			tags.add(tag);
		}
	}
	
	/**
	 * Adds all tags in the vector.<br>
	 * See {@link #addTag(XMLTag)} for more information.
	 * @param tags - tags to add
	 */
	public void addTags(Vector<XMLTag> tags)
	{
		for (XMLTag i:tags)
		{
			addTag(i);
		}
	}
	
	/**
	 * @return <code>true</code> if this element contains the mentioned {@link XMLTag}, <code>false</code> otherwise.
	 */
	public boolean hasTag(XMLTag tag)
	{
		return tags.contains(tag);
	}
	
	/**
	 * Removes an {@link XMLTag} from this XMLElement.
	 * @param tag - tag to remove
	 */
	public void removeTag(XMLTag tag)
	{
		tags.remove(tag);
	}
	
	/**
	 * @return a copy of the Vector containing all {@link XMLTag XMLTags} added to this XMLElement
	 */
	public Vector<XMLTag> getTags()
	{
		return (Vector<XMLTag>) tags.clone();
	}
	
	protected String getHeader()
	{
		String text="<"+getName();
		for (XMLTag i:tags)
		{
			text+=" "+i.toString();
		}
		text+=">";
		return text;
	}
	
	protected String getFooter()
	{
		return getHeader().replaceFirst("<","</");
	}
	
	/**
	 * Initializes the component from a piece of text read from an xml file
	 * @param text
	 */
	abstract protected void processText(String text);
	
	protected static String extractName(String text)
	{
		if (text.contains(XMLTag.tagDelimiter))
		{
			return text.substring(text.indexOf('<')+1,text.indexOf(XMLTag.tagDelimiter)-1);
		}
		else
		{
			return text.substring(text.indexOf('<')+1,text.indexOf('>'));
		}
	}
	
	protected static Vector<XMLTag> extractTags(String text)
	{
		Vector<XMLTag> tags=new Vector<XMLTag>();
		text=text.substring(0,text.indexOf('>')+1)+" ";
		while (text.contains(XMLTag.tagDelimiter))
		{
			text=text.substring(text.indexOf(XMLTag.tagDelimiter),text.length());
			int index1=text.indexOf(' ');
			int index2=text.indexOf('>');
			tags.add(XMLTag.stringToTag(text.substring(text.indexOf(XMLTag.tagDelimiter),Math.min(index1,index2))));
			text=text.substring(1);
		}
		return tags;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	/**
	 * @return String representation of this <code>XMLElement</code>
	 */
	abstract protected String getFileText();
	
	/**
	 * @return a <code>String</code> representing this <code>XMLElement</code>.<br>
	 * This includes its name and value (in case of {@link ElementWithValue}) or child elements (in case of {@link ElementWithChildren})
	 */
	@Override
	public String toString()
	{
		return getFileText();
	}
}