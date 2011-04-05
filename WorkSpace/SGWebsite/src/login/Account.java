package login;

import dataStructure.*;
import database.Databasable;
import database.DatabasableException;
import database.ID;
import database.InDatabase;
import database.OutDatabase;

public class Account implements Databasable
{
	private ID id;
	
	private String userName;
	private String password;
	private String language; 
	private Student student;
	private Educator educator;
	private Admin admin;
	
	public Account()
	{
		userName=null;
		password=null;
	}
	
	public Account(String name,String pass)
	{
		userName=name;
		password=pass;
	}
	
	@OutDatabase
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	@InDatabase
	public String getUserName()
	{
		return userName;
	}
	
	@OutDatabase
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	@InDatabase
	public String getPassword()
	{
		return password;
	}

	@Override
	public ID getId()
	{
		return id;
	}

	@Override
	public void setID(ID id)
	{
		this.id=id;
	}

}
