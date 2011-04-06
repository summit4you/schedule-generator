package dataStructure;

import database.*;

public class Admin implements Databasable
{
	private String name; 
	private ID id;
	
	public Admin()
	{
		
	}
	
	@Override
	public ID getId()
	{
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setID(ID id)
	{
		// TODO Auto-generated method stub
		this.id = id;
	}
	
	@OutDatabase
	public void setName(String name)
	{
		this.name = name;
	}
	
	@InDatabase
	public String getName()
	{
		return "admin";
	}

}
