package sessionTracking;

import java.util.UUID;

/**
 * <b>Class representing a web session. </b> </br>
 * A session needs to be created by the servlet every time a user logs in 
 * succesfully. Every session has a limited life time during which it is
 * considered valid. </br> </br>
 * The Session class contains a static SessionTable and SessionCleaner object.
 * Every session is automatically added to the table after construction and 
 * can be fetched using getSession. When the sessions lifetime has expired it
 * will be automatically removed from the table. A session can always be added
 * again with the refresh method.
 * 
 * @author Alexander
 * @version 1.2
 * @see {@link SessionTable}, {@link SessionCleaner}
 */
public class Session
{
	
	static final long DEFAULT_LIFE_TIME = 5*60*1000;
	
	static private SessionTable innerTable = new SessionTable();
	static private SessionCleaner cleaner = new SessionCleaner(innerTable);
	
	private String sessionID;
	private long timeOfCreation;
	private long lifeTime;
	private User user;
	
	public Session()
	{
		setUser(null);
		setTimeOfCreation();
		setLifeTime(DEFAULT_LIFE_TIME);
		generateID();
		addToInnerTable();
	}
	
	public Session(User u)
	{
		setUser(u);
		setTimeOfCreation();
		setLifeTime(DEFAULT_LIFE_TIME);
		generateID();
		addToInnerTable();
	}
	
	public Session(User u,long life)
	{
		setUser(u);
		setTimeOfCreation();
		setLifeTime(life);
		generateID();
		addToInnerTable();
	}
			
	private void setTimeOfCreation()
	{
		timeOfCreation=System.currentTimeMillis();
	}
	
	private void generateID()
	{
		sessionID = UUID.randomUUID().toString();
	}
	
	private void addToInnerTable()
	{
		synchronized(innerTable)
		{
			innerTable.addSession(this);
		}
	}
	
	private void removeFromInnerTable()
	{
		synchronized(innerTable)
		{
			innerTable.removeSession(this);
		}
	}
	
	public long getTimeOfCreation()
	{
		return timeOfCreation;
	}

	public String getSessionID()
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
	* Can be used to see if the lifetime of a session has expired.
	* When it does the session will be immediately removed.
	* @return true if valid </br> false if expired
	*/
	public boolean isValid()
	{
		long currentTime=System.currentTimeMillis();
		if (lifeTime<(currentTime-timeOfCreation))
		{
			removeFromInnerTable();
			return false;
		}
		else
		{
			addToInnerTable();
			return true;
		}
	}
	
	/**
	* Can be used to add a session to the table.
	*/
	public void refresh()
	{
		timeOfCreation=System.currentTimeMillis();
		addToInnerTable();
	}
	
	/**
	* removes a session from the table and makes it invalid.
	*/
	public void end()
	{
		timeOfCreation=System.currentTimeMillis()-lifeTime+1;
		removeFromInnerTable();
	}
	
	/**
	* Retruns a session from te table if the requested session is valid.
	* @return requested session or null 
	*/
	public static Session getSession(String id)
	{
		synchronized(innerTable)
		{
			Session ses = innerTable.getSession(id);
			if ((ses!=null) && ses.isValid())
			{
				return ses;
			}
			else
			{
				return null;
			}
			
		}
	}
	
	public static SessionTable getInnerTable()
	{
		return innerTable;
	}
	
	public static SessionCleaner getCleaner()
	{
		return cleaner;
	}
	
	
}

