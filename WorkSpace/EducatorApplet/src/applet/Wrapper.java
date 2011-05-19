package applet;

import java.io.Serializable;

/**
 * @author Zjef
 * @version 1.0
 */
public class Wrapper<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private T object;
	private Edit edit;
	
	public Wrapper(T object)
	{
		this.object=object;
		this.edit=Edit.nothing;
	}
	
	public Wrapper(T object,Edit edit)
	{
		this.object=object;
		this.edit=edit;
	}
	
	public T getObject()
	{
		return object;
	}
	
	public Wrapper<T> setEdit(Edit edit)
	{
		this.edit=edit;
		return this;
	}
	
	public Edit getEdit()
	{
		return edit;
	}
	
	@Override
	public String toString() 
	{
		return object.toString();
	};
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Wrapper)
		{
			return ((Wrapper) obj).object.equals(this.object);
		}
		return false;
	}
}