package dataStructure;

import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

import java.util.Vector;

import calendar.IcsCalendar;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Program implements Databasable,HTMLTablable
{
	private String name;
	private int level;
	private Vector<Course> courses;
	
	public Program(int newlevel, String newname,Vector<Course> courses)
	{
		level = newlevel;
		name = newname;
		this.courses = (Vector<Course>) courses.clone();
	}
	
	public Program()
	{
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
	}
	
	@InDatabase
	@TableInput(order=1,text="##Name_Program_Table##")
	public String getName() 
	{
		return name;
	}

	@OutDatabase
	public void setName(String name) 
	{
		this.name = name;
	}
	
	@InDatabase
	public Vector<Course> getCourses()
	{
		return (Vector<Course>) courses.clone();
	}

	@OutDatabase(Course.class)
	public void setCourses(Vector<Course> courses)
	{
		this.courses = (Vector<Course>) courses.clone();
	}
	
	@InDatabase
	@TableInput(order=2,text="##Level_Program_Table##")
	public int getlevel()
	{
		return level;
	}
	
	@OutDatabase
	public void setlevel(int newlevel)
	{
		level = newlevel;
	}
	
	private ID id;
	
	@Override
	public void setID(ID id)
	{
		this.id=id;
	}
	
	@Override
	public ID getID()
	{
		return id;
	}
	
	@Override
	public String toString() 
	{
		return getName();
	}

	public IcsCalendar getCalender()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
