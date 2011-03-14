package session;

import html.HTMLTabblad;

import java.util.Vector;

import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import database.*;
/**
 * 
 * 
 * @author Adam
 * @version 1.0
 */
public class User implements Databasable
{
	
 private String password;
 private String name;

public User(String Name, String Password)
{
	 setName(Name);
	 setPassword(Password);
}
 
 @ OutDatabase
public void setName(String name)
{
	this.name = name;
}

 @ InDatabase
public String getName()
{
	return name;
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
	this.password = password;
}

public String getPassword()
{
	return password;
}

public Vector<HTMLTabblad> getPrivileges()
{
	// TODO Auto-generated method stub
	return null;
}
}

