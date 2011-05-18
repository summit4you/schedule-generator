package pseudoServlets.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Vector;

import calendar.Translator;

import com.hp.gagawa.java.elements.Input;

import other.Globals;

import dataStructure.*;
import database.Databasable;

public class CalendarTools
{
	
	private final static String width="900";
	private final static String days="5";
	private final static String starthour="0800";
	private final static String stophour="1800";
	
	
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
		return "http://wilma.vub.ac.be/~se5_1011/phpicalendarEditable/week.php?width="+width+"&lang="+language+"&start="+starthour+"&end="+stophour+"&days="+days+"&popupLink="+popupLink+"&calPath="+calFolder+"&cal=";
	}
	
	public static String generateEditablePHPiCalendarLink(Educator edu,String language,String popupLink)
	{
		String calFolder=Globals.calendarFolder;
		try
		{
			popupLink=URLEncoder.encode(popupLink,"UTF-8");
			calFolder=URLEncoder.encode(calFolder,"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
		}
		return "http://wilma.vub.ac.be/~se5_1011/phpicalendarEditable/week.php?width="+width+"&lang="+language+"&start="+starthour+"&end="+stophour+"&days="+days+"&popupLink="+popupLink+"&calPath="+calFolder+"&cal="+Translator.getCalendarFileName(edu);
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
//	public static String GeneratePHPiCalendarLink(Student student,String language)
//	{
//		// TODO parameters uitlezen uit XML bestand
//		String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
//		link += CollectCalendarFiles(student);
//		link += "&width=1024&lang=";
//		link+=language;
//		link+="&start=0800&end=1800&days=5";
//		return link;
//	}
	
	public static String GeneratePHPiCalendarLink(Databasable input,String language)
	{
		if (input instanceof Student)
		{
			String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
			link += CollectCalendarFiles((Student.class.cast(input)));
			link += "&width="+width+"&lang=";
			link+=language;
			link+="&start="+starthour+"&end="+stophour+"&days="+days;
			return link;
		}
		else if (input instanceof Educator) 
		{
			String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
			link += CollectCalendarFiles((Educator) input);
			link += "&width="+width+"&lang=";
			link+=language;
			link+="&start="+starthour+"&end="+stophour+"&days="+days;
			return link;
		}
		else if (input instanceof Room) 
		{
			String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
			link += Translator.getCalendarFileName((Room) input);
			link += "&width="+width+"&lang=";
			link +=language;
			link +="&start="+starthour+"&end="+stophour+"&days="+days;
			return link;
		}
		else //ERROR!!
		{
			return null;
		}
	}
	
//	public static String GeneratePHPiCalendarLink(Room room,String language)
//	{
//		// TODO parameters uitlezen uit XML bestand
//		String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
//		link += room.getCalendarfile();
//		link += "&width=1024&lang=";
//		link+=language;
//		link+="&start=0800&end=1800&days=5";
//		return link;
//	}
	
	/**
	 * Generates the link for the PHPiCalendar to show the schedule of an educator
	 * @param educator 
	 * @param language
	 * @return
	 */
//	public static String GeneratePHPiCalendarLink(Educator educator,String language)
//	{
//		// TODO parameters uitlezen uit XML bestand
//		String link = "http://wilma.vub.ac.be/~se5_1011/phpicalendar/week.php?cal=";
//		link += CollectCalendarFiles(educator);
//		link += "&width=1024&lang=";
//		link+=language;
//		link+="&start=0800&end=1800&days=5";
//		return link;
//	}
	
	/**
	 * Creates a string containing the calendar files, separated by comma's
	 * @param student
	 * @return
	 */
	public static String CollectCalendarFiles(Student student)
	{
		String link = new String(); 
		if (!((student.getPrograms()==null)||(student.getPrograms().isEmpty())))
		{
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
		}
		if (!((student.getCourses()==null)||(student.getCourses().isEmpty())))
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
		if (!((educator.getCourses()==null)||(educator.getCourses().isEmpty())))
		{
			for (Course i : educator.getCourses())
			{
				for (Subcourse k : i.getSubcourses())
				{
					link+=k.getCalendarfile()+",";
				}
			}
		}
		return link.substring(0,link.length()-1); 
	}
}
