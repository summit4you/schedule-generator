package xml;

import java.util.Vector;

/**
 * XML element that has child elements (these can be {@link ElementWithValue} or ElementWithChildren itself)
 * @author Zjef
 * @version 1.0
 */
public class ElementWithChildren extends XMLElement
{
	final private static String tab="	 ";
	
	private Vector<XMLElement> children;
	
	
	public ElementWithChildren(String name)
	{
		super(name);
		children=new Vector<XMLElement>();
	}
	
	/**
	 * Constructor used by parser
	 */
	protected ElementWithChildren()
	{
		children=new Vector<XMLElement>();
	}
	
	/**
	 * This method returns a clone of the vector containing all the child elements.<br>
	 * So adding or removing elements from the returned vector have no effect on the child elements of this element.<br>
	 * Use {@link #addChild} and {@link #removeChild} for changing the child elements of this element.<br>
	 * The child elements in the vector are no copies.
	 * @return returns a clone of the vector containing all children of this element
	 */
	public Vector<XMLElement> getElements()
	{
		return (Vector<XMLElement>) children.clone();
	}
	
	public void addChild(XMLElement child)
	{
		children.add(child);
	}
	
	public void addChildren(Vector<XMLElement> children)
	{
		for (XMLElement i:children)
		{
			addChild(i);
		}
	}
	
	public void removeChild(XMLElement child)
	{
		children.remove(child);
	}
	
	/**
	 * @param name - name of the wanted child element
	 * @return The child element with the same name, or <code>null</code> if none of the children of this element has this name
	 */
	public XMLElement getChild(String name)
	{
		for (XMLElement i:children)
		{
			if (i.getName().equals(name))
			{
				return i;
			}
		}
		return null;
	}
	
	@Override
	protected String getFileText()
	{
		String text="<"+getName()+">"+'\n';
		for (XMLElement i:children)
		{
			text+=tab+i.getFileText().replaceAll("\n","\n"+tab).concat("\n");
		}
		text+="</"+getName()+">";
		return text;
	}

	@Override
	protected void processText(String text)
	{
		extractName(text);
		addChildren(XMLParser.parseText(text.substring(text.indexOf('>')+1,text.indexOf("</"+text.substring(1,text.indexOf(">")+1)))));
	}
}