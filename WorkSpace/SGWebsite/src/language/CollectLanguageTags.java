package language;

import java.io.File;
import java.util.Vector;

import login.UserType;
import other.*;
import pseudoServlets.PseudoServlet;


public class CollectLanguageTags 
{
	private static Vector<String> datastructure;
	private static String datastructurepath;
	private static Vector<String> pseudoservlets;
	private static String pseudoservletpath;
	private static Vector<String> templatefiles;
	private static String templatefilepath;
	private static Vector<String> languagefiles;
	private static String languagefilepath;
	private static Vector<String> languagereferences;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		languagefilepath = "C:/SVN/SGFiles/SGlanguage/english.l";
		languagereferences = new Vector<String>();
		addlanguagetags(FileIO.readFile(languagefilepath));
		Vector<String> folders = new Vector<String>();
		folders.add("C:/SVN/WorkSpace/SGWebsite/src/pseudoServlets");
		folders.add("C:/SGFiles/SGtemplates");
		folders.add("C:/SVN/WorkSpace/SGWebsite/src/dataStructure");
		folders.add("C:/SVN/WorkSpace/SGWebsite/src/htmlBuilder");
		folders.add("C:/SVN/WorkSpace/SGWebsite/src/login");
		for (String i : folders)
		{
			File parent = new File(i);
			for (File f : parent.listFiles())
			{
				if (!(f.isDirectory()))
				{
					addlanguagetags(FileIO.readFile(f.getAbsolutePath()));
					System.out.println(f.getName()+" has been processed");
				}
			}
		}
		
		// voeg de tabtitels toe
		for (PseudoServlet.TabName i : PseudoServlet.TabName.values())
		{
			if (!(languagereferences.contains(i.toString())))
			{
				languagereferences.add(i.toString());
			}
		}
		
		// steek de nieuwe language tags in een string die in een .l bestand kan gezet worden.
		String out = new String();
		for (String v : languagereferences)
		{
			out += "##"+v+"##=;\n";
		}
		System.out.println(out);
		
		System.out.println(languagereferences);
	}
	
	static void addlanguagetags(String content)
	{
		String[] arr = content.split("##");
		for (int i = 1; i < arr.length; i+=2)
		{
			if (!(languagereferences.contains(arr[i])))
			{
				languagereferences.add(arr[i]);
			}
		}
	}

	
}
