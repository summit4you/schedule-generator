package mypackage;

import database.*;

public class User implements Databasable
{
 private String Password;
 private String Name;

 public User(String Name, String Password)
{
	 setName(Name);
	 setPassword(Password);
}
 
 @ OutDatabase
public void setName(String name)
{
	Name = name;
}

 @ InDatabase
public String getName()
{
	return Name;
}

@Override
public ID getId()
{
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setID(ID id)
{
	// TODO Auto-generated method stub
}

public void setPassword(String password)
{
	Password = password;
}

public String getPassword()
{
	return Password;
}
}
