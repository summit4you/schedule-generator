package applet;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dataStructure.Course;

public class CourseTab extends BoxListTab
{
	private JScrollPane cPane;
	private JList cList;
	private ButtonPanel<Course> buttons;
	private CoursePanel coursePanel;
	private ListManager manager;
	
	public CourseTab(JScrollPane cPane,JList cList,ListManager manager)
	{
		super();
		this.cPane=cPane;
		this.cList=cList;
		this.manager=manager;
		initPanel();
	}
	
	private void initPanel()
	{
		cList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				Course c=(Course) cList.getSelectedValue();
				coursePanel.setVisible(cList.getSelectedValue()!=null);
				if (c!=null)
				{
					coursePanel.setCourse(c);
				}				
			}
		});
		add(cPane);
		
		buttons=new ButtonPanel<Course>(cList,manager,cPane.getBounds(),true,true){
			@Override
			public Course createNewObject(String name)
			{
				return new Course(name); 
			}		
			@Override
			protected Course createCopy(Course object,String name) 
			{
				Course c=(Course) object.clone();
				c.setName(name);
				return c;
			}
			@Override
			protected void renameItem(Course c,String name) 
			{
				c.setName(name);
			};
			@Override
			protected Course loadFromList() 
			{
				CourseSelector sel=new CourseSelector(manager.getFaculties(),manager.getZeProgram());
				sel.setSelection(manager.getFaculty(),manager.getProgram(),null);
				if (JOptionPane.showConfirmDialog(null,sel,"Select a course",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
				{
					return sel.getCourse();
				}
				return null;
			};
		};
		add(buttons);
		
		coursePanel=new CoursePanel(manager);
		coursePanel.setBounds(cPane.getX()+cPane.getWidth()+250,cPane.getY(),1000,1000);
		coursePanel.setVisible(false);
		add(coursePanel);
	}
}