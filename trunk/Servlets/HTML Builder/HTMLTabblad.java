package login;

import java.util.Vector;

import xml.ElementWithChildren;

public class HTMLTabblad extends ElementWithChildren
{
	private String top;
	private String bottom;
	private Vector<HTMLFunction> functions;
	private String name;
	
	public HTMLTabblad(String name, String top, String bottom)
	{
		setName(name);
		setTop(top);
		setBottom(bottom);
	}
	
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
	
	public Vector<HTMLFunction> getFunctions()
	{
		return functions;
	}

	public void setFunctions(Vector<HTMLFunction> functions)
	{
		this.functions = functions;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void addFunction(HTMLFunction function)
	{
		functions.add(function);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (HTMLObject.class==obj.getClass())
		{
		return name.equals(((HTMLObject) obj).getName());
		}
		return false;
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
