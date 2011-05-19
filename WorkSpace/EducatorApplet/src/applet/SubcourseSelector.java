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
import dataStructure.Subcourse;

public class SubcourseSelector extends JPanel
{
	private Vector<Faculty> faculties;
	
	private JComboBox subcourseBox;
	private CourseSelector courseSelector;
	
	public SubcourseSelector(Vector<Faculty> faculties)
	{
		super(null);
		this.faculties=faculties;
		initPanel();
	}
	
	public Subcourse getSubcourse()
	{
		return (Subcourse)subcourseBox.getSelectedItem();
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(800,100);
	}
	
	private void initPanel()
	{
		courseSelector=new CourseSelector(faculties,false);
		courseSelector.setBounds(0,0,1000,100);
		courseSelector.addCourseListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Course c=courseSelector.getCourse();
				if (c!=null)
				{
					subcourseBox.removeAllItems();
					for (Subcourse i:c.getSubcourses())
					{
						subcourseBox.addItem(i);
					}
				}
			}
		});
		add(courseSelector);
		
		JLabel slabel=new JLabel("Subcourse");
		slabel.setBounds(courseSelector.getPreferredSize().width,5,1000,25);
		add(slabel);
		subcourseBox=new JComboBox();
		subcourseBox.setBounds(slabel.getX(),slabel.getY()+30,150,25);
		add(subcourseBox);
	}
}