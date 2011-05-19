package login;

import java.io.Serializable;
import database.ID;

public class Account implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private ID id;
	
	private int accountNumber;
	private String userName;
	private String password;
	private String language; 
	private UserType type;
	
	public Account()
	{
		userName=null;
		password=null;
	}
	
	public Account(String name,String pass,UserType type)
	{
		this.userName = name;
		this.password = pass;
		this.language = "english";
		this.type=type;
	}	
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPassword()
	{
		return password;
	}

	public ID getID()
	{
		return id;
	}

	public void setID(ID id)
	{
		this.id=id;
	}
	
	public void setLanguage(String language)
	{
		this.language = language;
	}
	
	public String getLanguage()
	{
		return language;
	}

	public void setType(UserType type)
	{
		this.type = type;
	}
	
	public UserType getType()
	{
		return type;
	}
	
	public void setAccountNumber(int accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	@Override
	public String toString()
	{
		return userName;
	}
}