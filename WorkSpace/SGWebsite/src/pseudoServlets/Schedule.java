package pseudoServlets;

import javax.servlet.http.HttpServletRequest;

import dataStructure.*;
import pseudoServlets.tools.*;
import sessionTracking.Session;

public class Schedule extends PseudoServlet
{

	public Schedule()
	{
		templateFile="schedule.tpl";
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,
			Session session)
	{
		String link = new String();
		if (session.getAccount().getStudent()!=null)
		{
			link = calendarTools.GeneratePHPiCalendarLink(session.getAccount().getStudent(), session.getAccount().getLanguage());
		}
		else if (session.getAccount().getEducator()!=null) 
		{
			link = calendarTools.GeneratePHPiCalendarLink(session.getAccount().getEducator(), session.getAccount().getLanguage());
		}
		else
		{
			link = "Admins don't have a schedule";
		}
		String response = replaceTags(template, "LINK", link);
		return response;
	}

	@Override
	protected String getTabName()
	{
		return null;
	}

}
