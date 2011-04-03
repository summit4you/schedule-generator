package HTMLBuilder;


import java.util.Iterator;

import xml.ElementWithChildren;

public class HTMLTabblad extends ElementWithChildren
{
	
	private String top;
	private String bottom;
	private HTMLFunctionVector functions;
	private String name;
	
	public HTMLTabblad(String name, String top, String bottom)
	{
		setName(name);
		setTop(top);
		setBottom(bottom);
		functions = new HTMLFunctionVector();
	}
	
	@Override
	public String toString()
	{
		String out = top;
		for (HTMLFunction i:functions)
		{
			out += i.getCode();
		} 
		return out + bottom;
	}
	
	
	
	public HTMLFunctionVector getFunctions()
	{
		return functions;
	}

	public void setFunctions(HTMLFunctionVector functions)
	{
		this.functions = functions;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
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
