package login;

import xml.ElementWithValue;

public class HTMLFunction extends HTMLObject
{
	private String code;
	
	public HTMLFunction(String code)
	{
		// TODO Auto-generated constructor stub
		setCode(code);
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
