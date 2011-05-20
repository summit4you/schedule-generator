package applet;

import javax.swing.JList;
import javax.swing.JScrollPane;

import dataStructure.Program;

public class ProgramTab extends BoxListTab
{
	private JList programList;
	private JScrollPane programScroll;
	private ButtonPanel<Program> buttons;
	private ListManager manager;
	
	public ProgramTab(JScrollPane programScroll,JList programList,ListManager manager)
	{
		super();
		this.programScroll=programScroll;
		this.programList=programList;
		this.manager=manager;
		initPanel();
	}
	
	private void initPanel()
	{		
		add(programScroll);
		
		buttons=new ButtonPanel<Program>(programList,manager,programScroll.getBounds(),true,false){
			@Override
			public Program createNewObject(String name)
			{
				return new Program(name);
			}
			@Override
			protected Program createCopy(Program object,String name) 
			{
				Program p=(Program) object.clone();
				p.setName(name);
				return p;
			}
			@Override
			protected void renameItem(Program item, String name)
			{
				item.setName(name);
			};
		};
		add(buttons);
	}
}