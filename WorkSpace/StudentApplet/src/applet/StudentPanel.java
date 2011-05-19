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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import swingExtensions.RestrictedTextField;
import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Student;

public class StudentPanel extends JPanel
{
	private EditVector editVector;
	private Student student;
	private Program program;
	private Vector<Faculty> faculties;
	
	private JTextField surname;
	private JTextField firstName;
	private JList programList;
	private JList courseList;
	private JScrollPane programPane;
	private JScrollPane coursePane;
	private RestrictedTextField studentNumber;
	private JButton addProgram;
	private JButton addCourse;
	private JButton removeProgram;
	private JButton removeCourse;
	
	public StudentPanel(EditVector editVector,Vector<Faculty> faculties)
	{
		super(null);
		this.faculties=faculties;
		this.editVector=editVector;
		initPanel();
	}
	
	public void setStudent(Student student,Program program)
	{
		this.student=student;
		this.program=program;
		surname.setText(student.getSurName());
		firstName.setText(student.getFirstName());
		studentNumber.setText(Integer.toString(student.getstudentNumber()));
		programList.setListData(student.getPrograms());
		courseList.setListData(student.getCourses());
	}
	
	private void initPanel()
	{
		JLabel slabel=new JLabel("surname");
		slabel.setBounds(5,5,1000,25);
		add(slabel);
		surname=new JTextField();
		surname.setBounds(100,slabel.getY(),140,25);
		surname.addKeyListener(new Listener(){

			@Override
			protected void doStuff()
			{
				student.setSurName(surname.getText());
			}
		});
		add(surname);
		
		JLabel flabel=new JLabel("first name");
		flabel.setBounds(surname.getX()+surname.getWidth()+5,surname.getY(),slabel.getWidth(),slabel.getHeight());
		add(flabel);
		firstName=new JTextField();
		firstName.setBounds(flabel.getX()+100,flabel.getY(),surname.getWidth(),surname.getHeight());
		firstName.addKeyListener(new Listener(){
			@Override
			protected void doStuff()
			{
				student.setFirstName(firstName.getText());
			}
		});
		add(firstName);
		
		JLabel nlabel=new JLabel("Student number");
		nlabel.setBounds(slabel.getX(),slabel.getY()+35,slabel.getWidth(),slabel.getHeight());
		add(nlabel);
		studentNumber=new RestrictedTextField(RestrictedTextField.RESTRICTION_INT,-1);
		studentNumber.setBounds(surname.getX(),nlabel.getY(),surname.getWidth(),surname.getHeight());
		studentNumber.addKeyListener(new Listener(){
			@Override
			protected void doStuff()
			{
				student.setstudentNumber(Integer.parseInt(studentNumber.getText()));
			}
		});
		add(studentNumber);
		
		JLabel plabel=new JLabel("programs");
		plabel.setBounds(nlabel.getX(),nlabel.getY()+35,nlabel.getWidth(),nlabel.getHeight());
		add(plabel);
		programList=new JList();
		programList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		programList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				removeProgram.setEnabled(programList.getSelectedValues().length>0);
			}
		});
		programPane=new JScrollPane(programList);
		programPane.setBounds(plabel.getX(),plabel.getY()+30,150,100);
		add(programPane);
		
		addProgram=new JButton("Add");
		addProgram.setBounds(programPane.getX(),programPane.getY()+programPane.getHeight(),programPane.getWidth(),25);
		addProgram.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Program p=selectProgram();
				if (p!=null)
				{
					student.addProgram(p);
					editVector.add(new Wrapper<Student>(student,Edit.edited));
					programList.setListData(student.getPrograms());
				}
			}
		});
		add(addProgram);
		
		removeProgram=new JButton("Remove");
		removeProgram.setBounds(addProgram.getX(),addProgram.getY()+30,addProgram.getWidth(),addProgram.getHeight());
		removeProgram.setEnabled(false);
		removeProgram.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (confirmDelete("the selected programs"))
				{
					Object[] sel=programList.getSelectedValues();
					
					for (Object p:sel)
					{
						if (program.equals(p))
						{
							JOptionPane.showMessageDialog(null,"Cannot delete active program");
						}
						else
						{
							student.removeProgram((Program) p);
							editVector.add(new Wrapper<Student>(student,Edit.edited));
						}
					}
					programList.setListData(student.getPrograms());
				}
			}
		});
		add(removeProgram);
		
		JLabel clabel=new JLabel("Courses");
		clabel.setBounds(programPane.getX()+programPane.getWidth()+25,plabel.getY(),plabel.getWidth(),plabel.getHeight());
		add(clabel);
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
		coursePane.setBounds(clabel.getX(),programPane.getY(),programPane.getWidth()*2,programPane.getHeight());
		add(coursePane);
		
		addCourse=new JButton("Add");
		addCourse.setBounds(coursePane.getX(),coursePane.getY()+coursePane.getHeight(),coursePane.getWidth(),25);
		addCourse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Course s=selectCourse();
				if (s!=null)
				{
					student.addCourse(s);
					editVector.add(new Wrapper<Student>(student,Edit.edited));
					courseList.setListData(student.getCourses());
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
			public void actionPerformed(ActionEvent e)
			{
				if (confirmDelete("the selected courses"))
				{
					Object[] sel=courseList.getSelectedValues();
					for (Object c:sel)
					{
						student.removeCourse((Course) c);
						editVector.add(new Wrapper<Student>(student,Edit.edited));
					}
					courseList.setListData(student.getCourses());
				}
			}
		});
		add(removeCourse);
	}
	
	private Program selectProgram()
	{
		ProgramSelector sel=new ProgramSelector(faculties);
		if (JOptionPane.showConfirmDialog(null,sel,"Select Program",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
		{
			return sel.getProgram();
		}
		return null;
	}
	
	private Course selectCourse()
	{
		CourseSelector sel=new CourseSelector(faculties);
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
		public Listener()
		{
			super();
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
				editVector.add(new Wrapper<Student>(student,Edit.edited));
			}
		}
		@Override
		public void keyTyped(KeyEvent e)
		{
		}
	}
}