package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataStructure.Course;
import dataStructure.Educator;

public class CoursePanel extends JPanel
{
	private Course course;
	
	private JButton setEducator;
	private JTextField name;
	private ListManager m;
	
	public CoursePanel(ListManager m)
	{
		super(null);
		this.m=m;
		initPanel();
	}
	
	public void setCourse(Course c)
	{
		this.course=c;
		name.setText(c.getResponsible()==null?"":c.getResponsible().toString());
	}
	
	private void initPanel()
	{
		setEducator=new JButton("setEducator");
		setEducator.setBounds(5,5,150,25);
		setEducator.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				EducatorSelector sel=new EducatorSelector(m.getFaculties());
				sel.setSelection(m.getFaculty(),null);
				if (JOptionPane.showConfirmDialog(null,sel,"Select Educator",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
				{
					course.setResponsible(sel.getEducator());
					
					m.getEditVector().add(new Wrapper<Educator>(course.getResponsible(),Edit.edited));
					m.getEditVector().add(new Wrapper<Course>(course,Edit.edited));
					m.getEditVector().add(new Wrapper<Educator>(course.getResponsible(),Edit.edited));
					
					name.setText(course.getResponsible().toString());
				}
			}
		});
		add(setEducator);
		
		name=new JTextField();
		name.setEditable(false);
		name.setBounds(setEducator.getX(),setEducator.getY()+30,200,25);
		add(name);
	}
}