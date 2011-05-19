package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Educator implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String surName;
	private int employeeNumber;
	private Vector<Course> courses;
	private Vector<Subcourse> subcourses;

	public Educator(String firstName, String surName, int employeeNumber)
	{
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.employeeNumber = employeeNumber;
	}

	public Educator()
	{
		
	}
	
	public int getemployeeNumber()
	{
		return employeeNumber;
	}
	
	public void setemployeeNumber(int newemployeeNumber)
	{
		employeeNumber = newemployeeNumber;
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
	
	public String getFirstName() 
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}
	
	@Override
	public String toString() 
	{
		return getFirstName()+" "+getSurName();
	}

	public int getOwnID()
	{
		return employeeNumber;
	}
	
	public Vector<Course> getCourses()
	{
		return courses;
	}

	public void setCourses(Vector<Course> courses)
	{
		this.courses = (Vector<Course>) courses.clone();
	}

	public void setSubcourses(Vector<Subcourse> subcourses)
	{
		this.subcourses = (Vector<Subcourse>) subcourses.clone();
	}

	public Vector<Subcourse> getSubcourses()
	{
		return subcourses;
	}
	
	public void addSubcourse(Subcourse subcourse)
	{
		if (!subcourses.contains(subcourse))
		{
			this.subcourses.add(subcourse);
		}
	}
	
	public void removeSubcourse(Subcourse subcourse)
	{
		this.subcourses.remove(subcourse);
	}
	
	public void addCourse(Course course)
	{
		if (!courses.contains(course))
		{
			this.courses.add(course);
		}
	}
	
	public void removeCourse(Course course)
	{
		this.courses.remove(course);
	}
}