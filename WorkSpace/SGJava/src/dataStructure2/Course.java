package dataStructure2;

import java.util.Vector;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Course
{
	private String name;
	private Vector<Subcourse> subcourses;
	
	public Course(String name, Vector<Subcourse> subcourses)
	{
		super();
		this.name = name;
		this.subcourses = subcourses;
	}
	
	public Course()
	{
		
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Vector<Subcourse> getSubcourses()
	{
		return subcourses;
	}

	public void setSubcourses(Vector<Subcourse> subcourses)
	{
		this.subcourses = subcourses;
	}
}
