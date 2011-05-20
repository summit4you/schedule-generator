package pseudoServlets;

import javax.servlet.http.HttpServletRequest;

import pseudoServlets.PseudoServlet.RequestType;

import sessionTracking.Session;

public class StartAlgorithm extends PseudoServlet
{

	private Thread thread;
	
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
				// start the threat with the algorithm
			} 
			else if ("stop".equals(form))
			{
				// stop the algorithm
			} 
			else if ("status".equals(form))
			{
				// send the status of the algorithm back
			}
			else
			{
				// ERROR
			}
		}
		else
		{
			// get has been sent, send the html code of the page
		}
		return "ERROR";
	}

	@Override
	public TabName getTabName()
	{
		return pseudoServlets.PseudoServlet.TabName.StartAlgorithm;
	}

}
