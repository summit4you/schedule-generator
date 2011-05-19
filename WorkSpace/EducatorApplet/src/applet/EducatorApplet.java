package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dataStructure.Educator;
import dataStructure.Faculty;

/**
 * @author Zjef
 * @version 1.0
 */
public class EducatorApplet extends JApplet
{
	private static String url;
	
	private EditVector editVector;

	private Vector<Faculty> faculties;
	private Vector<Educator> educators;
	
	private JComboBox facultySelector;
	private EducatorPanel educatorPanel;
	private JList educatorList;
	private JScrollPane educatorScroll;
	private JButton removeEducators;
	private JButton createEducators;
	private JButton save;
	
	@Override
	public void init() 
	{
        url=getParameter("url");
		editVector=new EditVector();
		educators=new Vector<Educator>();
		loadFaculties();

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
		
		facultySelector=new JComboBox(faculties);
		facultySelector.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				confirmChange();
				Faculty f=(Faculty) facultySelector.getSelectedItem();
				educatorList.setListData(new Vector<Educator>());
				if (f!=null)
				{
					educators=loadEducators(f);
					if (educators!=null)
					{
						educatorList.setListData(educators);
					}
					createEducators.setEnabled(true);
				}
				else
				{
					createEducators.setEnabled(false);
				}
			}
		});
		facultySelector.setBounds(5,5,150,25);
		add(facultySelector);
		
		educatorList=new JList();
		educatorList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				Educator e=(Educator)educatorList.getSelectedValue();
				Faculty f=(Faculty)facultySelector.getSelectedItem();
				if (e==null)
				{
					educatorPanel.setVisible(false);
					removeEducators.setEnabled(false);
				}
				else
				{
					educatorPanel.setVisible(true);
					educatorPanel.setEducator(e,f);
					removeEducators.setEnabled(true);
				}
			}
		});
		educatorScroll=new JScrollPane(educatorList);
		educatorScroll.setBounds(5,facultySelector.getY()+facultySelector.getHeight()+10,160,250);
		add(educatorScroll);
		
		createEducators=new JButton("Create Educators");
		createEducators.setBounds(educatorScroll.getX()+educatorScroll.getWidth(),educatorScroll.getY(),145,25);
		createEducators.setEnabled(false);
		createEducators.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Faculty f=(Faculty) facultySelector.getSelectedItem();
				AddPanel panel=new AddPanel(f);
				if (panel.showInput())
				{
					Vector<Educator> created=panel.getEducators();
					for (Educator i:created)
					{
						editVector.add(new Wrapper<Educator>(i,Edit.added));
					}
					editVector.add(new Wrapper<Faculty>(f,Edit.edited));
					educators.addAll(created);
					educatorList.setListData(educators);
				}
			}
		});
		add(createEducators);
		
		removeEducators=new JButton("Remove");
		removeEducators.setBounds(createEducators.getX(),createEducators.getY()+30,createEducators.getWidth(),createEducators.getHeight());
		removeEducators.setEnabled(false);
		removeEducators.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (confirmDelete(" the selected educators"))
				{
					Faculty f=(Faculty) facultySelector.getSelectedItem();
					Object[] edus=educatorList.getSelectedValues();
					for (Object i:edus)
					{
						editVector.add(new Wrapper<Educator>((Educator) i,Edit.deleted));
						f.removeEducator((Educator) i);
					}
					editVector.add(new Wrapper<Faculty>(f,Edit.edited));
					educators.removeAll(Arrays.asList(edus));
					educatorList.setListData(educators);
				}
			}
		});
		add(removeEducators);
		
		educatorPanel=new EducatorPanel(educatorList,editVector, faculties);
		educatorPanel.setBounds(createEducators.getX()+createEducators.getWidth()+10,createEducators.getY(),800,educatorScroll.getHeight()+30);
		educatorPanel.setVisible(false);
		add(educatorPanel);
		
		save=new JButton("save");
		save.setBounds(educatorScroll.getX(),educatorScroll.getY()+educatorScroll.getHeight()+30,150,25);
		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendData();
				editVector.removeAllElements();
			}
		});
		add(save);
	}
	
	private boolean confirmDelete(String name)
	{
		return JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+name+"?")==JOptionPane.OK_OPTION;
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
	
	private void confirmChange()
	{
		if (editVector.size()!=0)
		{
			if (JOptionPane.showConfirmDialog(null,"The changes you made will be lost when changing the view. Save changes now?")==JOptionPane.OK_OPTION)
			{
				sendData();
			}
			editVector.removeAllElements();
		}
	}
	
	private void loadFaculties()
	{
		faculties=(Vector<Faculty>)loadObject("&faculties=true");
	}
	
	private Vector<Educator> loadEducators(Faculty f)
	{
		return (Vector<Educator>)loadObject("&educators=true&faculty="+f.getID().toString());
	}
	
	private Object getObjectsToWrite()
	{
		return editVector;
	}
	
	private void sendData() 
	{
		try 
		{
			URLConnection con = getServletConnection(url+"&save=true");
			OutputStream outstream = con.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstream);
			oos.writeObject(getObjectsToWrite());
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
}