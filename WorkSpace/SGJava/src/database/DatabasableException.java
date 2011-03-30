package database;

/**
 * Exception when the {@link Databasable} interface is not implemented correctly.
 * @author Zjef
 * @version 1.0
 */
public class DatabasableException extends Exception
{
	public DatabasableException(String descr)
	{
		super(descr);
		this.printStackTrace();
	}
}