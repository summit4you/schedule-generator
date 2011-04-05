package dataStructure;

import java.util.Vector;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Educator implements Databasable
{
	private String firstName;
	private String surName;
	private int employeeNumber;
	private Vector<Course> courses;
	

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
	
	
	
	@InDatabase
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
	
	
	public void setID(ID id)
	{
		this.id=id;
	}
	
	
	public ID getId()
	{
		return id;
	}
	
	@InDatabase
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
		this.courses = courses;
	}
}
