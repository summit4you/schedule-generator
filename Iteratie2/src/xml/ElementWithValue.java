package xml;

import java.io.Serializable;

/**
 * Represents an element of an xml file, without any children, but a certain value (which can be empty)
 * @author Zjef
 * @version 3.0
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
		super();
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
		return getHeader()+getValue()+getFooter();
	}

	@Override
	protected void processText(String text,XMLParser parser)
	{
		if (text.contains(XMLTag.linkDelimiter))
		{
			
		}
		setValue(text.substring(text.indexOf('>')+1,text.indexOf(getFooter())));
	}
	
	/**
	 * @return the same as {@link #getValue()}
	 */
	@Override
	public String toString()
	{
		return getValue();
	}
}