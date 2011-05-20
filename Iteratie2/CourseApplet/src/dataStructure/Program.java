package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version 2.0
 */
public class Program implements Serializable,Cloneable
{
	private static final long serialVersionUID = 1L;
	
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
		courses=new Vector<Course>();
	}
	
	public Program(String name)
	{
		this.name=name;
		courses=new Vector<Course>();
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public Vector<Course> getCourses()
	{
		return (Vector<Course>) courses.clone();
	}

	public void setCourses(Vector<Course> courses)
	{
		this.courses = (Vector<Course>) courses.clone();
	}
	
	public int getlevel()
	{
		return level;
	}
	
	public void setlevel(int newlevel)
	{
		level = newlevel;
	}
	
	private ID id;
	
	public void setID(ID id)
	{
		this.id=id;
	}
	
	public ID getID()
	{
		return id;
	}
	
	@Override
	public String toString() 
	{
		return getName();
	}

	public void addCourse(Course item)
	{
		courses.add(item);
		item.addProgram(this);
	}

	public void removeCourse(Course item)
	{
		courses.remove(item);
		item.removeProgram(this);
	}
	
	@Override
	public Object clone()
	{
		Program p=new Program(level,name,(Vector<Course>) courses.clone());
		p.id=null;
		return p;
	}
}