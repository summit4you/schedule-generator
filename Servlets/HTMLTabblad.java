package login;

import java.util.Vector;

import xml.ElementWithValue;

public class HTMLTabblad extends HTMLObject
{
	private String top;
	private String bottom;
	private Vector<HTMLFunction> functions;
	
	public String toString()
	{
		String out = top;
		for (int i = 0; i < functions.size(); i++)
		{
			out = out + functions.get(i).getCode();
		} 
		out = out + bottom;
		return out;
	}
	
	public void addFunction(HTMLFunction function)
	{
		functions.add(function);
	}
	
	
	
	public void setTop(String top)
	{
		this.top = top;
	}
	public String getTop()
	{
		return top;
	}
	public void setBottom(String bottom)
	{
		this.bottom = bottom;
	}
	public String getBottom()
	{
		return bottom;
	}
	
	

	
	
	
	
}
