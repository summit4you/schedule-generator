package HTMLBuilder2;

import xml.ElementWithValue;
import xml.XMLDocument;
import xml.XMLElement;

/**
 * Represents a object that is dependable of a XML file.
 * @author Alexander
 * @version 0.1
 */
public class Template
{
	protected XMLDocument doc;
	protected XMLElement format;
	
	public Template()
	{
		createFormat();
	}
	
	public Template(String fileName)
	{
		loadDoc(fileName);
		createFormat();
	}
	
	/**
	 *  Loads the xml file specified by filename
	 */
	public boolean loadDoc(String fileName)
	{
		doc.setFileName(fileName);
		return doc.load();
	}
		
	public XMLElement getFormat()
	{
		return format;
	}
	
	/**
	 *  Creates a default xml file
	 */
	public boolean createDefaultDoc(String fileName)
	{
		XMLDocument defaultDoc = new XMLDocument(fileName);
		defaultDoc.addElement(format);
		return defaultDoc.write();
	}
	
	/**
	 *  Checks if the loaded xml file is conform to the expected format.</br>
	 *  <center>!!!Not yet implemented!!! </center>
	 */
	public boolean checkDoc()
	{	
		return false;
	}
	
	/**
	 *  Creates the format of this class. Should be overridden by 
	 *  subclasses.
	 */
	protected void createFormat()
	{
		format = new ElementWithValue("Template"," ");
	}
}
