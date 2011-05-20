package applet;

import javax.swing.JApplet;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Zjef
 * @version 1.0
 */
public class AccountApplet extends JApplet
{
	private static String guestType,educatorType,studentType;
	
	private static String url;
	private static String[] userTypes;
	
	private JTabbedPane tabs;
	
	@Override
	public void init() 
	{
		url=getParameter("url");
		userTypes=getParameter("userTypes").split(",");
		guestType=getParameter("guest");
		studentType=getParameter("student");
		educatorType=getParameter("educator");
		
		try 
		{
			SwingUtilities.invokeAndWait(new Runnable() 
			{
				@Override
				public void run() 
				{
					createGUI();
                }
            });
        } catch (Exception e) 
        {
        	e.printStackTrace();
        }
	}
	
	private void createGUI()
	{
		setLayout(null);
		
		tabs=new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				((AccountPanel)tabs.getSelectedComponent()).notifyActiveTab();
			}
		});
		
		for (String i:userTypes)
		{
			if (!i.equals(guestType))
			{
				tabs.addTab(i,new AccountPanel(i,url,getCodeBase(),isAdmin(i)));
			}
		}
		
		setContentPane(tabs);
	}
	
	private static boolean isAdmin(String name)
	{
		return !(name.equals(studentType)||name.equals(educatorType)||name.equals(guestType));
	}
}