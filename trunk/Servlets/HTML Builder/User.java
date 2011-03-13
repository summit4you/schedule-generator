package login;

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
 private Vector<DBHTMLTabblad> privileges;
 private HTMLPage page;
 
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

public void setPrivileges(Vector<DBHTMLTabblad> privileges)
{
	this.privileges = privileges;
}

public Vector<DBHTMLTabblad> getPrivileges()
{
	return privileges;
}

public void setPage(HTMLPage page)
{
	this.page = page;
}

public HTMLPage getPage()
{
	return page;
}
}

