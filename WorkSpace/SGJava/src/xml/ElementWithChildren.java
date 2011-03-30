package xml;

import java.io.Serializable;
import java.util.Vector;

/**
 * XML element that has child elements (these can be {@link ElementWithValue} or ElementWithChildren itself)
 * @author Zjef
 * @version 3.0
 */
public class ElementWithChildren extends XMLElement implements Serializable
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
		super();
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
	 * @param name - name of the wanted child element of this document<br>
	 * This can include '.' to access child elements of the child element. Multiple '.' are supported.
	 * @return The element with the same name, or <code>null</code> if none of the elements has this name
	 */
	public XMLElement getChild(String name)
	{
		return Utils.getElement(name,getElements());
	}
	
	@Override
	protected String getFileText()
	{
		String text=getHeader()+'\n';
		for (XMLElement i:children)
		{
			text+=tab+i.getFileText().replaceAll("\n","\n"+tab)+"\n";
		}
		text+=getFooter();
		return text;
	}

	@Override
	protected void processText(String text,XMLParser parser)
	{
		addChildren(parser.parseText(text.substring(text.indexOf('>')+1,text.indexOf("</"+text.substring(1,text.indexOf(">")+1)))));
	}
}