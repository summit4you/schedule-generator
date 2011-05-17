package pseudoServlets;

import htmlBuilder.Site;
import htmlInterfaces.HTMLInterfaceTool;
import htmlInterfaces.HTMLTablable;

import java.io.File;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.sun.xml.internal.bind.v2.TODO;

import other.FileIO;
import pseudoServlets.tools.CalendarTools;

import sessionTracking.Session;
import database.*;

import dataStructure.*;


public class Search extends PseudoServlet
{

	private String calendarTemplate;
	private String scriptTemplate;

	public Search()
	{
		templateFile="search.tpl";
	}

	@Override
	protected void init()
	{
		super.init();		
		calendarTemplate=loadTemplate("search2.tpl");
		scriptTemplate=loadTemplate("search3.tpl");
	}

	
	
	private Vector<? extends Databasable> DoSearch(Class cl, String firstName, String lastName,Database db)
	{
		db.connect();
		Vector<Databasable> searchresult = new Vector<Databasable>();
		if (firstName==null)
		{
			if (lastName==null)
			{
				searchresult = null;
			}
			else
			{
				database.Search s = new database.Search(cl,"getSurName","%"+lastName.toUpperCase()+"%").setWildCardSearch(true).setCaseSensitive(false);
				searchresult = db.readAll(s);
			}
		}
		else 
		{
			if (lastName==null)
			{
				database.Search s = new database.Search(cl,"getFirstName","%"+firstName.toUpperCase()+"%").setWildCardSearch(true).setCaseSensitive(false);
				searchresult = db.readAll(s);
			}
			else
			{
				database.Search s = new database.Search(cl,"getFirstName;getSurName","%"+firstName.toUpperCase()+"%","%"+lastName.toUpperCase()+"%").setWildCardSearch(true).setCaseSensitive(false);
				searchresult = db.readAll(s);
			}
		}
		db.disconnect();
		return searchresult;
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String response = replaceTags(template,"MASTERSERVLET", createLink(session)); 
		String classtype =  request.getParameter("type");
		if (classtype==null)
		{
			response = replaceTags(response, "CALENDAR", ""); 
		}
		else
		{
			if (classtype.equals("student"))
			{
				String result = request.getParameter("searchresult");
				if (result==null)
				{
					// zoek op in de database met studenten
					Database db = getDB();
					Vector<Student> searchresult = (Vector<Student>) DoSearch(Student.class, request.getParameter("firstname"), request.getParameter("lastname"),db);
					if ((searchresult==null)||(searchresult.isEmpty()))
					{
						response = replaceTags(response, "CALENDAR", "##search_nothingfound##"); 
					}
					// als er maar 1 resultaat is, toon de kalender van de persoon
					else if (searchresult.size()==1)
					{
						String link = CalendarTools.GeneratePHPiCalendarLink(searchresult.get(0), session.getAccount().getLanguage());
						response = replaceTags(response, "CALENDAR", replaceTags(calendarTemplate, "LINK", link)); 
					}
					else
					{	
						response = replaceTags(response, "CALENDAR", replaceTags(scriptTemplate, "LINK", createLink(session)+"&type=student")); 
						response = replaceTags(response, "SEARCHRESULTS", HTMLInterfaceTool.changeToDataTable("results", searchresult));
					}
				}
				else // Er is geklikt op een rechtstreekse student waarvan het antwoord nu getoond moet worden
				{
					// zoek op in de database met studenten
					Database db = getDB();
					db.connect();
					database.Search s = new database.Search(Student.class,"getStudentNumber",result);
					String link = CalendarTools.GeneratePHPiCalendarLink(db.<Student>read(s) , session.getAccount().getLanguage());
					response = replaceTags(response, "CALENDAR", replaceTags(calendarTemplate, "LINK", link));
				}
			}
			else if (classtype.equals("educator")) 
			{
				String result = request.getParameter("searchresult");

				if (result==null)
				{
					Database db = getDB();
					Vector<Educator> searchresult = (Vector<Educator>) DoSearch(Educator.class, request.getParameter("firstname"), request.getParameter("lastname"),db);
					if ((searchresult==null)||(searchresult.isEmpty()))
					{
						response = replaceTags(response, "CALENDAR", "##search_nothingfound##"); 
					}
					else if (searchresult.size()==1)
					{
						String link = CalendarTools.GeneratePHPiCalendarLink(searchresult.get(0), session.getAccount().getLanguage());
						response = replaceTags(response, "CALENDAR", replaceTags(calendarTemplate, "LINK", link)); 
					}
					else
					{
						response = replaceTags(response, "CALENDAR", replaceTags(scriptTemplate, "LINK", createLink(session)+"&type=educator")); 
						response = replaceTags(response, "SEARCHRESULTS", HTMLInterfaceTool.changeToDataTable("results", searchresult));
					}
				}
				else // Er is geklikt op een rechtstreekse student waarvan het antwoord nu getoond moet worden
				{
					// zoek op in de database met studenten
					Database db = getDB();
					db.connect();
					database.Search s = new database.Search(Educator.class,"getEmployeeNumber",result);
					String link = CalendarTools.GeneratePHPiCalendarLink(db.<Educator>read(s) , session.getAccount().getLanguage());
					response = replaceTags(response, "CALENDAR", replaceTags(calendarTemplate, "LINK", link));
					db.disconnect();
				}
			}
			else if (classtype.equals("room"))
			{
				Database db = getDB();
				db.connect();
				database.Search s = new database.Search(Room.class,"getLocation",request.getParameter("roomnumber"));
				Room searchresult = db.read(s);
				db.disconnect();
				if (searchresult==null)
				{
					response = replaceTags(response, "CALENDAR", "##search_nothingfound##"); 
				}				
				else
				{
					String link = CalendarTools.GeneratePHPiCalendarLink(searchresult, session.getAccount().getLanguage());
					response = replaceTags(response, "CALENDAR", replaceTags(calendarTemplate, "LINK", link)); 
				}
			}
		}
		return response;
	}

	@Override
	public String getTabName()
	{
		// TODO Auto-generated method stub
		return PseudoServlet.TabName.Search.toLanguageTag();
	}

}