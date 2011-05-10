package pseudoServlets;

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
public class PSAccount extends PseudoServlet
	{
		
		public PSAccount()
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
			response = replaceTags(response,"ACCOUNTNAME","##account_is##" + session.getAccount().getUserName());
			response = replaceTags(response,"CURRENTLANGUAGE","##language_is##" + session.getAccount().getLanguage());
			response = replaceTags(response,"LANGUAGEOPTIONS",PSTools.createLanguageOptions());
			if (type==RequestType.POST)
			{
				String lang = request.getParameter("lang");
				if (lang==null)
				{
					// there has been a change password request
					String pass1=request.getParameter("pass1");
					String pass2=request.getParameter("pass2");
					if ((pass1==null)||(pass2==null))
					{
						response = replaceTags(response,"PASSRESULT","##please_complete_form##");
						response = replaceTags(response,"LANGRESULT"," ");
					} 
					else
					{
						if (pass1.equals(pass2))
						{
							response = replaceTags(response,"PASSRESULT","##password_change_success##");
							response = replaceTags(response,"LANGRESULT"," ");
							// TODO change password in database
							Database db = getDB();
							db.connect();
							session.getAccount().setPassword(pass1);
							db.write(session.getAccount());
							db.disconnect();
						}
						else
						{
							response = replaceTags(response,"PASSRESULT","##passwords_dont_match##");
							response = replaceTags(response,"LANGRESULT"," ");
						}
					}
				}
				else
				{
					// there has been a change language request
					// TODO change the shit in the database
					Database db = getDB();
					db.connect();
					session.getAccount().setLanguage(lang);
					db.write(session.getAccount());
					db.disconnect();
					response = replaceTags(response,"PASSRESULT"," ");
					response = replaceTags(response,"LANGRESULT","##language_changed##");
				}
			}
			else
			{
				response = replaceTags(response,"PASSRESULT"," ");
				response = replaceTags(response,"LANGRESULT"," ");
			}
			return response;
		}
		
		@Override
		public String getTabName()
		{
			return null; //Site.TabName.MyAccount.toLanguageTag();
		}

}


