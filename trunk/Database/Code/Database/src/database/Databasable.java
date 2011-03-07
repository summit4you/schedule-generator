package database;

/**
 * This is an interface to declare that an object can be stored into its own
 * table of the database<br>
 * <ul>
 * <lh>Class requirements correctly implementing the interface are:</lh>
 * <li>an constructor with no arguments must be present (not needed when a class is only writing to the database, but not reading)</li>
 * <li>all objects (linked to different elements in the database) of the class must have a unique {@link ID} (which can be generated with {@link Database#getUniqueID(Class)})<br>
 * The getter and setter for 'ID' are supplied by this interface</li>
 * <li>getter methods of parameters to be stored in the database are to be annotated with {@link InDatabase @InDatabase}<br>
 * A getter method can't have any input parameters</li>
 * <li>setter methods of parameters to be loaded from the database are to be annotated with {@link OutDatabase @OutDatabase}<br>
 * A setter method has exactly one parameter, namely the value of the field to set</li>
 * <li>the name of the getter method starts with 'get' or 'is' (usually for <code>booleans</code>)<br>
 * 'get' and 'is' cannot be used both for the same field<br>
 * This means <code>getXXX</code> combined with <code>isXXX</code> in the same class is invalid.<br>
 * but '<code>getXXX</code>' and '<code>isYYY</code>' (where <code>XXX!=YYY</code>) is of course valid</li>
 * <li>the name of the setter method starts with 'set'</li>
 * <li>each setter must be accompanied by a getter with the same name, ie '<code>setXXX</code>' and '<code>getXXX</code>' (or '<code>isXXX</code>')</li>
 * <li>the 'getID' and 'setID' methods supplied by this interface must <b>not</b> be annotated with 'outDatabase' and 'inDatabase'</li><br><br>
 * <li>Currently, recursion is not supported:<br>
 * If a Databasable class <code>ClassA</code> stores a field of <code>ClassB</code> in the database, then <code>ClassB</code> is not allowed to store a field of <code>ClassA</code></li>
 * </ul>
 * 
 * @author Zjef
 * @version 0.1
 * @see InDatabase
 * @see OutDatabase
 */
public interface Databasable
{
	public static final String getter="getID", setter="setID";

	/**
	 * @return unique ID of the object in the database
	 */
	public ID getId();

	/**
	 * @param ID
	 *            - unique ID of the object in the database
	 */
	public void setID(ID id);
}