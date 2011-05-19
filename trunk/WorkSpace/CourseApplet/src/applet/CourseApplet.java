package applet;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import dataStructure.Faculty;
import dataStructure.Program;

public class CourseApplet extends JApplet
{
	private static String url;
	
	private EditVector editVector;

	private Vector<Faculty> faculties;
	private Program zeroProgram;
	
	private JTabbedPane tabbedPane;
	private FacultyTab fTab;
	private ProgramTab pTab;
	private CourseTab cTab;
	private SubcourseTab sTab;
	private ZeroProgramTab zTab;
	private SaveTab saveTab;
	
	private JComboBox fBox,pBox,cBox;
	private JList fList,pList,cList,sList;
	private JScrollPane fPane,pPane,cPane,sPane;
	private ListManager manager;
	
	@Override
	public void init() 
	{
		url=getParameter("url");
		editVector=new EditVector();
		loadFaculties();
		loadZeroProgram();
		setSize(900,400);
		
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
		
		fBox=new JComboBox((Vector<Faculty>)faculties.clone());
		fBox.setBounds(5,5,170,25);
		pBox=new JComboBox();
		pBox.setBounds(fBox.getX()+fBox.getWidth()+25,fBox.getY(),fBox.getWidth(),fBox.getHeight());
		cBox=new JComboBox();
		cBox.setBounds(pBox.getX()+pBox.getWidth()+25,pBox.getY(),pBox.getWidth(),pBox.getHeight());
		
		fList=new JList(faculties);
		pList=new JList();
		cList=new JList();
		sList=new JList();
		
		fPane=new JScrollPane(fList);
		fPane.setBounds(fBox.getX(),fBox.getY()+fBox.getHeight()+20,fBox.getWidth(),200);
		pPane=new JScrollPane(pList);
		pPane.setBounds(fPane.getBounds());
		cPane=new JScrollPane(cList);
		cPane.setBounds(pPane.getBounds());
		sPane=new JScrollPane(sList);
		sPane.setBounds(cPane.getBounds());
		
		tabbedPane=new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
		
		manager=new ListManager(faculties,zeroProgram,editVector, fList, pList, cList, sList, fBox, pBox, cBox);
		
		fTab=new FacultyTab(fPane,fList,manager);
		tabbedPane.addTab("Faculties",fTab);
		
		pTab=new ProgramTab(pPane,pList,manager);
		pTab.addComponentListener(new tabListener(){
			@Override
			public void componentShown(ComponentEvent e)
			{
				pTab.addBoxAndList(fBox);
				pTab.repaint();
			}
		});
		tabbedPane.addTab("Programs",pTab);
		
		cTab=new CourseTab(cPane,cList,manager);
		cTab.addComponentListener(new tabListener(){
			@Override
			public void componentShown(ComponentEvent e)
			{
				cTab.addBoxAndList(fBox,pBox);
				cTab.repaint();
			}
		});
		tabbedPane.addTab("Courses",cTab);
		
		sTab=new SubcourseTab(sPane,sList,manager);
		sTab.addComponentListener(new tabListener(){
			@Override
			public void componentShown(ComponentEvent e)
			{
				sTab.addBoxAndList(fBox,pBox,cBox);
				sTab.repaint();
			}
		});
		tabbedPane.addTab("Subcourses",sTab);
		
		zTab=new ZeroProgramTab(zeroProgram,manager);
		tabbedPane.addTab("Trash",zTab);
		
		saveTab=new SaveTab();
		saveTab.addComponentListener(new ComponentListener()
		{
			@Override
			public void componentShown(ComponentEvent e)
			{
				if (JOptionPane.showConfirmDialog(null,"Save changes?","Saving",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				{
					sendData();
				}
				tabbedPane.setSelectedIndex(0);
			}
			@Override
			public void componentResized(ComponentEvent e)
			{
			}
			@Override
			public void componentMoved(ComponentEvent e)
			{	
			}
			@Override
			public void componentHidden(ComponentEvent e)
			{	
			}
		});
		tabbedPane.addTab("Save",saveTab);
		
		manager.fireListeners();
		
		setContentPane(tabbedPane);
	}
	
	private URLConnection getServletConnection(String url) throws MalformedURLException, IOException 
	{
		URL urlServlet = new URL(getCodeBase(),url+"&applet=true");
		URLConnection con = urlServlet.openConnection();
		
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type","application/x-java-serialized-object");
		
		return con;
	}
	
	private Object loadObject(String urlParams)
	{
		try 
		{
			URLConnection con = getServletConnection(url+urlParams);
			OutputStream outstream = con.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstream);
			oos.writeObject("hello");
			oos.flush();
			oos.close();

			InputStream instr = con.getInputStream();
			ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
			Object o=inputFromServlet.readObject();
			inputFromServlet.close();
			instr.close();
			return o;
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	private void loadZeroProgram()
	{
		zeroProgram=(Program)loadObject("&zeroProgram=true");
	}
	
	private void loadFaculties()
	{
		faculties=(Vector<Faculty>)loadObject("&faculties=true");
	}
	
	public void sendData() 
	{
		try 
		{
			URLConnection con = getServletConnection(url+"&save=true");
			OutputStream outstream = con.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstream);
			oos.writeObject(editVector);
			oos.flush();
			oos.close();
			
			InputStream instr = con.getInputStream();
			ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
			String res=(String) inputFromServlet.readObject();
			inputFromServlet.close();
			instr.close();
			JOptionPane.showMessageDialog(null,res);
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	private abstract class tabListener implements ComponentListener
	{
		public tabListener()
		{
			super();
		}
		@Override
		public void componentHidden(ComponentEvent e)
		{	
		}
		@Override
		public void componentMoved(ComponentEvent e)
		{	
		}
		@Override
		public void componentResized(ComponentEvent e)
		{
		}
	}
}