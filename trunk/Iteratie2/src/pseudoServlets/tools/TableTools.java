package pseudoServlets.tools;

import htmlInterfaces.HTMLInterfaceTool;

import java.util.Vector;
import pseudoServlets.PseudoServlet;
import dataStructure.Building;
import dataStructure.Faculty;
import database.Databasable;
import database.Database;
import database.Search;

/**
 * @author Zjef
 * @version 0.1
 */
public class TableTools 
{
	public static String createTabHeader(Vector<?> tabs)
	{
		String res="";
		int counter=1;
		for (Object i:tabs)
		{
			res+="<li><a href='#tabs-"+counter+"'>"+i.toString()+"</a></li>";
			counter++;
		}
		return res;
	}
	
	public static <T extends Databasable> Vector<T> loadObjects(Class<T> cl)
	{
		Database db=PseudoServlet.getDB();
		db.connect();
		Vector<? extends Databasable> res=db.readAll(new Search(cl));
		db.disconnect();
		return (Vector<T>) res;
	}
}