package login;

import htmlInterfaces.HTMLTablable;
import dataStructure.*;
import database.Databasable;
import database.DatabasableException;
import database.ID;
import database.InDatabase;
import database.OutDatabase;

public class Account implements Databasable,HTMLTablable
{
	private ID id;
	
	private String userName;
	private String password;
	private String language; 
	// TODO vind hier is een mooiere manier voor
	private Student student;
	private Educator educator;
	private Admin admin;
	
	private UserType type;
	
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
	
	public Account(String name,String pass,String language, Student student, Educator educator,Admin admin,UserType type)
	{
		this.userName = name;
		this.password = pass;
		this.language = language;
		this.student = student;
		this.educator = educator;
		this.admin = admin;
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
	public ID getId()
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
	public void setStudent(Student student)
	{
		this.student = student;
	}
	
	@TableInput(order=2,text="##Student_Account##")
	@InDatabase
	public Student getStudent()
	{
		return student;
	}
	
	@OutDatabase
	public void setEducator(Educator educator)
	{
		this.educator = educator;
	}
	
	@TableInput(order=3,text="##Educator_Account##")
	@InDatabase
	public Educator getEducator()
	{
		return educator;
	}
	
	@OutDatabase
	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}
	
	@TableInput(order=4,text="##Admin_Account##")
	@InDatabase
	public Admin getAdmin()
	{
		return admin;
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

}
