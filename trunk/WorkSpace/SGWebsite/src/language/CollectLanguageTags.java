package language;

import java.util.Vector;
import other.*;


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
		datastructurepath = "C:/SVN/WorkSpace/SGWebsite/src/dataStructure/";
		pseudoservletpath = "C:/SVN/WorkSpace/SGWebsite/src/pseudoServlets/";
		templatefilepath = "C:/SGtemplates/";
		languagefilepath = "C:/SVN/WorkSpace/SGWebsite/src/language/";
		makefilenames();
		languagereferences = new Vector<String>();
		
		//
		for (String i : datastructure)
		{
			System.out.println("File: "+i);
			addlanguagetags(datastructurepath+i);
		}
		
		// voeg de languagefiles uit de pseudoservlets toe
		for (String i : pseudoservlets)
		{
			System.out.println("File: "+i);
			addlanguagetags(pseudoservletpath+i);
		}
		// voeg de language files uit de templates toe
		for (String i : templatefiles)
		{
			System.out.println("File: "+i);
			addlanguagetags(templatefilepath+i);
		}
		
		// haal de language references uit site.xml
		System.out.println("File: Site.xml");
		addlanguagetags("C:/SVN/WorkSpace/SGWebsite/src/htmlBuilder/site.xml");
		
		
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
		String[] arr = content.split("##");
		for (int i = 1; i < arr.length; i+=2)
		{
			System.out.println(arr[i]);
			if (!(languagereferences.contains(arr[i])))
			{
				languagereferences.add(arr[i]);
			}
		}
	}
	
	
	static void makefilenames()
	{
		pseudoservlets = new Vector<String>();
		pseudoservlets.add("Search.java");
		pseudoservlets.add("Schedule.java");
		pseudoservlets.add("BuildingTable.java");
		pseudoservlets.add("CourseTable.java");
		pseudoservlets.add("EducatorTable.java");
		pseudoservlets.add("PseudoServlet.java");
		pseudoservlets.add("SingleTable.java");
		pseudoservlets.add("StudentTable.java");
		pseudoservlets.add("TabAndDrop.java");
		pseudoservlets.add("TabAndList.java");
		
		templatefiles = new Vector<String>();
		templatefiles.add("schedule.tpl");
		templatefiles.add("search.tpl");
		templatefiles.add("search2.tpl");
		templatefiles.add("simpletable.tpl");
		templatefiles.add("buildingTable.tpl");
		templatefiles.add("courseTable.tpl");
		
		datastructure = new Vector<String>();
		datastructure.add("Admin.java");
		datastructure.add("Building.java");
		datastructure.add("Course.java");
		datastructure.add("Educator.java");
		datastructure.add("Faculty.java");
		datastructure.add("Hardware.java");
		datastructure.add("Program.java");
		datastructure.add("Room.java");
		datastructure.add("Student.java");
		datastructure.add("SubCourse.java");
		datastructure.add("TypeOfCourse.java");
	}
	
	
}
