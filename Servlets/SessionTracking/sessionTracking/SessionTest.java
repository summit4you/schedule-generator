package sessionTracking;

public class SessionTest
{
	/**
	 * <b>Class used to for simple test </b> </br>
	 * Makes a number of sessions with different lifetimes and prints the 
	 * number of sessions in to the SessionTable. Ideal to see the 
	 * SessionCleaner add work. </br></br>
	 * This is not a JUnit test and should be replaced with one
	 * @author Alexander
	 * @version 1.1
	 * @see {@link Session},{@link SessionTable}, {@link SessionCleaner}
	 */
	public static void main(String[] args)
	{
		System.out.println("+++ main has started +++");
		User user = new User();
		SessionTable table = Session.GetInnerTable();
		SessionCleaner cleaner = Session.GetCleaner();
		Session ses = null;
		
		cleaner.stop();
		
		for (int i=0;i<60;i++)
		{
			ses = new Session(user,i*1000);
		}
		
		int i=0;
		int old=0;
		
		cleaner.start();
		
		while (table.getNumberOfSessions()>0)
		{	
			old=i;
			i=table.getNumberOfSessions();
			if (i!=old)
			{
				System.out.println(i);
			}
			
		}
		
		System.out.println("+++ main has ended +++");
	}

}
