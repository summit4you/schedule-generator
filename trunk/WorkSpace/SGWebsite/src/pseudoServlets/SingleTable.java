package pseudoServlets;

import java.io.File;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import sessionTracking.Session;
import htmlInterfaces.HTMLInterfaceTool;
import htmlInterfaces.HTMLTablable;
import database.Databasable;
import database.Database;
import database.Search;

/**
 * @author Zjef
 * @version 1.0
 */
public class SingleTable<T extends Databasable & HTMLTablable> extends PseudoServlet
{
	private Class<T> tableClass;
	private String name;
	
	public SingleTable(Class<T> tableClass,String name) 
	{
		super();
		this.tableClass=tableClass;
		this.name=name;
		templateFile="singleTable.tpl";
	}

	private Vector<T> getObjects()
	{
		Database db=getDB();
		db.connect();
		Vector<T> result=db.readAll(new Search(tableClass));
		db.disconnect();
		return result;
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		Vector<T> objects=getObjects();
		if (objects.size()==0)
		{
			return replaceTags(template,"TABLE",HTMLInterfaceTool.makeEmptyTable("maintable",tableClass));
		}
		else
		{
			return replaceTags(template,"TABLE",HTMLInterfaceTool.changeToDataTable("maintable",getObjects()));
		}
	}

	@Override
	protected String getTabName() 
	{
		return name;
	}
	
	@Override
	public String getIdentifier() 
	{
		return name;
	}
}