package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Subcourse;

public class ListManager
{
	Program zeroProgram;
	EditVector editVector;
	private JList fList,pList,cList,sList;
	private JComboBox fBox,pBox,cBox;
	private Vector<Faculty> faculties;
	
	public ListManager(Vector<Faculty> faculties,Program zeroProgram,EditVector editVector,JList fList,JList pList,JList cList,JList sList,JComboBox fBox,JComboBox pBox,JComboBox cBox)
	{
		this.faculties=faculties;
		this.zeroProgram=zeroProgram;
		this.editVector=editVector;
		this.fList=fList;this.pList=pList;this.cList=cList;this.sList=sList;
		this.fBox=fBox;this.pBox=pBox;this.cBox=cBox;
		
		initListeners();
	}
	
	public void fireListeners()
	{
		if (faculties.size()>0)
		{
			fList.setSelectedIndex(0);
		}
	}
	
	public void rename(JList list)
	{
		Object i=list.getSelectedValue();
		editVector.add(new Wrapper(i,Edit.edited));
	}
	
	private void removeProgramFromCourse(Program p,Course c)
	{
		editVector.add(new Wrapper<Program>(p,Edit.edited));
		if (c.getPrograms().size()>1)
		{
			editVector.add(new Wrapper<Course>(c,Edit.edited));
		}
		else
		{
			if (p==zeroProgram)
			{
				deleteCourse(c);
			}
			else
			{
				zeroProgram.addCourse(c);
				editVector.add(new Wrapper<Program>(zeroProgram,Edit.edited));
				editVector.add(new Wrapper<Course>(c,Edit.edited));
			}
		}
		p.removeCourse(c);
	}
	
	private void deleteCourse(Course c)
	{
		editVector.add(new Wrapper<Course>(c,Edit.deleted));
		for (Subcourse s:c.getSubcourses())
		{
			editVector.add(new Wrapper<Subcourse>(s,Edit.deleted));
		}
	}
	
	private void deleteProgram(Faculty f,Program p)
	{
		for (Course c:p.getCourses())
		{
			removeProgramFromCourse(p,c);
		}
		f.removeProgram(p);
		editVector.add(new Wrapper<Program>(p,Edit.deleted));
		editVector.add(new Wrapper<Faculty>(f,Edit.edited));
	}
	
	public void removeItems(Object[] items)
	{
		if (items==null || items.length==0)
		{
			return;
		}
		for (Object item:items)
		{
			if (item instanceof Faculty)
			{
				Faculty f=(Faculty)item;
				faculties.remove(f);
				fList.setListData(faculties);
				fBox.removeItem(faculties);
				editVector.add(new Wrapper(item,Edit.deleted));
				//Do something with programs
			}
			else if (item instanceof Program)
			{
				Faculty f=(Faculty) fList.getSelectedValue();
				deleteProgram(f,(Program)item);
				
				pList.setListData(f.getPrograms());
				pBox.removeItem(item);
			}
			else if (item instanceof Course)
			{
				if (((Course) item).getPrograms().contains(zeroProgram))
				{
					removeProgramFromCourse(zeroProgram,(Course)item);
				}
				else
				{
					Program p=(Program) pList.getSelectedValue();
					removeProgramFromCourse(p,(Course)item);
					cList.setListData(p.getCourses());
					cBox.removeItem(item);
				}
			}
			else if (item instanceof Subcourse)
			{
				Course c=(Course) cList.getSelectedValue();
				c.removeSubcourse((Subcourse) item);
				
				sList.setListData(c.getSubcourses());
				
				editVector.add(new Wrapper<Course>(c,Edit.edited));
				editVector.add(new Wrapper(item,Edit.deleted));
			}
		}
	}
	
	public void addItem(Object item)
	{
		if (item==null)
		{
			return;
		}
		if (item instanceof Faculty)
		{
			faculties.add((Faculty) item);
			fList.setListData(faculties);
			fBox.addItem(item);
			fBox.setSelectedItem(item);
		}
		else if (item instanceof Program)
		{
			Faculty f=(Faculty) fList.getSelectedValue();
			f.addProgram((Program) item);
			
			pList.setListData(f.getPrograms());
			pBox.addItem(item);
			pBox.setSelectedItem(item);
			
			editVector.add(new Wrapper<Faculty>(f,Edit.edited));
		}
		else if (item instanceof Course)
		{
			Program p=(Program) pList.getSelectedValue();
			p.addCourse((Course) item);
			
			cList.setListData(p.getCourses());
			cBox.addItem(item);
			cBox.setSelectedItem(item);
			
			editVector.add(new Wrapper<Program>(p,Edit.edited));
			editVector.add(new Wrapper<Course>((Course) item,Edit.edited));
		}
		else if (item instanceof Subcourse)
		{
			Course c=(Course) cList.getSelectedValue();
			c.addSubcourse((Subcourse) item);
			
			sList.setListData(c.getSubcourses());
			sList.setSelectedValue(item,true);
			
			editVector.add(new Wrapper<Course>(c,Edit.edited));
		}
		editVector.add(new Wrapper(item,Edit.added));
	}
	
	private void initListeners()
	{
		fList.addListSelectionListener(new ListToCombo(fBox));
		pList.addListSelectionListener(new ListToCombo(pBox));
		cList.addListSelectionListener(new ListToCombo(cBox));
		
		fBox.addActionListener(new ComboToList(fList));
		pBox.addActionListener(new ComboToList(pList));
		cBox.addActionListener(new ComboToList(cList));
	
		fBox.addActionListener(new HighToLow(pList,pBox,"getPrograms"));
		pBox.addActionListener(new HighToLow(cList,cBox,"getCourses"));
		cBox.addActionListener(new HighToLow(sList,null,"getSubcourses"));
	}
	
	private class HighToLow implements ActionListener
	{
		private JList list;
		private JComboBox box;
		private String getter;
		
		public HighToLow(JList list,JComboBox box,String getter)
		{
			super();
			this.list=list;
			this.box=box;
			this.getter=getter;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				JComboBox parent=(JComboBox) e.getSource();
				Object sel=parent.getSelectedItem();
				Vector items;
				if (sel==null)
				{
					items=new Vector();
				}
				else
				{
					items=(Vector) sel.getClass().getDeclaredMethod(getter).invoke(sel);
				}
				
				if (box!=null)
				{
					box.setModel(new DefaultComboBoxModel(items));
				}
				list.setListData(items);
				if (items.size()>0)
				{
					list.setSelectedValue(items.get(0),false);
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	private class ListToCombo implements ListSelectionListener
	{
		private JComboBox box;
		
		public ListToCombo(JComboBox box)
		{
			super();
			this.box=box;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			JList l=(JList) e.getSource();
			box.setSelectedItem(l.getSelectedValue());
		}
	}
	
	private class ComboToList implements ActionListener
	{
		private JList list;
		
		public ComboToList(JList list)
		{
			super();
			this.list=list;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox box=(JComboBox) e.getSource();
			list.setSelectedValue(box.getSelectedItem(),true);
		}
	}
	
	public Faculty getFaculty()
	{
		return (Faculty) fList.getSelectedValue();
	}
	
	public Program getProgram()
	{
		return (Program) pList.getSelectedValue();
	}
	
	public Course getCourse()
	{
		return (Course) cList.getSelectedValue();
	}
	
	public Subcourse getSubcourse()
	{
		return (Subcourse) sList.getSelectedValue();
	}
	
	public Vector<Faculty> getFaculties()
	{
		return (Vector<Faculty>) faculties.clone();
	}
	
	public EditVector getEditVector()
	{
		return editVector;
	}
	
	public Program getZeProgram()
	{
		return zeroProgram;
	}
}