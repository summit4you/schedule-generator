package sessionTracking;

import java.util.Vector;

import HTMLBuilder.DBHTMLTabblad;
import HTMLBuilder.HTMLPage;

/**
 * <b> ! Dummy class used in sessionTracking !</b> </br>
 * Has to be replaced with real user class later on. 
 * @author Alexander
 * @version 1.1
 */
public class User
{
	private String name;
	private String password;
	private Vector<DBHTMLTabblad> privileges;
	private HTMLPage page;
	
	public User(String name,String password)
	{
		this.setName(name);
		this.setPassword(password);
	}
	
	public User()
	{
		this.setName("Unkown");
	}

	public void setPrivileges(Vector<DBHTMLTabblad> privileges)
	{
		this.privileges = privileges;
	}

	public Vector<DBHTMLTabblad> getPrivileges()
	{
		return privileges;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPage(HTMLPage page)
	{
		this.page = page;
	}

	public HTMLPage getPage()
	{
		return page;
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
