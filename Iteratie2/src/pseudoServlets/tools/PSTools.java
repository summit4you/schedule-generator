package pseudoServlets.tools;

import java.util.Vector;

import language.LanguageResolver;
import pseudoServlets.PseudoServlet;
import database.Databasable;
import database.Database;
import database.Search;

/**
 * @author Zjef
 * @version 1.1
 */
public class PSTools 
{
	public static String implodeVector(Vector<?> objects)
	{
		String res="";
		for (Object i:objects)
		{
			res+=i.toString()+",";
		}
		return objects.size()>0?res.substring(0,res.length()-1):"";
	}
	
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
	
	public static String createSelectOptions(Vector<? extends Databasable> options)
	{
		String res="";
		for (Databasable i:options)
		{
			res+="<option value='"+i.getID().getValue()+"'>"+i.toString()+"</option>";
		}
		return res;
	}
	
	public static <T extends Databasable> Vector<T> loadObjects(Class<T> cl)
	{
		return loadObjects(cl,false);
	}
	
	public static <T extends Databasable> Vector<T> loadObjects(Class<T> cl,boolean topOnly)
	{
		Database db=PseudoServlet.getDB();
		db.connect();
		Vector<? extends Databasable> res=topOnly?db.readAllSingle(new Search(cl)):db.readAll(new Search(cl));
		db.disconnect();
		return (Vector<T>) res;
	}

	public static <T extends Databasable> Vector<T> loadObjects(Class<T> cl,Class... classes)
	{
		Database db=PseudoServlet.getDB();
		db.connect();
		Vector<? extends Databasable> res=db.readAll(new Search(cl),classes);
		db.disconnect();
		return (Vector<T>) res;
	}
	
	public static String createLanguageOptions()
	{
		String res="";
		for (String i : LanguageResolver.supportedLanguages())
		{
			res+="<option>"+i.toString()+"</option>";
		}
		return res;
	}
}