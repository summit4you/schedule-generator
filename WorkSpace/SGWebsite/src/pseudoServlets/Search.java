package pseudoServlets;

import htmlBuilder.Site;
import htmlInterfaces.HTMLInterfaceTool;
import htmlInterfaces.HTMLTablable;

import java.io.File;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import other.FileIO;
import pseudoServlets.tools.calendarTools;

import sessionTracking.Session;
import database.*;

import dataStructure.*;


public class Search extends PseudoServlet
{

	private String template2;
	
	public Search()
	{
		templateFile="search.tpl";
	}
	
	@Override
	protected void init()
	{
		super.init();
		String templateFile2="search2.tpl";
		template2=(templateFile2==null?"":FileIO.readFile(new File(new File(servletPath).getParentFile(),templateFolder+templateFile2).getAbsolutePath()));
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
				// zoek op in de database met studenten
				Database db = getDB();
				db.connect();
				database.Search s = new database.Search(Student.class,"getFirstName;getSurName",request.getParameter("firstname"),request.getParameter("lastname"));
				Vector<Student> searchresult = db.readAll(s);
				db.disconnect();
				if ((searchresult==null)||(searchresult.isEmpty()))
				{
					response = replaceTags(response, "CALENDAR", "#search_nothingfound#"); 
				}
				// als er maar 1 resultaat is, toon de kalender van de persoon
				else if (searchresult.size()==1)
				{
					String link = calendarTools.GeneratePHPiCalendarLink(searchresult.get(0), session.getAccount().getLanguage());
					response = replaceTags(response, "CALENDAR", replaceTags(template2, "LINK", link)); 
				}
				else
				{
					// TODO toon tabel met de verschillende resultaten en maak een link op elk resultaat
					response = replaceTags(response, "CALENDAR", replaceTags(template2, "LINK", HTMLInterfaceTool.changeToDataTable("results", searchresult))); 
				}
				
			}
			else if (classtype.equals("educator")) 
			{
				Database db = getDB();
				db.connect();
				database.Search s = new database.Search(Educator.class,"getFirstName;getSurName",request.getParameter("firstname"),request.getParameter("lastname"));
				Vector<Educator> searchresult = db.readAll(s);
				db.disconnect();
				if ((searchresult==null)||(searchresult.isEmpty()))
				{
					response = replaceTags(response, "CALENDAR", "#search_nothingfound#"); 
				}
				else if (searchresult.size()==1)
				{
					String link = calendarTools.GeneratePHPiCalendarLink(searchresult.get(0), session.getAccount().getLanguage());
					response = replaceTags(response, "CALENDAR", replaceTags(template2, "LINK", link)); 
				}
				else
				{
					response = replaceTags(response, "CALENDAR", replaceTags(template2, "LINK", HTMLInterfaceTool.changeToDataTable("results", searchresult))); 
				}
			}
			else if (classtype.equals("room"))
			{
				Database db = getDB();
				db.connect();
				database.Search s = new database.Search(Room.class,"getLocation",request.getParameter("roomnumber"));
				Vector<Room> searchresult = db.readAll(s);
				db.disconnect();
				if ((searchresult==null)||(searchresult.isEmpty()))
				{
					response = replaceTags(response, "CALENDAR", "#search_nothingfound#"); 
				}				
				else if (searchresult.size()==1)
				{
					String link = calendarTools.GeneratePHPiCalendarLink(searchresult.get(0), session.getAccount().getLanguage());
					response = replaceTags(response, "CALENDAR", replaceTags(template2, "LINK", link)); 
				}
				// als er meerdere resultaten zijn, toon de resultaten in een tabel en laat klikken toe op links
				else
				{
					response = replaceTags(response, "CALENDAR", replaceTags(template2, "LINK", HTMLInterfaceTool.changeToDataTable("results", searchresult))); 
				}
			}
		}
		return response;
	}

	@Override
	public String getTabName()
	{
		// TODO Auto-generated method stub
		return Site.TabName.Search.toLanguageTag();
	}

}
