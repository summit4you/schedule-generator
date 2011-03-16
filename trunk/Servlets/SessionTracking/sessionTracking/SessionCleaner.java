package sessionTracking;

import java.util.Enumeration;
/**
 * <b>Class used to remove old sessions from a sessionTable</b> </br>
 * This class implements the runnable interface but contains 
 * its own thread. So no extra threads are needed to make use
 * of this class. The table assigned to an instance of this 
 * class should be accessed in a synchronized way. This is not
 * a concern if you use the methods of the Session class.
 * @author Alexander
 * @version 1.1
 * @see  {@link Session}, {@link SessionTable}
 */
public class SessionCleaner implements Runnable
{
	private Thread thread;
	private SessionTable table;
	private int sleepTime;
	private boolean running;
		
	SessionCleaner(SessionTable t)
	{
		table=t;
		sleepTime=5*60;
		start();
	}
	
	SessionCleaner(SessionTable t,int sleep)
	{
		table=t;
		sleepTime=sleep;
		start();
	}
	
	/**
	 * The table of this class will be checked sequentially to see
	 * if it contains no invalid sessions. After this the thread 
	 * will sleep a specified amount of time. This process is repeated
	 * until the thread is stopped. If no table was assigned the thread
	 * will still remain active.
	 */
	@Override
	public void run()
	{
		while (running)
		{
			if (table!=null)
			{
				Enumeration<Session> e;
				
				synchronized(table)
				{
					e= table.getEnumeration();
				}
				
				while(e.hasMoreElements())
				{
						Session session=e.nextElement();
						session.isValid();
				}	
			}
			try
			{
				Thread.sleep(sleepTime);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public void start()
	{
		running = true;
		if (thread==null || !thread.isAlive())
		{
			thread = new Thread(this);
			thread.start();	
		}
	}
	
	public void stop()
	{
		running = false;
	}

	public int getSleepTime()
	{
		return sleepTime;
	}
	
	public void setSleepTime(int sleep)
	{	
		sleepTime=sleep;
	}
}

