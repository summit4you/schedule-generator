package sessionTracking;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

/**
 * <b>Class which encapsulate a Hashsable used for storing sessions.</b> </br>
 * Only needed methods of HashTabel are made accessible. Also the number
 * of element contained in the table can now be checked.
 * @author Alexander
 * @version 1.2
 * @see {@link HashTable}, {@link Session}, {@link SessionCleaner}
 */

public class SessionTable 
{
	private Hashtable<String, Session> table;
	private int numberOfSessions;
	
	SessionTable()
	{
		table = new Hashtable<String, Session>();
		numberOfSessions=0;
	}
	
	public void addSession(Session ses)
	{
		if (!table.containsKey(ses.getSessionID()))
		{
			numberOfSessions=numberOfSessions+1;
		}
		table.put(ses.getSessionID(), ses);
	}
	
	public void removeSession(Session ses)
	{
		if (table.containsKey(ses.getSessionID()))
		{
			numberOfSessions=numberOfSessions-1;
		}
		table.remove(ses.getSessionID());
	}
	
	public int getNumberOfSessions()
	{
		return numberOfSessions;
	}

	public Session getSession(String id)
	{
		if (id!=null)
		{
			return table.get(id);
		}
		return null;
	} 
	
	public Enumeration<Session> getEnumeration()
	{
		return table.elements();
	}
}

