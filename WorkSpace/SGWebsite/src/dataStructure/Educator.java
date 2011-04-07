package dataStructure;

import htmlInterfaces.HTMLTablable;

import java.util.Vector;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Educator implements Databasable,HTMLTablable
{
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
		this.courses = new Vector<Course>();
		this.subcourses = new Vector<Subcourse>();

	}

	public Educator()
	{
		
	}
	
	
	
	@InDatabase
	@TableInput(order=3,text="#Number_Educator_Table#")
	public int getemployeeNumber()
	{
		return employeeNumber;
	}
	
	@OutDatabase
	public void setemployeeNumber(int newemployeeNumber)
	{
		employeeNumber = newemployeeNumber;
	}
	
	private ID id;
	
	
	@Override
	public void setID(ID id)
	{
		this.id=id;
	}
	
	
	@Override
	public ID getId()
	{
		return id;
	}
	
	@InDatabase
	@TableInput(order=1,text="#Firstname_Educator_Table#")
	public String getFirstName() 
	{
		return firstName;
	}

	@OutDatabase
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	@InDatabase
	@TableInput(order=2,text="#Surname_Educator_Table#")
	public String getSurName()
	{
		return surName;
	}

	@OutDatabase
	public void setSurName(String surName)
	{
		this.surName = surName;
	}
	
	@InDatabase
	public Vector<Course> getCourses()
	{
		return courses;
	}

	@OutDatabase(Course.class)
	public void setCourses(Vector<Course> courses)
	{
		this.courses = (Vector<Course>) courses.clone();
	}

	@OutDatabase(Subcourse.class)
	public void setSubcourses(Vector<Subcourse> subcourses)
	{
		this.subcourses = (Vector<Subcourse>) subcourses.clone();
	}

	@InDatabase
	public Vector<Subcourse> getSubcourses()
	{
		return subcourses;
	}
	
	@Override
	public String toString() 
	{
		return getFirstName()+" "+getSurName()+"("+getemployeeNumber()+")";
	}
}
