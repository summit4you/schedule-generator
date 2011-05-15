package pseudoServlets;

import htmlBuilder.Site;

import javax.servlet.http.HttpServletRequest;

import dataStructure.*;
import database.Databasable;
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
		Databasable data = session.getAccount().getData();
		if ((data instanceof dataStructure.Student)||(data instanceof dataStructure.Educator))
		{
			String link = CalendarTools.GeneratePHPiCalendarLink(data, session.getAccount().getLanguage());
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
		return PseudoServlet.TabName.Schedule.toLanguageTag();
	}

}
