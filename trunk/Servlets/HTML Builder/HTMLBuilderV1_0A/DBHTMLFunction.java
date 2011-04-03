package HTMLBuilder;

import database.Databasable;
import database.ID;

public class DBHTMLFunction implements Databasable
{
	private String name;

	@Override
	public void setID(ID id)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ID getId()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	
}
