package applet;

import javax.swing.JList;
import javax.swing.JScrollPane;
import dataStructure.Faculty;

public class FacultyTab extends BoxListTab
{
	private JList facultyList;
	private JScrollPane facultyPane;
	private ButtonPanel<Faculty> buttons;
	private ListManager manager;
	
	public FacultyTab(JScrollPane facultyPane,JList facultyList,ListManager manager)
	{
		super();
		this.facultyList=facultyList;
		this.facultyPane=facultyPane;
		this.manager=manager;
		initPanel();
	}
	
	private void initPanel()
	{
		add(facultyPane);
		buttons=new ButtonPanel<Faculty>(facultyList,manager,facultyPane.getBounds(),false,false){
			@Override
			public Object createNewObject(String name)
			{
				return new Faculty(name);
			}
			@Override
			protected void renameItem(Faculty item, String name)
			{
				item.setName(name);
			}
		};
		add(buttons);
	}
}