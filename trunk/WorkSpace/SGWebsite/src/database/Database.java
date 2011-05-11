package database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Represents a MySQLDatabase<br>
 * An object of this class provides methods to read and write objects from and to the database.<br>
 * Read the requirements at {@link Databasable} to enable classes to read and write their objects to a database.
 * 
 * @author Zjef
 * @version 1.1
 */
public class Database implements Serializable
{
	final private static String driverClassName = "com.mysql.jdbc.Driver";

	private String url,name,password;
	private boolean connected;
	private Connection connect;
	private Vector<String> tables;
	Vector<Databasable> cache;
	private Vector<Databasable> toLoad;
	
	/**
	 * Creates object to communicate to MySQL database.<br>
	 * Connection with the database is not established upon calling this
	 * constructor. An explicit call to {@link #connect()} is needed for this.
	 * 
	 * @param url
	 *            - url of the server of the MySQL database (or IP address)
	 * @param name
	 *            - name of the database on the server
	 * @param password
	 *            - password to access the database
	 */
	public Database(String url,String name,String password)
	{
		this.url=url;
		this.name=name;
		this.password=password;
		connected=false;
		cache=new Vector<Databasable>();
		toLoad=new Vector<Databasable>();
	}		
	
	public String getURL()
	{
		return url;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	/**
	 * Creates a new object in the database.
	 * @param databasable - object to create
	 */
	protected void create(Databasable databasable)
	{
		if (!tables.contains(Extract.getTableName(databasable)))
		{
			update(Extract.getNewTable(databasable.getClass()).getText());
			tables.add(Extract.getTableName(databasable));
		}
		update("INSERT INTO "+Extract.getTableName(databasable)+" () VALUES ()");
		databasable.setID(getLastInsertedID());
	}
	
	/**
	 * Writes a {@link Databasable} object into its table in this database.<br>
	 * If the {@link ID} of this object is <code>null</code>, a new element is created in the database and the object is given a unique ID.<br>
	 * If the ID is not <code>null</code>, the the element with the same ID in the database is updated.<br>
	 * If this object stores any other Databasable objects, they are not updated themselves. If there are any referred objects without ID, then a new entry for these new objects is created. Only an entry, not the data of these objects.<br><br>
	 * If the table associated with the object does not exist yet, it is created. The table has the same name as the class of the object.
	 * @param databasable - object to write/update in this database
	 * @see #writeAll(Vector)
	 */
	synchronized public void write(Databasable databasable)
	{
		if (databasable.getID()==null)
		{
			create(databasable);
		}
		
		Insertion ins=Extract.getUpdate(databasable);
		for (Databasable i:ins.getReferences())
		{
			if (i.getID()==null)
			{
				create(i);
				write(i);
			}
		}
		update(ins.getText());
	}
	
	private ID getLastInsertedID()
	{
		ResultSet result=query("SELECT LAST_INSERT_ID()");
		try
		{
			result.first();
			return new ID(result.getInt(1));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * See {@link #write(Databasable) write}
	 */
	public void writeAll(Vector<? extends Databasable> objects)
	{
		for (Databasable i:objects)
		{
			write(i);
		}
	}
	
	/**
	 * Deletes an object from its table in this database.<br>
	 * This method only checks for a match between the ID from the element in the table and the specified object.
	 * @param databasable - the object to delete
	 *  @see #delete(Class, ID)
	 * @see #deleteAll(Vector)
	 * @see #deleteAll(Class)
	 * @see #deleteAll(Class, Vector)
	 */
	public void delete(Databasable databasable)
	{
		delete(databasable.getClass(),databasable.getID());
	}
	
	/**
	 * Deletes all objects from their table in this database.<br>
	 * This method only checks for a match between the ID from the element in the table and the specified object.<br><br>
	 * All objects need to be instances of the same class.
	 * @param databasables - objects to delete
	 * @see #delete(Databasable)
	 * @see #delete(Class, ID)
	 * @see #deleteAll(Class)
	 * @see #deleteAll(Class, Vector)
	 */
	public void deleteAll(Vector<? extends Databasable> databasables)
	{
		for (Databasable i:databasables)
		{
			delete(i);
		}
	}
	
	/**
	 * Deletes the element in a table (specified by its corresponding class) with a certain ID; 
	 * @param cl - class coupled to the table in which to delete the element
	 * @param id - id of the to be deleted element
	 * @see #delete(Databasable)
	 * @see #deleteAll(Class)
	 * @see #deleteAll(Vector)
	 * @see #deleteAll(Class, Vector)
	 */
	public void delete(Class<? extends Databasable> cl,ID id)
	{
		update("DELETE FROM "+Extract.getTableName(cl)+" WHERE ID ="+id.toString());
	}
	
	/**
	 * Deletes the elements in a table (specified by its corresponding class) with a certain ID's; 
	 * @param cl - class coupled to the table in which to delete the elements
	 * @param ids - ID's of the to be deleted elements
	 * @see #delete(Databasable)
	 * @see #delete(Class, ID)
	 * @see #deleteAll(Class)
	 * @see #deleteAll(Vector)
	 */
	public void deleteAll(Class<? extends Databasable> cl,Vector<ID> ids)
	{
		for (ID i:ids)
		{
			delete(cl,i);
		}
	}
	
	/**
	 * Deletes all elements associated with this class from this database.
	 * @param cl - class of which to delete all entries in its table of this database
	 * @see #delete(Databasable)
	 * @see #deleteAll(Vector)
	 */
	public void deleteAll(Class<? extends Databasable> cl)
	{
		update("DELETE FROM "+Extract.getTableName(cl));
	}
	
	/**
	 * Deletes the entire table and its elements from this database.
	 * @param cl - class of which to delete the table
	 */
	public void deleteTable(Class<? extends Databasable> cl)
	{
		String tableName=Extract.getTableName(cl);
		update("DROP TABLE "+tableName);
		tables.remove(tableName);
	}
	
	/**
	 * When objects are read from the database, a copy of them is also stored in the cache.<br> 
	 * When asked to read an object that is already stored in the cache, the stored version is returned instead of loading it from the database.<br>
	 * This method clears the cache.
	 */
	public void clearCache()
	{
		cache.removeAllElements();
	}

	protected Databasable getFromCache(Class<? extends Databasable> cl,ID id)
	{
		if (id==null)
		{
			return null;
		}
		for (Databasable i:cache)
		{
			if ((i.getClass()==cl)&&(i.getID().equals(id)))
			{
				return i;
			}
		}
		for (Databasable i:toLoad)
		{
			if ((i.getClass()==cl)&&(i.getID().equals(id)))
			{
				return i;
			}
		}
		return null;
	}
	
	protected void addToLoad(Databasable databasable)
	{
		toLoad.add(databasable);
	}
	
	protected void addCache(Databasable databasable)
	{
		cache.add(databasable);
		toLoad.remove(databasable);
	}
	
	protected Vector<Databasable> getLoadList()
	{
		return (Vector<Databasable>) toLoad.clone();
	}
	
	protected void removeFromLoadList(Object object)
	{
		toLoad.remove(object);
	}
	
	/**
	 * Reads the object specified in the search, but none of it's child objects
	 */
	synchronized public <T extends Databasable> T readSingle(Search search)
	{
		return (T) read(search,new Class[]{});
	}
	
	/**
	 * Reads the objects specified in the search, but none of it's child objects
	 */
	synchronized public <T extends Databasable> Vector<T> readAllSingle(Search search)
	{
		return readAll(search,new Class[]{});
	}
	
	/**
	 * Limits the read objects to the classes that are specified
	 */
	synchronized public <T extends Databasable> T read(Search search,Class<? extends Databasable>...toLoad)
	{
		Databasable d=getFromCache(search.getCl(),search.getID());
		if (d!=null)
		{
			return (T) d;
		}
		ResultSet res=query(search.getText()+" LIMIT 1");
		
		Vector<T> objects=(Vector<T>) Extract.readResult(res,search.getCl(),this,toLoad==null?null:Arrays.asList(toLoad));
		return objects.size()>0?objects.get(0):null;
	}
	
	/**
	 * Limits the read objects to the classes that are specified
	 */
	synchronized public <T extends Databasable> Vector<T> readAll(Search search,Class<? extends Databasable>...toLoad)
	{
		ResultSet res=query(search.getText());
		return (Vector<T>) Extract.readResult(res,search.getCl(),this,toLoad==null?null:Arrays.asList(toLoad));
	}
	
	/**
	 * Searches and returns one in the database that meets the specifications of the {@link Search}
	 * @param search - criterion
	 * @return object that matches the search criterion; <code>null</code> if no such object exists.
	 */
	synchronized public <T extends Databasable> T read(Search search)
	{
		return this.<T>read(search,(Class<? extends Databasable>[])null);
	}
	
	/**
	 * Searches and returns all elements in the database that meet the specifications of the {@link Search}
	 * @param search - criterion
	 * @return All objects that matches the search criterion; an empty <code>Vector</code> if no such object exists.
	 */
	synchronized public <T extends Databasable> Vector<T> readAll(Search search)
	{
		return readAll(search,null);
	}

	/**
	 * Makes connection with the specified database.<br>
	 * This is <b>not</b> automatically done when creating a new instance of Database.
	 */
	public boolean connect()
	{
		if (connected)
		{
			return true;
		}
		try
		{
			Class.forName(driverClassName).newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://" + url, name, password);
			connected = true;
			loadTableNames();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return connected;
	}

	/**
	 * Closes the connection with the database after a connection has been made
	 */
	public void disconnect()
	{
		try
		{
			connect.close();
			connected = false;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void loadTableNames()
	{
		tables=new Vector<String>();
		ResultSet res=query("show tables");
		try
		{
			while (res.next())
			{
				tables.add(res.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	protected ResultSet query(String instruction)
	{
		Statement statement;
		try
		{
			statement = connect.createStatement();
			return statement.executeQuery(instruction);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private void update(String instruction)
	{
		try
		{
			Statement statement=connect.createStatement();
			statement.executeUpdate(instruction);
			statement.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}