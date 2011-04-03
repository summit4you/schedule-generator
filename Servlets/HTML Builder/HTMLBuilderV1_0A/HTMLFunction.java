package HTMLBuilder;

import xml.ElementWithValue;



public class HTMLFunction extends ElementWithValue
{

	private String code;
	
	public HTMLFunction(String code, String name)
	{
		// TODO Auto-generated constructor stub
		setCode(code);
		setName(name);
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	
}
