package applet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;

/**
 * @author Zjef
 * @version 1.0
 */
public class CourseSelector extends JTabbedPane
{
	private Vector<Faculty> faculties;
	private Program zeroProgram;
	
	private ProgramSelector psel;
	private JComboBox courses;
	private JList zList;
	private JScrollPane zPane;
	
	public CourseSelector(Vector<Faculty> faculties,Program zeroProgram)
	{
		super();
		this.zeroProgram=zeroProgram;
		this.faculties=faculties;
		initPanel();
	}
	
	public void setSelection(Faculty f,Program p,Course c)
	{
		psel.setSelection(f,p);
		if (c!=null)
		{
			courses.setSelectedItem(c);
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(600,300);
	}
	
	public Course getCourse()
	{
		return getSelectedIndex()==0?(Course)courses.getSelectedItem():(Course)zList.getSelectedValue();
	}
	
	private void initPanel()
	{
		createNormal();
		createTrash();
	}
	
	private void createNormal()
	{
		JPanel normal=new JPanel(null);
		
		psel=new ProgramSelector(faculties);
		psel.setBounds(0,0,psel.getPreferredSize().width,psel.getPreferredSize().height);
		normal.add(psel);
		psel.addProgramListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Program p=psel.getProgram();
				if (p!=null)
				{
					courses.removeAllItems();
					for (Course i:p.getCourses())
					{
						courses.addItem(i);
					}
				}
			}
		});
		
		JLabel clabel=new JLabel("Course");
		clabel.setBounds(psel.getX()+psel.getWidth(),5,1000,25);
		normal.add(clabel);
		courses=new JComboBox();
		courses.setBounds(clabel.getX(),clabel.getY()+30,150,25);
		normal.add(courses);
		
		addTab("Courses",normal);
	}
	
	private void createTrash()
	{
		JPanel trash=new JPanel(null);
		zList=new JList(zeroProgram.getCourses());
		zPane=new JScrollPane(zList);
		zPane.setBounds(5,5,300,200);
		trash.add(zPane);
		
		addTab("Trash",trash);
	}
}