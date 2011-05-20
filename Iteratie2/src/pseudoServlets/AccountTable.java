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
	public AccountTable() 
	{
		super();
		templateFile="singleTable.tpl";
	}

	private Vector<login.Account> getObjects()
	{
		return PSTools.loadObjects(login.Account.class);
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		Vector<login.Account> objects=getObjects();
		
		if (objects.size()==0)
		{
			return replaceTags(template,"TABLE",HTMLInterfaceTool.makeEmptyTable("maintable",login.Account.class));
		}
		else
		{
			return replaceTags(template,"TABLE",HTMLInterfaceTool.changeToDataTable("maintable",getObjects()));
		}
	}

	@Override
	public TabName getTabName()
	{
		return TabName.Accounts;
	}
}