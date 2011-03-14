package session;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Class which encapsulate a Hashsable used for storing sessions. </br>
 * Only needed methods of HashTabel are made accessible. Also the number
 * of element contained in the table can now be checked.
 * @author Alexander
 * @version 1.0
 * @see {@link HashTable}, {@link Session}, {@link SessionCleaner}, {@link SessionManager}
 */

public class SessionTable 
{
	private Hashtable<UUID, Session> tabel;
	private int numberOfSessions;
	
	SessionTable()
	{
		tabel = new Hashtable<UUID, Session>();
		numberOfSessions=0;
	}
	
	public void addSession(Session ses)
	{
		if (!tabel.containsKey(ses.getSessionID()))
		{
			numberOfSessions=numberOfSessions+1;
		}
		tabel.put(ses.getSessionID(), ses);
	}
	
	public void removeSession(Session ses)
	{
		if (!tabel.containsKey(ses.getSessionID()))
		{
			numberOfSessions=numberOfSessions-1;
		}
		tabel.remove(ses.getSessionID());
	}
	
	public int getNumberOfSessions()
	{
		return numberOfSessions;
	}

	public Session getSession(UUID id)
	{
		return tabel.get(id);
	} 
	
	public Enumeration<Session> getEnumeration()
	{
		return tabel.elements();
	}
}
