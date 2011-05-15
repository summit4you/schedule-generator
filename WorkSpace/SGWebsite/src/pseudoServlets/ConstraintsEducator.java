package pseudoServlets;

import javax.servlet.http.HttpServletRequest;

import pseudoServlets.PseudoServlet.RequestType;

import sessionTracking.Session;

public class ConstraintsEducator extends PseudoServlet
{

	@Override
	public String processRequest(RequestType type, HttpServletRequest request,
			Session session)
	{
		if (type==RequestType.GET)
		{
			
		}
		else if (type==RequestType.POST)
		{
			if (request.getParameter("change").equals("single"))
			{
				//TODO datum uit de request halen en event aan kalender toevoegen
				String start = request.getParameter("start"); // starting hour
				String stop = request.getParameter("stop");   // stop hour
			}
			else if (request.getParameter("change").equals("week"))
			{
				//TODO aan kalender toevoegen
				String day = request.getParameter("day"); // day of the week which is not possible
				String startweek = request.getParameter("startweek"); // week at which the unavailability starts, maybe change to date
				String stopweek = request.getParameter("stopweek"); // week at which the unavailability stops, maybe change to date
				String starthour = request.getParameter("starthour"); // hour at which the weekly unavailability starts
				String stophour = request.getParameter("stophour"); // hour at which the weekly unavailability stops
			}
			else if (request.getParameter("change").equals("long"))
			{
				//TODO startdatum en stopdatum uit de request halen en aan kalender toevoegen
			}
			else
			{
				return "ERROR, change type not known";
			}
		}
	}

	@Override
	public String getTabName()
	{
		return PseudoServlet.TabName.ConstraintsEducator.toLanguageTag();
	}

}
