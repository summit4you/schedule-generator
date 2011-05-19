package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Student;
import dataStructure.Subcourse;

/**
 * @author Zjef
 * @version 1.0
 */
public class StudentApplet extends JApplet
{
	private static String url;
	
	private EditVector editVector;

	private Vector<Faculty> faculties;
	private Vector<Student> students;
	
	private ProgramSelector programSelector;
	private StudentPanel studentPanel;
	private JList studentList;
	private JScrollPane studentScroll;
	private JButton removeStudent;
	private JButton createStudents;
	private JButton save;
	
	@Override
	public void init() 
	{
        url=getParameter("url");
		editVector=new EditVector();
		students=new Vector<Student>();
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
		
		programSelector=new ProgramSelector(faculties);
		programSelector.addProgramListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				confirmChange();
				Program p=programSelector.getProgram();
				studentList.setListData(new Vector<Student>());
				if (p!=null)
				{
					students=loadStudents(p);
					if (students!=null)
					{
						studentList.setListData(students);
					}
					createStudents.setEnabled(true);
				}
				else
				{
					createStudents.setEnabled(false);
				}
			}
		});
		programSelector.setBounds(0,0,600,60);
		add(programSelector);
		
		studentList=new JList();
		studentList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				Student s=(Student) studentList.getSelectedValue();
				Program p=programSelector.getProgram();
				if (s==null)
				{
					studentPanel.setVisible(false);
					removeStudent.setEnabled(false);
				}
				else
				{
					studentPanel.setVisible(true);
					studentPanel.setStudent(s,p);
					removeStudent.setEnabled(true);
				}
			}
		});
		studentScroll=new JScrollPane(studentList);
		studentScroll.setBounds(5,programSelector.getY()+programSelector.getHeight()+10,160,250);
		add(studentScroll);
		
		createStudents=new JButton("Create Students");
		createStudents.setBounds(studentScroll.getX()+studentScroll.getWidth(),studentScroll.getY(),125,25);
		createStudents.setEnabled(false);
		createStudents.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Program p=programSelector.getProgram();
				AddPanel panel=new AddPanel(p);
				if (panel.showInput())
				{
					Vector<Student> created=panel.getStudents();
					for (Student i:created)
					{
						setCounter(p, editVector,true);
						editVector.add(new Wrapper<Student>(i,Edit.added));
					}
					students.addAll(created);
					studentList.setListData(students);
				}
			}
		});
		add(createStudents);
		
		removeStudent=new JButton("Remove");
		removeStudent.setBounds(createStudents.getX(),createStudents.getY()+30,createStudents.getWidth(),createStudents.getHeight());
		removeStudent.setEnabled(false);
		removeStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (confirmDelete(" the selected students"))
				{
					Object[] studs=studentList.getSelectedValues();
					for (Object i:studs)
					{
						editVector.add(new Wrapper<Student>((Student) i,Edit.deleted));
						setCounter((Student)i,editVector);
					}
					students.removeAll(Arrays.asList(studs));
					studentList.setListData(students);
				}
			}
		});
		add(removeStudent);
		
		studentPanel=new StudentPanel(editVector, faculties);
		studentPanel.setBounds(createStudents.getX()+createStudents.getWidth()+10,createStudents.getY(),800,studentScroll.getHeight()+30);
		studentPanel.setVisible(false);
		add(studentPanel);
		
		save=new JButton("save");
		save.setBounds(studentScroll.getX(),studentScroll.getY()+studentScroll.getHeight()+30,150,25);
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
		faculties=(Vector<Faculty>)loadObject("&faculties=true&");
	}
	
	private Vector<Student> loadStudents(Program p)
	{
		return (Vector<Student>)loadObject("&students=true&program="+p.getID().toString());
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
	
	public static void setCounter(Student s,EditVector editVector)
	{
		for (Program p:s.getPrograms())
		{
			setCounter(p,editVector,false);
		}
		for (Course c:s.getCourses())
		{
			setCounter(p,editVector,false);
		}
	}
	
	public static void setCounter(Program p,EditVector editVector,boolean inc)
	{
		for (Course c:p.getCourses())
		{
			setCounter(c,editVector,inc);
		}
	}
	
	public static void setCounter(Course c,EditVector editVector,boolean inc)
	{
		for (Subcourse s:c.getSubcourses())
		{
			if (inc)
			{
				s.incStudentCounter();
			}
			else
			{
				s.decStudentCounter();
			}
			editVector.add(new Wrapper<Subcourse>(s,Edit.edited));
		}
	}
}