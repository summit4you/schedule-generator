package pseudoServlets.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Vector;

import other.Globals;

import dataStructure.*;

public class CalendarTools
{
	public static String generateEditablePHPiCalendarLink(String language,String popupLink)
	{
		String calFolder=Globals.calendarFolder;
		try
		{
			popupLink=URLEncoder.encode(popupLink,"UTF-8");
			calFolder=URLEncoder.encode(calFolder,"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
		}
		return "http://wilma.vub.ac.be/~se5_1011/phpicalendarEditable/week.php?width=1024&lang="+language+"&start=0800&end=1800&days=5&popupLink="+popupLink+"&calPath="+calFolder+"&cal=";
	}
	
	public static String generateEditablePHPiCalendarLink(Vector<Program>programs,String language,String popupLink)
	{
		String link=generateEditablePHPiCalendarLink(language,popupLink);
		boolean added=false;
		for (Program i : programs)
		{
			for (Course j : i.getCourses())
			{
				for (Subcourse k : j.getSubcourses())
				{
					link+=k.getCalendarfile()+",";
					added=true;
				}
			}
		}
		if (!added)
		{
			return link;
		}
		return link.substring(0,link.length()-1);
	}
	
	/**
	 * Generates the link for the PHPiCalendar to show the schedule of a student
	 * @param student
	 * @param language
	 * @return
	 */
	public static String GeneratePHPiCalendarLink(Student student,String language)
	{
		// TODO parameters uitlezen uit XML bestand
		String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
		link += CollectCalendarFiles(student);
		link += "&width=1024&lang=";
		link+=language;
		link+="&start=0800&end=1800&days=5";
		return link;
	}
	
	public static String GeneratePHPiCalendarLink(Room room,String language)
	{
		// TODO parameters uitlezen uit XML bestand
		String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
		link += room.getCalendarfile();
		link += "&width=1024&lang=";
		link+=language;
		link+="&start=0800&end=1800&days=5";
		return link;
	}
	
	/**
	 * Generates the link for the PHPiCalendar to show the schedule of an educator
	 * @param educator 
	 * @param language
	 * @return
	 */
	public static String GeneratePHPiCalendarLink(Educator educator,String language)
	{
		// TODO parameters uitlezen uit XML bestand
		String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
		link += CollectCalendarFiles(educator);
		link += "&width=1024&lang=";
		link+=language;
		link+="&start=0800&end=1800&days=5";
		return link;
	}
	
	/**
	 * Creates a string containing the calendar files, separated by comma's
	 * @param student
	 * @return
	 */
	public static String CollectCalendarFiles(Student student)
	{
		String link = new String(); 
		for (Program i : student.getPrograms())
		{
			for (Course j : i.getCourses())
			{
				for (Subcourse k : j.getSubcourses())
				{
					link+=k.getCalendarfile()+",";
				}
			}
		}
		for (Course i : student.getCourses())
		{
			for (Subcourse k : i.getSubcourses())
			{
				link+=k.getCalendarfile()+",";
			}
		}
		return link.substring(0,link.length()-1); 
	}
	
	public static String CollectCalendarFiles(Educator educator)
	{
		String link = new String();
		for (Course i : educator.getCourses())
		{
			for (Subcourse k : i.getSubcourses())
			{
				link+=k.getCalendarfile()+",";
			}
		}
		return link.substring(0,link.length()-1); 
	}
}
