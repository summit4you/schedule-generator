package login;

import java.io.Serializable;

public class UserType implements Serializable
{		
	private static final long serialVersionUID = 1L;
	
	protected String value;
	
	public UserType(String value)
	{
		this.value=value;
	}
}