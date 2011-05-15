package pseudoServlets;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import sessionTracking.Session;
import htmlBuilder.Site;
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
	private TabName name;
	
	public SingleTable(Class<T> tableClass,TabName name) 
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
		System.out.println(">>>SingleTable.processRequest: "+objects);
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
	public String getTabName() 
	{
		return name.toLanguageTag();
	}
	
	@Override
	public String getIdentifier() 
	{
		return name.toString();
	}
}