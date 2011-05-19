package applet;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataStructure.Subcourse;

public class SubcourseTab extends BoxListTab
{
	private JScrollPane sPane;
	private JList sList;
	private ButtonPanel<Subcourse> buttons;
	private SubcoursePanel subcoursePanel;
	private ListManager manager;
	
	public SubcourseTab(JScrollPane sPane,JList sList,ListManager manager)
	{
		super();
		this.sPane=sPane;
		this.sList=sList;
		this.manager=manager;
		initPanel();
	}

	private void initPanel()
	{
		sList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				Subcourse s=(Subcourse) sList.getSelectedValue();
				subcoursePanel.setVisible(s!=null);
				if (s!=null)
				{
					subcoursePanel.setSubcourse(s);
				}
			}
		});
		add(sPane);
		
		buttons=new ButtonPanel<Subcourse>(sList,manager,sPane.getBounds(),false,false){
			@Override
			public Object createNewObject(String name)
			{
				return new Subcourse(name);
			}
			@Override
			protected void renameItem(Subcourse item, String name)
			{
				item.setName(name);
			}
		};
		add(buttons);
		
		subcoursePanel=new SubcoursePanel(manager);
		subcoursePanel.setBounds(sPane.getX()+sPane.getWidth()+250,sPane.getY(),1000,1000);
		subcoursePanel.setVisible(false);
		add(subcoursePanel);
	}
}