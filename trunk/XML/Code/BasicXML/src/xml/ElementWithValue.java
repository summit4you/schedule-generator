package xml;

import java.io.Serializable;

/**
 * Represents an element of an xml file, without any children, but a certain value (which can be empty)
 * @author Zjef
 * @version 1.1
 */
public class ElementWithValue extends XMLElement implements Serializable
{
	private String value;
	
	public ElementWithValue(String name,String value)
	{
		super(name);
		setValue(value);
	}
	
	/**
	 * Constructor used by parser
	 */
	protected ElementWithValue()
	{
		
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
	
	@Override
	protected String getFileText()
	{
		return "<"+getName()+">"+value+"</"+getName()+">";
	}

	@Override
	protected void processText(String text)
	{
		extractName(text);
		setValue(text.substring(text.indexOf('>')+1,text.indexOf("</")));
	}
}