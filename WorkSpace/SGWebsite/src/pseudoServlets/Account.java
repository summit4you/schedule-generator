package pseudoServlets;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import database.Database;

import pseudoServlets.tools.PSTools;

import sessionTracking.Session;

import htmlBuilder.Site;

/**
 * <b>PseudoServlet that shows the user its account data</b> </br> </br> 
 * 
 * @version 0.1
 * @author Adam
 *
 */
public class Account extends PseudoServlet
	{
		
		public Account()
		{
			templateFile="account.tpl";
		}
	
		@Override
		protected void init()
		{
			super.init();
		}
		
		@Override
		public String processRequest(RequestType type, HttpServletRequest request, Session session)
		{
			String response = replaceTags(template,"MASTERSERVLET", createLink(session));
			response = replaceTags(response,"ACCOUNTNAME","##account_is##: " + session.getAccount().getUserName());
			response = replaceTags(response,"CURRENTLANGUAGE","##language_is##: " + session.getAccount().getLanguage());
			response = replaceTags(response,"LANGUAGEOPTIONS",PSTools.createLanguageOptions());
			System.out.println(type.toString());
			if (type==RequestType.POST)
			{
				// TODO for testing purposes, print request
				Enumeration<String> e = request.getParameterNames();
				while (e.hasMoreElements())
				{
					String testprint = (String) e.nextElement();
					System.out.println(testprint + " = " + request.getParameter(testprint));
				}
				
				String lang = request.getParameter("lang");
				if (lang==null)
				{
					// there has been a change password request
					System.out.println("password change requested");
					String pass1=request.getParameter("pass1");
					String pass2=request.getParameter("pass2");
					if ((pass1==null)||(pass2==null))
					{
						response = replaceTags(response,"PASSRESULT","##please_complete_form##");
						return replaceTags(response,"LANGRESULT"," ");
					} 
					else
					{
						if (pass1.equals(pass2))
						{
							response = replaceTags(response,"PASSRESULT","##password_change_success##");
							response = replaceTags(response,"LANGRESULT"," ");
							Database db = getDB();
							db.connect();
							session.getAccount().setPassword(pass1);
							db.write(session.getAccount());
							db.disconnect();
							return response;
						}
						else
						{
							response = replaceTags(response,"PASSRESULT","##passwords_dont_match##");
							return replaceTags(response,"LANGRESULT"," ");
						}
					}
				}
				else
				{
					// there has been a change language request
					Database db = getDB();
					db.connect();
					session.getAccount().setLanguage(lang);
					db.write(session.getAccount());
					db.disconnect();
					response = replaceTags(response,"PASSRESULT"," ");
					return replaceTags(response,"LANGRESULT","##language_changed##");
				}
			}
			else
			{
				response = replaceTags(response,"PASSRESULT"," ");
				return replaceTags(response,"LANGRESULT"," ");
			}
		}
		
		@Override
		public String getTabName()
		{
			return PseudoServlet.TabName.Account.toLanguageTag();
		}

}


