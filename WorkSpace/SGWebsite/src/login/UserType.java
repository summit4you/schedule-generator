package login;

import java.util.Vector;

import htmlBuilder.MainServlet;
import htmlBuilder.Site;

public abstract class UserType
{
	protected Vector<String> pseudos;
	
	UserType()
	{
		pseudos = new Vector<String>();
	}
	
	public abstract String buildMainSite(String baselink,String id);
	
	public boolean isAuthorized(String ps)
	{
		for(String p:pseudos)
		{
			if (p.equals(ps))
			{ 
				return true;
			}
		}
		return false;
	}
	

}
