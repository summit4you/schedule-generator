package language;

import java.io.IOException;

import java.text.ParseException;
import java.util.Hashtable;

import other.FileIO;

/**
 * <b>HashTable containing LanguageTags as keys and Strings as values </b> </br>
 * A dictionary is used by a LanguageResolver to translate language tags to a chosen
 * language. Normally Dictionaries are constructed from a a language file. These
 * files have a .l extension. Entries of a language file have to be from the following
 * form ##languageTag##=value; . The language of the dictionary will be the same as
 * the filename (without the extension).
 * 
 *  
 * 
 * @author Alexander
 * @version 2.0
 * @see {@link LanguageResolver}, {@link LanguageTag}
 */
public class Dictionary
{	
	private static final String excMessage=">>>Dictionary: Following language file could not be parsed: ";
	
	private String language;
	
	private Hashtable<String, String> content;
	
	Dictionary()
	{
		setLanguage("Undefined");
		this.setContent(new Hashtable<String, String>());
	}
	
	Dictionary(String language,String filePath) throws ParseException,IOException
	{
		this.setLanguage(language);
		this.setContent(loadContentFromFile(filePath));
	}
	
	/**
	* Used to construct the content of a dictionary out of a language file. If the 
	* language file can not be read a IOException is thrown. If an entry of the
	* file can not be parsed the entry is skipped and the stack trace is printed.
	* Other entries are still read.
	*/
	static private Hashtable<String, String> loadContentFromFile(String filePath) throws IOException
	{
		int index1=-1;
		int index2=-1;
		
		Hashtable<String, String> content=new Hashtable<String, String>();
		try
		{
			String file = FileIO.readFileWithException(filePath);
				while ((index1=file.indexOf(";"))!=-1)
				{
					try
					{
						String line = file.substring(0,index1);
						file=file.substring(index1+1);
						index2 = line.indexOf("=");
						content.put((line.substring(line.indexOf("##"), index2)),line.substring(index2+1,index1));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				return content;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IOException();
			
		}
	}
	

	/**
	 * Used to get the value corresponding to a given LanguageTag. If the LanguagTag
	 * is not present in the dictionary, the tag it self is returned. This makes
	 * that unresolved LanguageTags stay present in the text.
	 */
	public String lookUp(LanguageTag key)
	{
		if (content.get(key.toString())==null)
		{
			return key.toString();
		}
		else
		{
			return content.get(key.toString());
		}
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setContent(Hashtable<String, String> content)
	{
		this.content = content;
	}

	public Hashtable<String, String> getContent()
	{
		return content;
	}
	
	
}
