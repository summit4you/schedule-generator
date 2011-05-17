package pseudoServlets;

import htmlInterfaces.HTMLInterfaceTool;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.UserType;

import database.Database;
import database.Search;

import applet.EditVector;

import pseudoServlets.tools.PSTools;
import sessionTracking.Session;

public class AccountTable extends PseudoServlet
{
	final private static String accountsTag="accounts";
	final private static String saveTag="save";
	final private static String accountTypeTag="type";
	
	private boolean editable;
	
	public AccountTable(boolean editable) 
	{
		super();
		this.editable=editable;
		templateFile=editable?"accountsEditable.tpl":"singleTable.tpl";
		templateFile="accounts.tpl";
	}

	private Vector<login.Account> getObjects()
	{
		return PSTools.loadObjects(login.Account.class);
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		Vector<login.Account> objects=getObjects();
		String res="";
		if (editable)
		{
			res=replaceTags(template,"URL",createLink(session));
			res=replaceTags(res,"TYPES",PSTools.implodeVector(UserType.getUserTypes()));
		}
		
		if (objects.size()==0)
		{
			return replaceTags(res,"TABLE",HTMLInterfaceTool.makeEmptyTable("maintable",login.Account.class));
		}
		else
		{
			return replaceTags(res,"TABLE",HTMLInterfaceTool.changeToDataTable("maintable",getObjects()));
		}
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

	@Override
	public String getTabName()
	{
		if (editable)
		{
			return PseudoServlet.TabName.AccountTableEditable.toLanguageTag();
		}
		else
		{
			return PseudoServlet.TabName.AccountTable.toLanguageTag();
		}
	}
}