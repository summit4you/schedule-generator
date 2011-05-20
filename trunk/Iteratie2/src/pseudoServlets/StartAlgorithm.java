package pseudoServlets;

import javax.servlet.http.HttpServletRequest;

import pseudoServlets.PseudoServlet.RequestType;

import scheduleGenerator.SemesterScheduler;
import sessionTracking.Session;

public class StartAlgorithm extends PseudoServlet
{

	private SemesterScheduler SS=new SemesterScheduler();
	
	public StartAlgorithm()
	{
		templateFile="bigredbutton.tpl";
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,
			Session session)
	{
		if (type==RequestType.POST)
		{
			// POST had been sent, there are 3 possible posts: start, stop and status
			String form = request.getParameter("form");
			if ("start".equals(form))
			{
				// a semester has been sent, start up the algorithm
				String sem = request.getParameter("semester");
				SS.start(Integer.parseInt(sem));
				return replaceTags(template, "MASTERSERVLET", createLink(session));
			} 
			else if ("stop".equals(form))
			{
				// stop the algorithm
				SS.forceStop();
				return replaceTags(template, "MASTERSERVLET", createLink(session));
			} 
			else if ("status".equals(form))
			{
				// send the status of the algorithm back
				String status=SS.getStatus();
				return status;
			}
			else
			{
				return "ERROR";
			}
		}
		else
		{
			// get has been sent, send the html code of the page
			return replaceTags(template, "MASTERSERVLET", createLink(session));
		}
	}

	@Override
	public TabName getTabName()
	{
		return pseudoServlets.PseudoServlet.TabName.StartAlgorithm;
	}

}
