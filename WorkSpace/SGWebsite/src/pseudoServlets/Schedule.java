package pseudoServlets;

import htmlBuilder.Site;

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
		String response = new String();
		if (session.getAccount().getStudent()!=null)
		{
			String link = calendarTools.GeneratePHPiCalendarLink(session.getAccount().getStudent(), session.getAccount().getLanguage());
			response = replaceTags(template, "LINK", link);
		}
		else if (session.getAccount().getEducator()!=null) 
		{
			String link = calendarTools.GeneratePHPiCalendarLink(session.getAccount().getEducator(), session.getAccount().getLanguage());
			response = replaceTags(template, "LINK", link);
		}
		else
		{
			response = "You don't have a schedule";
		}
		
		return response;
	}

	@Override
	public String getTabName()
	{
		return Site.TabName.Schedule.toLanguageTag();
	}

}
