package language;

import java.util.zip.DataFormatException;

import sessionTracking.SessionCleaner;
import sessionTracking.SessionTable;

/**
 * <b>Class representing a language tag </b> </br>
 * A language tag is a string that has to be replaced by a languageResolover and is 
 * used as a key in a Dictionary. The format of a language tag is fixed and will be
 * checked every time a tag is constructed or changed. If the format is not met
 * a DataFormatException will be thrown </br></br>
 *  
 *  The current format is  ##tag##. 
 * 
 * @author Alexander
 * @version 1.0
 * @see {@link LanguageResolver}, {@link Dictionary}
 */
public class LanguageTag
{
	
	private static final String excMessage=">>>LanguageTag: Wrong tag format";
	
	public static final String tagStart="##";
	public static final String tagEnd="##";
	
	private String tag;
	
	LanguageTag(String tag) throws DataFormatException
	{
		setTag(tag);
	}
	
	/**
	* Checks if the String corresponds to the LanguageFile format
	*/
	public static boolean checkTagFormat(String possibleTag)
	{
		return (possibleTag.startsWith(tagStart) && possibleTag.endsWith(tagEnd));
	}

	public void setTag(String tag) throws DataFormatException
	{
		if (checkTagFormat(tag))
		{
			this.tag=tag;
		}
		else
		{
			throw new DataFormatException(excMessage); 
		}
	}

	@Override
	public String toString()
	{
		return tag;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return this.toString().equals(obj.toString());
	}
}
