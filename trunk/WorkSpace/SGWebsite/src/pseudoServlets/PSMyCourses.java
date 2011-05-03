package pseudoServlets;

import htmlBuilder.Site;
import javax.servlet.http.HttpServletRequest;

import pseudoServlets.PseudoServlet.RequestType;
import sessionTracking.Session;

/**
 * <b>PseudoServlet that shows the user its courses</b> </br> </br> 
 * 
 * @version 0.1
 * @author Alexander
 *
 */
public class PSMyCourses extends PseudoServlet
{
	@Override
	protected void init()
	{
		super.init();
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		if (type==RequestType.POST)
		{
			//TODO receive data and process it
		}
		else
		{
			//TODO send data and form
			
		}
		return null;
	}
	
	@Override
	public String getTabName()
	{
		return Site.TabName.MyCourses.toLanguageTag();
	}
}
