package session;

import java.util.UUID;

/**
 * Class which contains a sessionTabel and sessionCleaner.</br>
 * This class facilitates the use of sessions by ensuring that
 * the sessionTable is only accessed in a synchronized way. All
 * session created by this class will automatically be added to
 * the contained table.
 * @author Alexander
 * @version 1.0
 * @see  {@link Session}, {@link SessionTable}, {@link SessionCleaner}
 */
public class SessionManager
{
	private SessionCleaner cleaner;
	private SessionTable tabel;
	
	SessionManager()
	{
		tabel = new SessionTable();
		cleaner = new SessionCleaner(tabel);
	}
	
	public Session makeSession(User u)
	{
		Session ses = new Session(u);
		synchronized(tabel)
		{
			tabel.addSession(ses);	
		}
		return ses;
	}
	
	public void deleteSession(Session ses)
	{
		synchronized(tabel)
		{
			tabel.removeSession(ses);	
		}
	}
	
	public void getSession(UUID id)
	{
		synchronized(tabel)
		{
			tabel.getSession(id);	
		}
	}
	
	public void startCleaner()
	{
		cleaner.start();
	}
	
	public void stopCleaner()
	{
		cleaner.stop();
	}
}
