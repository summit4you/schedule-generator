package pseudoServlets;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.UserType;

import database.Database;
import database.Search;

import pseudoServlets.PseudoServlet.TabName;
import pseudoServlets.tools.PSTools;

import sessionTracking.Session;

public class AccountEditor extends PseudoServletForApplet
{
	final private static String accountsTag="accounts";
	final private static String saveTag="save";
	final private static String accountTypeTag="type";
	
	public AccountEditor()
	{
		super("AccountApplet");
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String res=replaceTags(template,"PARAMS",",userTypes:'"+PSTools.implodeVector(UserType.getUserTypes())+"'");
		res=replaceTags(res,"JNLP",appletName);
		return replaceTags(res,"URL",createLink(session));
	}

	@Override
	public TabName getTabName()
	{
		return TabName.EditAccounts;
	}

	@Override
	public void processAppletRequest(HttpServletRequest request, HttpServletResponse response, Session session)
	{
		if ("true".equals(request.getParameter(saveTag)))
		{
			receive(request,response);
		}
		else if ("true".equals(request.getParameter(accountsTag)))
		{
			readObjectFromApplet(request);
			String type=request.getParameter(accountTypeTag);
			Database db=getDB();
			db.connect();
			Vector<login.Account> accounts=db.<login.Account>readAll(new Search(login.Account.class,"getType",type));
			db.disconnect();
			sendObjectToApplet(response,accounts);
		}
	}
}