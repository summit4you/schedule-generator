package pseudoServlets;

import javax.servlet.http.HttpServletRequest;

import sessionTracking.Session;

import htmlBuilder.Site;

/**
 * <b>PseudoServlet that shows the user its account data</b> </br> </br> 
 * 
 * @version 0.1
 * @author Alexander
 *
 */
public class PSAccount extends PseudoServlet
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
			return Site.TabName.MyAccount.toLanguageTag();
		}

}


