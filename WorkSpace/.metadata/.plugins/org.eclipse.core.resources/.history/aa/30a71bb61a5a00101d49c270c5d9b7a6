package database;

import java.io.Serializable;

/**
 * ID for Databasable objects<br>
 * This is a required field for all classes implementing {@link Databasable}.<br>
 * A unique ID can be generated with {@link Database#getUniqueID(Class)}, but can also be managed externally.<br>
 * To check if two ID's (assigned to two objects from the same class) are equal, use {@link #equals(Object)}
 * @author Zjef
 * @version 1.0
 */
public class ID implements Serializable
{
	private int value;
	
	/**
	 * 
	 * @param value
	 */
	protected ID(int value)
	{
		this.value=value;
	}
	
	protected ID(String value)
	{
		this.value=Integer.parseInt(value);
	}
	
	/**
	 * @return the value of this ID in a decimal format
	 */
	@Override
	public String toString()
	{
		return Integer.toString(value);
	}
	
	/**
	 * @return the integer value associated with this ID
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * Checks if the numerical values of the ID's are equal. This method does not check if the ID's are assigned to objects from different classes.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ID)
		{
			return ((ID) obj).value==this.value;
		}
		return false;
	}
}