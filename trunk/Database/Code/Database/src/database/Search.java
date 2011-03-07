package database;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Search criterion for a database
 * @author Zjef
 * @version 0.2
 */
public class Search implements Serializable
{
	private Class<?extends Databasable> cl;
	private Vector<Method> getters;
	private Vector<Object> results;
	
	/**
	 * This constructor creates a Search object with which you can load all instances from a database (using the {@link Database#readAll()})
	 * @param cl - class of which to load all the objects
	 */
	public Search(Class<?extends Databasable> cl)
	{
		this.cl=cl;
		this.getters=null;
		this.results=null;
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
		this.results=(Vector<Object>)results.clone();
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
		this.results=(Vector<Object>)results.clone();
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
			this.results.add(i);
		}
	}
	
	private void getMethods(String getters)
	{
		//TODO
	}
}