package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import swingExtensions.RestrictedTextField;
import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Subcourse;

public class EducatorPanel extends JPanel
{
	private EditVector editVector;
	private Educator educator;
	private Faculty faculty;
	private Vector<Faculty> faculties;
	
	private JTextField surname;
	private JTextField firstName;
	private JList courseList;
	private JList subcourseList;
	private JScrollPane coursePane;
	private JScrollPane subcoursePane;
	private RestrictedTextField educatorNumber;
	private JButton addCourse;
	private JButton addSubcourse;
	private JButton removeCourse;
	private JButton removeSubcourse;
	
	private JList list;
	
	public EducatorPanel(JList list,EditVector editVector,Vector<Faculty> faculties)
	{
		super(null);
		this.list=list;
		this.faculties=faculties;
		this.editVector=editVector;
		initPanel();
	}
	
	public void setEducator(Educator educator,Faculty faculty)
	{
		this.educator=educator;
		this.faculty=faculty;
		surname.setText(educator.getSurName());
		firstName.setText(educator.getFirstName());
		educatorNumber.setText(Integer.toString(educator.getemployeeNumber()));
		courseList.setListData(educator.getCourses());
		subcourseList.setListData(educator.getSubcourses());
	}
	
	private void initPanel()
	{
		JLabel slabel=new JLabel("surname");
		slabel.setBounds(5,5,1000,25);
		add(slabel);
		surname=new JTextField();
		surname.setBounds(100,slabel.getY(),140,25);
		surname.addKeyListener(new Listener(list){

			@Override
			protected void doStuff()
			{
				educator.setSurName(surname.getText());
			}
		});
		add(surname);
		
		JLabel flabel=new JLabel("first name");
		flabel.setBounds(surname.getX()+surname.getWidth()+5,surname.getY(),slabel.getWidth(),slabel.getHeight());
		add(flabel);
		firstName=new JTextField();
		firstName.setBounds(flabel.getX()+100,flabel.getY(),surname.getWidth(),surname.getHeight());
		firstName.addKeyListener(new Listener(list){
			@Override
			protected void doStuff()
			{
				educator.setFirstName(firstName.getText());
			}
		});
		add(firstName);
		
		JLabel nlabel=new JLabel("Educator number");
		nlabel.setBounds(slabel.getX(),slabel.getY()+35,slabel.getWidth(),slabel.getHeight());
		add(nlabel);
		educatorNumber=new RestrictedTextField(RestrictedTextField.RESTRICTION_INT,-1);
		educatorNumber.setBounds(surname.getX(),nlabel.getY(),surname.getWidth(),surname.getHeight());
		educatorNumber.addKeyListener(new Listener(null){
			@Override
			protected void doStuff()
			{
				educator.setemployeeNumber(Integer.parseInt(educatorNumber.getText()));
			}
		});
		add(educatorNumber);
		
		JLabel plabel=new JLabel("courses");
		plabel.setBounds(nlabel.getX(),nlabel.getY()+35,nlabel.getWidth(),nlabel.getHeight());
		add(plabel);
		courseList=new JList();
		courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		courseList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				removeCourse.setEnabled(courseList.getSelectedValues().length>0);
			}
		});
		coursePane=new JScrollPane(courseList);
		coursePane.setBounds(plabel.getX(),plabel.getY()+30,200,100);
		add(coursePane);
		
		addCourse=new JButton("Add");
		addCourse.setBounds(coursePane.getX(),coursePane.getY()+coursePane.getHeight(),coursePane.getWidth(),25);
		addCourse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Course c=selectCourse();
				if (c!=null)
				{
					educator.addCourse(c);
					editVector.add(new Wrapper<Educator>(educator,Edit.edited));
					editVector.add(new Wrapper<Course>(c,Edit.edited));
					courseList.setListData(educator.getCourses());
				}
			}
		});
		add(addCourse);
		
		removeCourse=new JButton("Remove");
		removeCourse.setBounds(addCourse.getX(),addCourse.getY()+30,addCourse.getWidth(),addCourse.getHeight());
		removeCourse.setEnabled(false);
		removeCourse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (confirmDelete("the selected courses"))
				{
					Object[] sel=courseList.getSelectedValues();
					
					for (Object c:sel)
					{
						educator.removeCourse((Course) c);
						editVector.add(new Wrapper<Educator>(educator,Edit.edited));
						editVector.add(new Wrapper<Course>((Course)c,Edit.edited));
					}
					courseList.setListData(educator.getCourses());
				}
			}
		});
		add(removeCourse);
		
		JLabel clabel=new JLabel("Subcourses");
		clabel.setBounds(coursePane.getX()+coursePane.getWidth()+25,plabel.getY(),plabel.getWidth(),plabel.getHeight());
		add(clabel);
		subcourseList=new JList();
		subcourseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		subcourseList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				removeSubcourse.setEnabled(subcourseList.getSelectedValues().length>0);
			}
		});
		subcoursePane=new JScrollPane(subcourseList);
		subcoursePane.setBounds(clabel.getX(),coursePane.getY(),coursePane.getWidth(),coursePane.getHeight());
		add(subcoursePane);
		
		addSubcourse=new JButton("Add");
		addSubcourse.setBounds(subcoursePane.getX(),subcoursePane.getY()+subcoursePane.getHeight(),subcoursePane.getWidth(),25);
		addSubcourse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Subcourse s=selectSubcourse();
				if (s!=null)
				{
					educator.addSubcourse(s);
					editVector.add(new Wrapper<Educator>(educator,Edit.edited));
					editVector.add(new Wrapper<Subcourse>(s,Edit.edited));
					subcourseList.setListData(educator.getSubcourses());
				}
			}
		});
		add(addSubcourse);
		
		removeSubcourse=new JButton("Remove");
		removeSubcourse.setBounds(addSubcourse.getX(),addSubcourse.getY()+30,addSubcourse.getWidth(),addSubcourse.getHeight());
		removeSubcourse.setEnabled(false);
		removeSubcourse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (confirmDelete("the selected subcourses"))
				{
					Object[] sel=subcourseList.getSelectedValues();
					for (Object s:sel)
					{
						//TODO
						educator.removeSubcourse((Subcourse) s);
						editVector.add(new Wrapper<Educator>(educator,Edit.edited));
						editVector.add(new Wrapper<Subcourse>((Subcourse)s,Edit.edited));
					}
					subcourseList.setListData(educator.getSubcourses());
				}
			}
		});
		add(removeSubcourse);
	}
	
	private Subcourse selectSubcourse()
	{
		SubcourseSelector sel=new SubcourseSelector(faculties);
		if (JOptionPane.showConfirmDialog(null,sel,"Select Subcourse",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
		{
			return sel.getSubcourse();
		}
		return null;
	}
	
	private Course selectCourse()
	{
		CourseSelector sel=new CourseSelector(faculties,true);
		if (JOptionPane.showConfirmDialog(null,sel,"Select Course",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
		{
			return sel.getCourse();
		}
		return null;
	}
	
	private boolean confirmDelete(String name)
	{
		return JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+name+"?","delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
	}
	
	
	private abstract class Listener implements KeyListener
	{
		private JList list;
		
		public Listener(JList list)
		{
			super();
			this.list=list;
		}
		
		protected abstract void doStuff();
		
		@Override
		public void keyPressed(KeyEvent e)
		{
		}
		@Override
		public void keyReleased(KeyEvent e)
		{
			if ((!e.isConsumed())||(e.getKeyChar()==KeyEvent.VK_BACK_SPACE)||(e.getKeyChar()==KeyEvent.VK_DELETE))
			{
				doStuff();
				editVector.add(new Wrapper<Educator>(educator,Edit.edited));
				if (list!=null)
				{
					list.repaint();
				}
			}
		}
		@Override
		public void keyTyped(KeyEvent e)
		{
		}
	}
}