package language;

import java.util.Vector;
import other.*;


public class CollectLanguageTags 
{
	
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
		pseudoservletpath = "C:/SVN/WorkSpace/SGWebsite/src/pseudoServlets/";
		templatefilepath = "C:/SGtemplates/";
		languagefilepath = "C:/SVN/WorkSpace/SGWebsite/src/language/";
		makefilenames();
		languagereferences = new Vector<String>();
		
		
		// voeg de languagefiles uit de pseudoservlets toe
		for (String i : pseudoservlets)
		{
			addlanguagetags(pseudoservletpath+i);
		}
		
		// voeg de language files uit de templates toe
		for (String i : templatefiles)
		{
			addlanguagetags(templatefilepath+i);
		}
		
		// steek de language tags in een string die in een .l bestand kan gezet worden.
		String out = new String();
		for (String i : languagereferences)
		{
			out += "##"+i+"##=;\n";
		}
		System.out.println(out);
	}
	
	static void addlanguagetags(String filename)
	{
		String content = FileIO.readFile(filename);
		// zoek naar de eerste language tag in de code
		int hekje1 = 1;
		int hekje2 = 1;
		while ((content.indexOf("##", hekje2+1)!=-1)&&(content.indexOf("##", hekje1+1)!=-1))
		{
			hekje1=content.lastIndexOf("##", hekje2+1);
			hekje2=content.lastIndexOf("##", hekje1+1);
			System.out.println("H1: "+hekje1+ "H2:"+hekje2);
			// alles tussen hekje1 en hekje2 is een language tag. controleer of die nog niet eerder gevonden is
			String tag = content.substring(hekje1+1,hekje2);
			if (!(languagereferences.contains(tag)))
			{
				languagereferences.add(tag);
			}
		}
	}
	
	
	static void makefilenames()
	{
		pseudoservlets = new Vector<String>();
		pseudoservlets.add("Search.java");
		pseudoservlets.add("Schedule.java");
		
		templatefiles = new Vector<String>();
		templatefiles.add("schedule.tpl");
		templatefiles.add("search.tpl");
		templatefiles.add("search2.tpl");
		//templatefiles.add("simpletable.tpl");
		//templatefiles.add("buildingTable.tpl");
		//templatefiles.add("courseTable.tpl");
		
	}
	
	
}
