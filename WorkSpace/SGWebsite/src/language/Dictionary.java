package language;

import java.util.Hashtable;
import java.util.Vector;

import login.Account;
import other.FileIO;
/**
 * <b> Class that resolves language tags </b> </br>
 * A dictionary can be used to replace language tags that are present in the language file.
 * A language tag is of the following form '#tag#' and should be unique. An entry in a
 * language file should be as followed '#tag#=replacement;'. Every language will have its 
 * own file with tags. Now only English is supported. 
 *  
 * @author Alexander
 * @version 1.0
 */

public class Dictionary
{
	public static enum Language {english,dutch};
	final public static String languageFileExtension=".l";
	
	private String root;
	private Language language;
	
	private Hashtable<String, String> table;
		
	private void initTable()
	{
		table=new Hashtable<String, String>();
		String filePath=new String(root+language.toString()+languageFileExtension);
		String content = FileIO.readFile(filePath);
		
		int index1;
		int index2;
		
		while ((index1=content.indexOf(";"))!=-1)
		{
			String line = content.substring(0,index1);
			content=content.substring(index1+1);
			index2 = line.indexOf("=");
			table.put(line.substring(line.indexOf("#"), index2),line.substring(index2+1,index1));
		}
	}
 	
	public Dictionary(Language language,String root)
	{
		setRoot(root);
		setLanguage(language);
		
	}

	public void setRoot(String root)
	{
		this.root = root;
	}

	public String getRoot()
	{
		return root;
	}

	public void setLanguage(Language language)
	{
		this.language = language;
		initTable();
	}

	public Language getLanguage()
	{
		return language;
	}
	
	public String lookUp(String key)
	{
		return table.get(key);
	}
	
	public boolean checkTag(String key)
	{
		return table.containsKey(key);
	}
	
	public boolean checkValue(String value)
	{
		return table.containsValue(value);
	}
	
	public String replaceLanguageTag(String text,String tag)
	{
		return text.replaceAll(tag, table.get(tag));
	}
	
	public String replaceLanguageTags(String text,Vector<String> tags)
	{
		for(String t:tags)
		{
			text=replaceLanguageTag(text,t);
		}
		return text;
	}
	
	public String translatePage(String text)
	{
		int hekje1 = 0;
		int hekje2 = 1;
		while ((text.indexOf('#', hekje2+1)!=-1)&&(text.indexOf('#', hekje1+1)!=-1))
		{
			hekje1=text.indexOf('#', hekje2+1);
			hekje2=text.indexOf('#', hekje1+1);
			System.out.println("H1: "+hekje1+ "H2:"+hekje2);
			// alles tussen hekje1 en hekje2 is een language tag
			String tag = text.substring(hekje1+1,hekje2);
			text = text.replaceFirst("#"+tag+"#",table.get("#"+tag+"#"));
		}
		return text;
	}
}
