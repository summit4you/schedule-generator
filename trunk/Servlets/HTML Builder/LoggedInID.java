package login;
/**
 * 
 * 
 * @author Adam
 * @version 1.0
 */
public class LoggedInID
{	
	private int ID;
	private User user;
	
	public LoggedInID(int ID,User user)
	{
		setID(ID);
		setUser(user);
	}
	
	public LoggedInID(int ID)
	{
		setID(ID);
	}
	
	@Override
    public boolean equals(Object obj)
    {
    	if (obj instanceof LoggedInID) {
    		return ((LoggedInID) obj).ID==this.ID;
    	}
    	return false;
    }
	
	public void setID(int ID)
	{
		this.ID = ID;
	}
	public int getID()
	{
		return ID;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public User getUser()
	{
		return user;
	}
	
}

