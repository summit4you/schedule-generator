package database;

/**
 * Implementation of a {@link DatabasableObject}.<br>
 * Objects from this class cannot be stored as such on their own in the database.<br>
 * This means they have no separate table in the database.<br><br>
 * They are stored indirectly in the database when an object of this interface belongs to another Databasable object.<br>
 * The stored value is determined by the method {@link #toValue()}.<br><br>
 * When an object is loaded that references an object of this interface, the method {@link #loadFromValue(Object)} is called.<br>
 * The user should implement this method in a way that the object is initializes in this method.<br><br>
 * 
 * This interface has the following implementation requirements:
 * <ul>
 * <li>constructor without parameters</li>
 * </ul>
 * That's it!<br><br>
 * 
 * A databasable object cannot have a field of the type DatabasableAsValue,DatabasableAsInt,DatabasableAsString,...<br>
 * The field must be a class that implements one of these interfaces (that really implements the methods toValue and loadFromValue)<br><br>
 * 
 * Following subinterfaces are provided in this package:
 * <ul>
 * <li>{@link DatabasableAsInt}</li>
 * <li>{@link DatabasableAsString}</li>
 * </ul>
 * To create extra of these interfaces (with a type of your choice):
 * <ul>
 * <li>Create a new interface extending DatabasableAsValue&ltE&gt</li>
 * <li>Replace E with the type you want</li>
 * </ul>
 * It's that simple!
 * <br><br>
 * You can use a class implementing Databasable as E, but be aware of how the database loads these elements.<br>
 * The database creates a dummy object holding the correct ID, but nothing else.<br>
 * So when the loadFromValue method is called, the object is not completely loaded yet; only the ID is set.<br>
 * Only when the method database.read() returns is it sure all Databasable objects are loaded completely. 
 * @author Zjef
 * @version 2.0
 */
public interface DatabasableAsValue<E> extends DatabasableObject
{
	String toValueName="toValue";
	
	public E toValue();
	
	public void loadFromValue(E value);
}