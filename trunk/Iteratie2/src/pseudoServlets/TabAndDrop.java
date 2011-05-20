package pseudoServlets;

import htmlInterfaces.HTMLInterfaceTool;
import htmlInterfaces.HTMLTablable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import pseudoServlets.tools.PSTools;
import sessionTracking.Session;
import database.Databasable;

/**
 * @author Zjef
 * @version 1.0
 */
abstract public class TabAndDrop<T extends Databasable> extends PseudoServlet 
{
	Class<T> cl;
	Class<? extends HTMLTablable> tableClass;
	
	public TabAndDrop(Class<T> cl,Class<? extends HTMLTablable> tableClass) 
	{
		super();this.tableClass=tableClass;
		this.cl=cl;
		templateFile="buildingTable.tpl";
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		Vector<T> tabObjects=PSTools.loadObjects(cl);
		String res=replaceTags(template,"TABS",PSTools.createTabHeader(tabObjects));
		res=replaceTags(res,"ON_OPEN",createOnOpen());
		res=replaceTags(res,"TABLE",createTable(tabObjects));
		res=replaceTags(res,"EXPAND_SCRIPT",createExpandScript(tabObjects));
		res=replaceTags(res,"INIT_OPEN",createInitOpen(tabObjects));
		return res;
	}
	
	protected String createTabObjectID(int index)
	{
		return "tabid"+index;
	}
	
	protected String createInitOpen(Vector<T> tabObjects)
	{
		String res="";
		int counter=1;
		for (T t:tabObjects)
		{
			res+="initTables('"+createTabObjectID(counter)+"');";
			counter++;
		}
		return res;
	}
	
	abstract protected String createOnOpen();
	
	abstract protected String createExpandScript(Vector<T> tabObjects);

	abstract protected Vector<? extends HTMLTablable> getTableObjects(T tabObject);
	
	private String createTable(Vector<T> tabObjects)
	{
		String res="";
		int counter=1;
		for (T t:tabObjects)
		{
			res+="<div id='tabs-"+counter+"'>";
			if (getTableObjects(t)!=null && getTableObjects(t).size()>0)
			{
				res+=HTMLInterfaceTool.changeToDataTable(createTabObjectID(counter),getTableObjects(t));
			}
			else
			{
				res+=HTMLInterfaceTool.makeEmptyTable(createTabObjectID(counter),tableClass);
			}
			res+="</div>";
			counter++;
		}
		return res;
	}
}