package database;

import java.io.Serializable;
import java.lang.reflect.Method;
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
 * @version 0.1 - methods (which enable an interface to the database) are declared and visible for external code, but are not implemented
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
	 * If the specified object has any getters that return other {@link Databasable} objects (or Vectors containing such objects), then these objects will also be written/updated in this database.<br><br>
	 * If this table does not exist, it is created. The table has the same name as the class of the object.
	 * @param object - object to write/update in this database
	 * @see #writeAll(Vector)
	 */
	public void write(Databasable object)
	{
		//TODO
	}
	
	/**
	 * Writes all elements of the vector to their table in this database.<br>
	 * If the specified objects have any getters that return other {@link Databasable} objects (or Vectors containing such objects), then these objects will also be written/updated in this database.<br><br>
	 * The ID's of the objects are checked. If an element in the table with the same ID already exists, it is overwritten, otherwise a new element is created in the table<br><br>
	 * All objects in this vector need to be instances of the same class.
	 * @param objects - objects to write
	 * @see #write(Databasable)
	 */
	public void writeAll(Vector<? extends Databasable> objects)
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
	 * Returns 1 element from a table in this database, selected by its ID;
	 * @param cl - class of the object to be fetched
	 * @param id - ID of the object to be fetched
	 * @return the object stored in this database with the specified ID.<br>
	 * If no element with this ID exists, or if the table does not exist, this methods returns <code>null</code>
	 * @see #read(T, Method)
	 * @see #read(T, String)
	 * @see #read(T, Vector)
	 * @see #readAll(Class)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, String)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> T read(Class<T> cl,ID id)
	{
		//TODO
		return null;
	}
	
	/**
	 * Reads all elements from a table in this database.<br>
	 * Returns an empty vector if this table is empty or does not exist.
	 * @param cl - class (implementing {@link Databasable}) of the elements to load
	 * @return vector with all elements of the specified table
	 * @see #read(Class, ID)
	 * @see #read(T, Method)
	 * @see #read(T, String)
	 * @see #read(T, Vector)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, String)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> Vector<T> readAll(Class<T> cl)
	{
		//TODO
		return null;
	}
	
	/**
	 * Returns the first element in the table (linked to the class of the getter) of this database that fits the constraint imposed by the getter method.<br>
	 * @param getter - getter method (that has to annotated with {@link InDatabase}) with which to extract the first element from the table
	 * @param object - object from which to check the getter.
	 * @return the first element in the database that fits the constraint imposed by the getter method; <code>null</code> if no such element exists or the table does not exist.<br>
	 * Also returns <code>null</code> when T does not equal
	 * @see #read(Class, ID)
	 * @see #read(T, String)
	 * @see #read(T, Vector)
	 * @see #readAll(Class)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, String)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> T read(T object,Method getter)
	{
		return null;
	}
	
	/**
	 * Returns the first element in the table (linked to the class of the getters) of this database that fits all constraints imposed by all the getters.
	 * @param getters - getter methods (all annotated with {@link InDatabase}) that determine the constraints to select elements from the table in this database.<br>
	 * All getters need to be declared in the same class implementing {@link Databasable}.
	 * @param object - object from which to check the getters
	 * @return the first element in the table of this database that fits all constraints imposed by the getters; <code>null</code> if no such element exists or the table does not exist.
	 * @see #read(Class, ID)
	 * @see #read(T, Method)
	 * @see #read(T, String)
	 * @see #readAll(Class)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, String)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> T read(T object,Vector<Method> getters)
	{
		//TODO
		return null;
	}
	
	/**
	 * Returns all elements in the table (linked to the class of the getter) of this database that fit the constraints imposed by the getter.
	 * @param getter - getter method that determines the constraint to select elements from the table in this database.
	 * @param object - object from which to check the getter 
	 * @return a vector with all objects that fit the constraint imposed by the getter; returns an empty vector if none of the elements fits the constraint or if the table does not exist.
	 * @see #read(Class, ID)
	 * @see #read(T, Method)
	 * @see #read(T, String)
	 * @see #read(T, Vector)
	 * @see #readAll(Class)
	 * @see #readAll(T, String)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> Vector<T> readAll(T object,Method getter)
	{
		//TODO
		return null;
	}
	
	/**
	 * Returns all elements in the table (linked to the class of the getters) of this database that fit the constraints imposed by all the getters.
	 * @param getters - getter methods (all annotated with {@link InDatabase}) that determine the constraints to select elements from the table in this database.<br>
	 * All getters need to be declared in the same class implementing {@link Databasable}.
	 * @param object - object from which to check the getters
	 * @return a vector with all objects that fit the constraints imposed by the getters; returns an empty vector if none of the elements fits all constraints or if the table does not exist.
	 * @see #read(Class, ID)
	 * @see #read(T, Method)
	 * @see #read(T, String)
	 * @see #read(T, Vector)
	 * @see #readAll(Class)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, String)
	 */
	public <T extends Databasable> Vector<T> readAll(T object,Vector<Method> getters)
	{
		//TODO
		return null;
	}
	
	/**
	 * Behaves the same as {@link #readAll(T, Vector)}, but the methods are specified by their name in a String.<br><br>
	 * Use of this function is discouraged by Zjef, since it is not safe against spelling mistakes and refactoring, whereas {@link #readAll(Databasable, Vector)} is.<br>
	 * But it is included for its convenience.<br><br>
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
	 * @param methods - name(s) of the getter(s) that determine the constraints to select the elements from the table in this database.<br>
	 * All getters need to be declared in the same class implementing {@link Databasable}.
	 * @param object - object from which to check the getters
	 * @return a vector containing all objects that fit the constraints imposed by the getter(s); returns an empty vector if none of the elements fits all constraints or if the table does not exist.
	 * @see #read(Class, ID)
	 * @see #read(T, Method)
	 * @see #read(T, Vector)
	 * @see #readAll(Class)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, String)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> T read(T object,String methods)
	{
		//TODO
		return null;
	}
	
	/**
	 * Does the same as {@link #read(T,String)}, but instead of returning the first matching element, this method returns all matching elements.
	 * @see #read(Class, ID)
	 * @see #read(T, Method)
	 * @see #read(T, String)
	 * @see #read(T, Vector)
	 * @see #readAll(Class)
	 * @see #readAll(T, Method)
	 * @see #readAll(T, Vector)
	 */
	public <T extends Databasable> Vector<T> readAll(T object,String methods)
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