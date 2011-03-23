package sessionTracking;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <b>JUnit test to check the functionalities of the package sessionTracking</b> </br>
 * - test1: Create 100 sessions with a lifetime of 1 sec. Wait 1.5 sec and see if all session
 * are removed. </br>
 * - test2: Create 100 sessions with a lifetime of 1 sec. Wait 1.5 sec and constantly see if 
 * refresh the last created session. See if this session remains.  </br>
 * - test3: Create 100 sessions with a lifetime of 5 min. Get existing and none existing
 * sessions out of the inner table  </br>
 * @author Alexander
 * @version 1.2
 * @see {@link Session}
 */
public class SessionTest
{	
	User user;
	
    Session ses;
	SessionTable table;
	SessionCleaner cleaner;
	
    @Before
    public void initTest() 
    {
    	
        user = new User();
		table = Session.getInnerTable();
		cleaner = Session.getCleaner();
		cleaner.stop();
		System.out.println("SessionTest has initialized");
    }

    @After
    public void endTest() 
    {
    	System.out.println("SessionTest has ended");
    }
    

    @Test
    public void testTest() 
    {   
    	System.out.println("SessionTest has begon:");
    	//test1:
    	System.out.print("	Test1 start...");
		for (int i=0;i<100;i++)
		{
			ses = new Session(user,1000);
		}
		cleaner.start();
		long startTime= System.currentTimeMillis();
		while (System.currentTimeMillis()-startTime<1500)
		{
		}
		cleaner.stop();
		
		assertTrue(table.getNumberOfSessions()==0);
		assertFalse(table.getEnumeration().hasMoreElements());
		assertFalse(ses.isValid());
		System.out.println("end");
		
    	//test2:
		System.out.print("	Test2 start...");
		for (int i=0;i<100;i++)
		{
			ses = new Session(user,1000);
		}
		cleaner.start();
		startTime= System.currentTimeMillis();
		while (System.currentTimeMillis()-startTime<1500)
		{
			ses.refresh();
		}
		cleaner.stop();
		
		assertTrue(table.getNumberOfSessions()==1);
		assertTrue(table.getEnumeration().nextElement()==ses);
		assertTrue(ses.isValid());
		System.out.println("end");
		
    	//test3:
		System.out.print("	Test3 start...");
		for (int i=0;i<100;i++)
		{
			ses = new Session(user);
		}
		cleaner.start();
		assertTrue(Session.getSession(ses.getSessionID())==ses);
		ses.end();
		assertFalse(Session.getSession(ses.getSessionID())==ses);
		assertFalse(Session.getSession(null)==ses);
		cleaner.stop();
		System.out.println("end");
    }


}
