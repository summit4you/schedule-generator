package database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Represents a MySQLDatabase<br>
 * An object of this class provides methods to read and write objects from and to the database.<br>
 * Read the requirements at {@link Databasable} to enable classes to read and write their objects to a database.
 * 
 * @author Zjef
 * @version 0.2 - methods (which enable an interface to the database) are declared and visible for external code, but are not implemented
 */
public class Database implements Serializable
{
	final private static String driverClassName = "com.mysql.jdbc.Driver";

	private String url,name,password;
	private boolean connected;
	private Connection connect;

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
	 * Returns a unique ID for an object from the specified class.<br>
	 * This automatically reserves this ID. If for some reason the object, to which this ID is allocated, is never written to this database ({@link #write(Databasable) write}), release the use of this ID again by calling {@link #delete(Class, ID)}<br>
	 * This ensures that the ID can be used again in the future.
	 * @param cl - class of the object for which to receive a unique ID
	 * @return - unique ID for the class
	 * @see #releaseID(ID)
	 */
	public ID getUniqueID(Class<? extends Databasable> cl)
	{
		//TODO
		return null;
	}
	
	/**
	 * Writes a {@link Databasable} object into its table in this database.<br>
	 * The ID of the object is checked. If an element in the table with the same ID already exists, it is overwritten, otherwise a new element is created in the table<br><br>
	 * If the specified object has any getters that return other {@link Databasable} objects (or Vectors containing such objects), then these objects will also be written/updated in this database if <code>updateRef</code> is <code>true</code>.<br><br>
	 * If this table does not exist, it is created. The table has the same name as the class of the object.
	 * @param object - object to write/update in this database
	 * @param updateRef - whether the referenced Databasable objects have to be updated too (<code>true</code>), or if only their ID has to be stored (<code>false</code>)
	 * @see #writeAll(Vector)
	 */
	public void write(Databasable object,boolean updateRef)
	{
		//TODO
	}
	
	/**
	 * Writes all elements of the vector to their table in this database.<br>
	 * If the specified objects have any getters that return other {@link Databasable} objects (or Vectors containing such objects), then these objects will also be written/updated in this database if <code>updateRef</code> is <code>true</code>.<br><br>
	 * The ID's of the objects are checked. If an element in the table with the same ID already exists, it is overwritten, otherwise a new element is created in the table<br><br>
	 * All objects in this vector need to be instances of the same class.
	 * @param objects - objects to write
	 * @param updateRef - whether the referenced Databasable objects have to be updated too (<code>true</code>), or if only their ID has to be stored (<code>false</code>)
	 * @see #write(Databasable)
	 */
	public void writeAll(Vector<? extends Databasable> objects,boolean updateRef)
	{
		//TODO
	}
	
	/**
	 * Deletes an object from its table in this database.<br>
	 * This method only checks for a match between the ID from the element in the table and the specified object.
	 * @param object - the object to delete
	 *  @see #delete(Class, ID)
	 * @see #deleteAll(Vector)
	 * @see #deleteAll(Class)
	 * @see #deleteAll(Class, Vector)
	 */
	public void delete(Databasable object)
	{
		//TODO
	}
	
	/**
	 * Deletes all objects from their table in this database.<br>
	 * This method only checks for a match between the ID from the element in the table and the specified object.<br><br>
	 * All objects need to be instances of the same class.
	 * @param objects - objects to delete
	 * @see #delete(Databasable)
	 * @see #delete(Class, ID)
	 * @see #deleteAll(Class)
	 * @see #deleteAll(Class, Vector)
	 */
	public void deleteAll(Vector<? extends Databasable> objects)
	{
		//TODO
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
		//TODO
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
		//TODO
	}
	
	/**
	 * Deletes all elements associated with this class from this database.
	 * @param cl - class of which to delete all entries in its table of this database
	 * @see #delete(Databasable)
	 * @see #deleteAll(Vector)
	 */
	public void deleteAll(Class<? extends Databasable> cl)
	{
		//TODO
	}
	
	/**
	 * Searches and returns the first element in the database that meets the specifications of the {@link Search}
	 * @param search - criterion
	 * @return First object that matches the search criterion; <code>null</code> if no such object exists.
	 */
	public <T extends Databasable> T read(Search search)
	{
		//TODO
		return null;
	}
	
	/**
	 * Searches and returns the all elements in the database that meet the specifications of the {@link Search}
	 * @param search - criterion
	 * @return All objects that matches the search criterion; <code>null</code> if no such object exists.
	 */
	public <T extends Databasable> Vector<T> readAll(Search search)
	{
		//TODO
		return null;
	}

	/**
	 * Makes connection with the specified database
	 * 
	 * @throws ClassNotFoundException
	 *             when the JDBC driver is not present
	 * @throws SQLException
	 *             if no connection could be established with the database. Make
	 *             sure URL,account name and password are all correct.
	 */
	public void connect() throws ClassNotFoundException, SQLException
	{
		if (connected)
		{
			return;
		}
		Class.forName(driverClassName);
		connect = DriverManager.getConnection("jdbc:mysql://" + url, name, password);
		connected = true;
	}

	/**
	 * Closes the connection with the database after a connection has been made
	 * 
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException
	{
		connect.close();
		connected = false;
	}
}