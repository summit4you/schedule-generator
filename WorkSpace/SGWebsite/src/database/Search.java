package database;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Search criterion for a database search with the methods {@link Database#read(Search)} and {@link Database#readAll(Search)}
 * @author Zjef
 * @version 1.1
 */
public class Search implements Serializable,Syntaxable
{
	final public static String classSeparator=";";
	
	private Class<?extends Databasable> cl;
	private Vector<Method> getters;
	private Vector<Object> results;
	private ID id;
	private boolean like=false;
	private boolean and=true;
	
	/**
	 * Enables or disables searches with wildcards, eg: "zoekterm%","%zoekterm","%zoekterm%"
	 */
	public Search setWildCardSearch(boolean enable)
	{
		this.like=enable;
		return this;
	}
	
	/**
	 * Sets the search to 'and' or 'or' when specifying multiple criteria
	 */
	public Search setAndOr(boolean and)
	{
		this.and=and;
		return this;
	}
	
	/**
	 * copyMethodString("getName",3) returns "getName;getName;getName"<br>
	 * This can be used when searching for multiple objects in one search, in combination with {@link #setAndOr(boolean)}<br>
	 */
	public static String copyMethodString(String method,int times)
	{
		String res=method;
		for (int i=0;i<times-1;i++)
		{
			res+=";"+method;
		}
		return res;
	}
	
	/**
	 * This constructor creates a Search object with which you can load all instances from a database (using the {@link Database#readAll()})
	 * @param cl - class of which to load all the objects
	 */
	public Search(Class<?extends Databasable> cl)
	{
		this.cl=cl;
		this.getters=null;
		this.results=null;
		this.id=null;
	}
	
	/**
	 * Enables a search for an object based on its unique ID
	 * @param cl - class of the object to be searched
	 * @param id - {@link ID} of the object to be retrieved
	 */
	public Search(Class<? extends Databasable> cl,ID id)
	{
		this.cl=cl;
		this.id=id;
		this.getters=null;
		this.results=null;
	}
	
	public Search(Class<? extends Databasable> cl,String id)
	{
		this(cl,new ID(id));
	}
	
	/**
	 * Enables a search of the database for objects matching specific requirements.<br>
	 * These requirements are specified by the getter methods. The fields linked to the specified getters (annotated with {@link InDatabase}) are checked if they contain the same objects as the specified results.<br>
	 * So, an object is returned from the table if all the specified fields (by means of the parameter getters) contain the searched values (by means of the parameter results) 
	 * @param cl - class of object(s) to be searched
	 * @param getters - getter Methods to check
	 * @param results - objects these methods should return
	 */
	public Search(Class<?extends Databasable> cl,Vector<Method> getters,Vector<Object> results)
	{
		this.cl=cl;
		this.getters=(Vector<Method>)getters.clone();
		this.results=new Vector<Object>();
		for (Object i:results)
		{
			addObject(i);
		}
		this.id=null;
	}
	
	/**
	 * See {@link #Search(Class, Vector, Vector)} for information about a Search is used to search a database.
	 * 
	 * To construct the string 'getters':<br>
	 * <ul>
	 * <li>Take the name of the method, without the brackets</li>
	 * <li>For multiple methods, separate by a semicolon, ';'
	 * </ul>
	 * Example<br>
	 * We want to select the following three getters:<br>
	 * <code>
	 * public int getMyInt()<br>
	 * { ... }<br>
	 * public int getMyInt2()<br>
	 * { ... }<br>
	 * public boolean getMyBool()<br>
	 * { ... }<br>
	 * </code>
	 * Then getters=<code>"getMyInt;getMyInt2;getMyBool"</code><br><br>
	 */
	public Search(Class<?extends Databasable> cl,String getters,Vector<Object> results)
	{
		this.cl=cl;
		getMethods(getters);
		this.results=new Vector<Object>();
		for (Object i:results)
		{
			addObject(i);
		}
		this.id=null;
	}
	
	/**
	 * See {@link #Search(Class, Vector, Vector)} for information about a Search is used to search a database.<br><br>
	 * See {@link #Search(Class, String, Vector)} for information about how to construct the String 'getters'.
	 */
	public Search(Class<?extends Databasable> cl,String getters,Object...results)
	{
		this.cl=cl;
		getMethods(getters);
		this.results=new Vector<Object>();
		for (Object i:results)
		{
			addObject(i);
		}
	}
	
	private void addObject(Object object)
	{
		results.add(object instanceof Databasable?((Databasable) object).getID():object);
	}
	
	protected Class<? extends Databasable> getCl()
	{
		return cl;
	}
	
	protected ID getID()
	{
		return id;
	}
	
	/**
	 * Extracts methods from the string
	 */
	private void getMethods(String getters)
	{
		this.getters=new Vector<Method>();
		String[] names=getters.split(classSeparator);
		for (String i:names)
		{
			try
			{
				this.getters.add(cl.getDeclaredMethod(i));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String getText()
	{
		String text="SELECT * FROM "+Extract.getTableName(cl);
		if (id!=null)
		{
			return text+" WHERE ID="+id.toString();
		}
		else if (getters==null)
		{
			return text;
		}
		else
		{
			text+=" WHERE ";
			for (int i=0;i<getters.size();i++)
			{
				text+=Extract.methodToString(getters.get(i))+(like?" LIKE ":"=")+Extract.valueToString(results.get(i))+(and?" AND ":" OR ");
			}
			return text.substring(0,text.length()-(and?5:4));
		}
	}
}