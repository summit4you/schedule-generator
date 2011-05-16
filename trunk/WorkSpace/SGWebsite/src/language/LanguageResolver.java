package language;

import java.io.File;
import java.util.Vector;
import java.util.zip.DataFormatException;

import other.Globals;

/**
 * <b>Static tool to resolve language tags used by the MainServlet</b> </br>
 * LanguageResolver translates text to a given language. This means he replaces
 * all language tags in the text by their corresponding value. For this purpose 
 * the languageResolver uses a set of dictionaries. These dictionaries are loaded
 * upon class construction.
 *  
 * 
 * @author Alexander 16/5 16:34
 * @version 1.1
 * @see {@link Dictionary}, {@link LanguageTag}
 */
public class LanguageResolver
{	
	static private Dictionary defaultDictionary=new Dictionary();
	
	static private  Vector<Dictionary> dictionaries=LoadDictionaries();
	
	static final public String languageFileExtension=".l";
		
	/**
	 * Constructs dictionaries from all language files present in the specified directory.
	 * If a language file can not be read or parsed ,it is skipped an the stack trace is 
	 * printed. Other files still get processed. The returned vector always contains one 
	 * dictionary. Empty dictionaries can be present.
	 */
	private static Vector<Dictionary> LoadDictionaries()
	{
		Vector<Dictionary> dic = new Vector<Dictionary>();
		try
		{
			for (File f:new File(Globals.languagePath).listFiles())
			{
				if (f!=null && f.isFile() && f.getName().endsWith(languageFileExtension))
				{
					try
					{
						dic.add(new Dictionary(f.getName().substring(0,f.getName().indexOf(".")),f.getAbsolutePath()));
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					} 
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if (dic.isEmpty())
		{
			dic.add(defaultDictionary);
		}
		return dic;
	}
	
	/**
	 * Method used to reload the dictionaries if language files are changed.
	 */
	public static void ReloadDictionaries()
	{
		dictionaries=LoadDictionaries();
	}
	
	public static Dictionary getDictionary(String language)
	{
		for(Dictionary d:dictionaries)
		{
			if (d.getLanguage().equals(language))
			{
				return d;
			}
		}
		return defaultDictionary;
	}
	
	public static boolean islanguageSupported(String language)
	{
		for(Dictionary d:dictionaries)
		{
			if (d.getLanguage().equals(language))
			{
				return true;
			}
		}
		return false;
	}
	
	public static Vector<String> supportedLanguages()
	{
		Vector<String> res = new Vector<String>();
		for(Dictionary d:dictionaries)
		{
			res.add(d.getLanguage());
		}
		return res;
	}
	
	/**
	 * Replaces all language tags in a text by their corresponding values.
	 * If there is no dictionary corresponding to the specified language
	 * the default dictionary is used (which is always not null). By default 
	 * the default dictionary is empty. Use its setter and getter to change
	 * it to an non empty dictionary.
	 */
	public static String translate(String text,String language)
	{
		Dictionary dic = getDictionary(language);
		
		String translatedText= new String();
		
		int index1=-1;
		
		try
		{
			while((index1=text.indexOf(LanguageTag.tagStart))!=-1)
			{
				translatedText=translatedText.concat(text.substring(0,index1));
				text=text.substring(index1);
				index1=text.substring(LanguageTag.tagStart.length()).indexOf(LanguageTag.tagEnd);
				translatedText=translatedText.concat(dic.lookUp(new LanguageTag(text.substring(0,index1+LanguageTag.tagEnd.length()+LanguageTag.tagStart.length()))));
				text=text.substring(index1+LanguageTag.tagStart.length()+LanguageTag.tagEnd.length());
			}
			translatedText=translatedText.concat(text);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return translatedText;
	}
	
	public static Dictionary getDefaultDictionary()
	{
		return defaultDictionary;
	}
	
	/**
	 *  Sets the default dictionary which is used if a language isn't supported
	 *  If null is given as a parameter nothing will happened the previous default
	 *  is kept
	 */
	public static void setDefaultDictionary(Dictionary dic)
	{
		if (defaultDictionary!=null)
		{
			defaultDictionary = dic;
		}
	}
	
}
