package xml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Vector;

/**
 * Represents an xml file.<br>
 * This can be used to load ({@link #load}) an existing xml file or to write ({@link #write}) a new xml file.
 * @see ElementWithChildren
 * @see ChildlessElement
 * @see Child
 * @author Zjef
 * @version 3.0
 */
public class XMLDocument implements Serializable
{
	public static final String xmlExtension=".xml";
	
	private String filename;
	private Vector<XMLElement> elements;
	
	/**
	 * Creates a new document<br>
	 * This document can be written to an xml file:{@link #load}<br>
	 * or an be loaded with an existing xml file:{@link #write}<br>
	 * @param filename - file name (absolute or relative) of the xml file
	 */
	public XMLDocument(String filename)
	{
		this.setFileName(filename);
		this.elements=new Vector<XMLElement>(0);
	}
	
	/**
	 * @return <code>true</code> if the file specified by the filename of this document physically exists, else <code>false</code>
	 */
	public boolean fileExists()
	{
		return new File(filename).exists();
	}
	
	/**
	 * loads an xml file specified by the filename associated with this document.<br>
	 * The result can be retrieved with {@link #getElements}
	 * @return if the file specified by the filename does not exist or an error while reading occurred, this method returns <code>false</code>, else <code>true</code>.
	 */
	public boolean load()
	{
		try
		{
			FileReader in=new FileReader(filename);
			int c;
			String text="";
			while ((c=in.read())!=-1)
			{
				text+=(char)c;
			}
			XMLParser parser=new XMLParser();
			addElements(parser.parseText(text));
			parser.finish();
			in.close();
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Creates an xml file with the filename associated with this document.<br>
	 * All elements of this document will be written to the file.
	 * @return <code>true</code> if the write succeeded, else <code>false</code>
	 */
	public boolean write()
	{
		try
		{
			FileWriter out=new FileWriter(filename);
			out.write(toString());
			out.close();
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @return the same String as would be written to a file by {@link #write}
	 */
	@Override
	public String toString()
	{
		String text="";
		for (XMLElement i:elements)
		{
			text+=i.getFileText()+"\n\n";
		}
		return text;
	}

	/**
	 * Sets the file associated with this document.<br><br>
	 * This method automatically adds {@value #xmlExtension} to the filename if the extension is not present in the parameter 'fileName'.
	 * @param fileName
	 */
	public void setFileName(String fileName)
	{
		this.filename = fileName.endsWith(xmlExtension)?fileName:fileName+xmlExtension;
	}

	public String getFileName()
	{
		return filename;
	}
	
	public void addElement(XMLElement element)
	{
		this.elements.add(element);
	}
	
	public void addElements(Vector<XMLElement> elements)
	{
		for (XMLElement i:elements)
		{
			addElement(i);
		}
	}
	
	/**
	 * @param name - name of the wanted element of this document<br>
	 * This can include '.' to access child elements. Multiple '.' are supported.
	 * @return The element with the same name, or <code>null</code> if none of the elements has this name
	 */
	public XMLElement getElement(String name)
	{
		return Utils.getElement(name,getElements());
	}
	
	/**
	 * This method returns a clone of the vector containing all the elements.<br>
	 * So adding or removing elements from the returned vector have no effect on the elements of this document.<br>
	 * Use {@link #addElement} and {@link #removeElement} for changing the elements of this document.<br>
	 * The elements in the vector are no copies.
	 * @return returns a clone of the vector containing all elements of this document
	 */
	public Vector<XMLElement> getElements()
	{
		return (Vector<XMLElement>) elements.clone();
	}
	
	public void removeElement(XMLElement element)
	{
		this.elements.remove(element);
	}
}