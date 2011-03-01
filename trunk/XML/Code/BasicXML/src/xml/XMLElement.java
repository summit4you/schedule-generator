package xml;

/**
 * Element of a xml file.<br>
 * Use the subclasses {@link ElementWithChildren} or {@link ElementWithValue} as elements for an {@link XMLDocument}
 * @author Zjef
 * @version 1.0
 */
abstract class XMLElement
{	
	private String name;
	
	protected XMLElement(String name)
	{
		setName(name);
	}
	
	protected XMLElement()
	{
		
	}
	
	/**
	 * Initializes the component from a piece of text read from an xml file
	 * @param text
	 */
	abstract protected void processText(String text);
	
	/**
	 * extracts the name from the piece of text and calls {@link #setName}
	 * @param text
	 */
	protected void extractName(String text)
	{
		setName(text.substring(text.indexOf('<')+1,text.indexOf('>')));
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