package xml;

/**
 * Tag enums for an {@link XMLElement}.<br>
 * An XMLElement can have a tag associated with it, to give it extra properties depending on the tag.
 * @author Zjef
 * @version 3.0
 */
public enum XMLTag
{
	/**
	 * Used when writing a {@link XMLDocument}.<br>
	 * This tag has to be added to an {@link ElementWithValue} when its value contains text that could be interpreted as XML tags.<br>
	 * An example would be an ElementWithValue that contains HTML code as its value. HTML code contains text between brackets <>. This would interfere with the
	 * basic XML Parser included in this package, resulting in errors while trying reading the file. The HTML tags in the value would be treated as XML elements.<br>
	 * When this tag is added however, HTML code can be safely stored in an ElementWithValue.<br><br>
	 * This Tag can only be added to an ElementWithValue.
	 */
	code(false,true),
	
	/**
	 * This enables links between xml files.<br>
	 * When the parser then encounters § §, it will open the specified file and read the specified value.<br>
	 * Example of xml file: <br>
	 * &ltinfo&gt My name is §data.name§ and my age is §data.age§ &lt/info&gt<br>
	 * This will then insert the values stored in the fields 'name' and 'age' in 'data.xml'. So '§data.name§' will be replaced by for example '21' while loading the XML file<br><br>
	 * Nested elements (children of {@link ElementWithChildren}) can be retrieved by adding '.'.<br>
	 * For example: §data.myData.age§, will retrieve the value of the element 'age' inside the element 'myData' in the file 'data.xml'.<br><br>
	 * {@value #errorTextLoading} will be inserted if the specified link does not exist.
	 * This Tag can only be added to an ElementWithValue.
	 */
	link(false,true);
	
	final protected static String tagDelimiter="tag=";
	
	/**
	 * This should be placed around a link, see {@link #link}
	 */
	final public static String linkDelimiter="§";
	
	/**
	 * The character § is sometimes stored as two characters Â§
	 */
	final protected static String linkDelimiterChange="Â§";
	
	/**
	 * Text that will be displayed when a link (see {@link #link}) is invalid.
	 */
	final public static String errorLoadingLink=" $$$ Error loading XML link $$$";
	
	/**
	 * <code>true</code> if this tag can be added to an {@link ElementWithChildren}, <code>false</code> otherwise.
	 */
	final public boolean forElementWithChildren;
	
	/**
	 * <code>true</code> if this tag can be added to an {@link ElementWithValue}, <code>false</code> otherwise.
	 */
	final public boolean forElementWithValue;
	
	private XMLTag(boolean forElementWithChildren,boolean forElementWithValue)
	{
		this.forElementWithChildren=forElementWithChildren;
		this.forElementWithValue=forElementWithValue;
	}
	
	protected static XMLTag stringToTag(String tag)
	{
		for (XMLTag i:XMLTag.class.getEnumConstants())
		{
			if (i.toString().equals(tag))
			{
				return i;
			}
		}
		return null;
	}
	
	/**
	 * @return "tag="+super.toString()<br>
	 * (the standard enum toString() simply returns the enum's name)
	 */
	@Override
	public String toString()
	{
		return tagDelimiter+super.toString();
	}
}