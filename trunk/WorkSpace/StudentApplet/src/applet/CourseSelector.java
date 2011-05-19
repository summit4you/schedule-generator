package applet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;

/**
 * @author Zjef
 * @version 1.0
 */
public class CourseSelector extends JPanel
{
	private Vector<Faculty> faculties;
	private ProgramSelector psel;
	private JComboBox courses;
	
	public CourseSelector(Vector<Faculty> faculties)
	{
		super(null);
		this.faculties=faculties;
		initPanel();
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(600,100);
	}
	
	public Course getCourse()
	{
		return (Course) courses.getSelectedItem();
	}
	
	private void initPanel()
	{
		psel=new ProgramSelector(faculties);
		psel.setBounds(0,0,psel.getPreferredSize().width,psel.getPreferredSize().height);
		add(psel);
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
		add(clabel);
		courses=new JComboBox();
		courses.setBounds(clabel.getX(),clabel.getY()+30,150,25);
		add(courses);
	}
}