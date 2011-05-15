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
	public ID getID()
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
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
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
