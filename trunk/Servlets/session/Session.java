package session;

import java.util.UUID;

/**
 * Class representing a web session </br>
 * A session needs to be created by the servlet every time a user logs in 
 * succesfully. 
 * @author Alexander
 * @version 1.0
 * @see {@link SessionTable}, {@link SessionCleaner},{@link SessionManager}
 */
public class Session
{
	
	static final long DEFAULT_LIFE_TIME = 5*60*10^3;
	
	private UUID sessionID;
	private long timeOfCreation;
	private long lifeTime;
	private User user;
	
	Session()
	{
		setUser(null);
		setTimeOfCreation();
		setLifeTime(DEFAULT_LIFE_TIME);
	}
	
	Session(User u)
	{
		setUser(u);
		setTimeOfCreation();
		setLifeTime(DEFAULT_LIFE_TIME);
	}
	
	Session(User u,long life)
	{
		setUser(u);
		setTimeOfCreation();
		setLifeTime(life);
	}
			
	private void setTimeOfCreation()
	{
		timeOfCreation=System.currentTimeMillis();
	}
	
	public long getTimeOfCreation()
	{
		return timeOfCreation;
	}

	public UUID getSessionID()
	{
		return sessionID;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

	public User getUser()
	{
		return user;
	}
	
	public void setLifeTime(long lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	public long getLifeTime()
	{
		return lifeTime;
	}
	
	/**
	 * Checks if a session has exceeded its lifetime or not. </br>
	 * Every time a servlet gets a request the session of the user has to be 
	 * refreshed in order to see if its still valid. If its valid the creation
	 * time of the session will be updated to keep the session active a little
	 * longer. In other words, only if the time between two refresh calls exceeds 
	 * the lifetime of the session it will be considered invalid. 
	 */
	public boolean refresh()
	{
		long currentTime=System.currentTimeMillis();
		if (lifeTime>(currentTime-timeOfCreation))
		{
			return false;
		}
		else
		{
			setTimeOfCreation();
			return true;
		}
	}
	
	public void end()
	{
		timeOfCreation=System.currentTimeMillis()-lifeTime+1;
	}
	
	
}
