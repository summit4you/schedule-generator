package login;

import java.io.Serializable;

import htmlInterfaces.HTMLTablable;
import database.Databasable;
import database.Database;
import database.ID;
import database.InDatabase;
import database.OutDatabase;
import database.Search;

public class Account implements Databasable,HTMLTablable,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ID id;
	
	private int accountNumber;
	private String userName;
	private String password;
	private String language; 
	
	private Databasable data;
	
	private UserType type;
	
	public Account()
	{
		userName=null;
		password=null;
	}
	
	public Account(String name,String pass,String language,UserType type)
	{
		this.userName = name;
		this.password = pass;
		this.language = language;
		this.type=type;
	}	
	
	@OutDatabase
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	@TableInput(order=1,text="##UserName_Account##")
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
	public ID getID()
	{
		return id;
	}

	@Override
	public void setID(ID id)
	{
		this.id=id;
	}
	
	@OutDatabase
	public void setLanguage(String language)
	{
		this.language = language;
	}
	
	@InDatabase
	public String getLanguage()
	{
		return language;
	}

	@OutDatabase
	public void setType(UserType type)
	{
		this.type = type;
	}
	
	@TableInput(order=5,text="##UserType_Account##")
	@InDatabase
	public UserType getType()
	{
		return type;
	}
	
	public Databasable getData()
	{
		if (data==null)
		{
			loadData();
		}
		return data;
	}

	@OutDatabase
	public void setAccountNumber(int accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	private void loadData()
	{
		if (type!=null)
		{
			Class c=type.getTypeClass();
			if (c!=null && accountNumber!=0)
			{
				Database db=Database.getDB();
				db.connect();
				data=db.read(new Search(c,Integer.toString(accountNumber)));
				db.disconnect();
			}
		}
	}

	@InDatabase
	@TableInput(order=6,text="##UserName_Account##")
	public int getAccountNumber()
	{
		return accountNumber;
	}
}