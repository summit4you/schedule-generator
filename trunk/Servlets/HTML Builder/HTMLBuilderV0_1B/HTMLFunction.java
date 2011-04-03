    package HTMLBuilder2;

import xml.ElementWithValue;

public class HTMLFunction extends Template implements HTMLable
{

	@Override
	protected void createFormat()
	{
		format = new ElementWithValue("HTMLFunction"," ");
	}
	
	@Override
	public String toHTML()
	{
		return null;
	}

}
