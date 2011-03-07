package mypackage;

public class LoggedInID
{

	public LoggedInID(int ID,User user)
	{
		// TODO Auto-generated constructor stub
		setID(ID);
		setUser(user);
	}
	
	public LoggedInID(int ID)
	{
		// TODO Auto-generated constructor stub
		setID(ID);
	}
	
	private int ID;
	private User user;
	
	@Override
    public boolean equals(Object obj)
    {
    	// TODO Auto-generated method stub
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
