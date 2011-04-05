package dataStructure;

import java.util.Vector;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Program implements Databasable
{
	private String name;
	private int level;
	private Vector<Course> courses;
	
	public Program(int newlevel, String newname,Vector<Course> courses)
	{
		level = newlevel;
		name = newname;
		this.courses = courses;
	}
	
	@InDatabase
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
	
	public void setID(ID id)
	{
		this.id=id;
	}
	
	public ID getId()
	{
		return id;
	}
	
}
