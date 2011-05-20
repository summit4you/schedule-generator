package database;

import java.io.Serializable;

public class ID implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int value;
	
	protected ID(int value)
	{
		this.value=value;
	}
	
	protected ID(String value)
	{
		this.value=Integer.parseInt(value);
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(value);
	}
	
	public int getValue()
	{
		return value;
	}
	
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