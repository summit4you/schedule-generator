package dataStructure;

import htmlInterfaces.HTMLTablable;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Educator implements Databasable,HTMLTablable,DatabasableWithOwnID,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String surName;
	private int employeeNumber;
	private Vector<Course> courses;
	private Vector<Date> unavailableDates;

	public Educator(String firstName, String surName, int employeeNumber)
	{
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.employeeNumber = employeeNumber;
		this.courses = new Vector<Course>();
	}

	public Educator()
	{
		
	}
	
	
	
	@InDatabase
	@TableInput(order=3,text="##Number_Educator_Table##")
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
	public ID getID()
	{
		return id;
	}
	
	@InDatabase
	@TableInput(order=1,text="##Firstname_Educator_Table##")
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
	@TableInput(order=2,text="##Surname_Educator_Table##")
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

	@Override
	public String toString() 
	{
		return getFirstName()+" "+getSurName();
	}

	@Override
	public int getOwnID()
	{
		return employeeNumber;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
	}

	@OutDatabase(Date.class)
	public void setUnavailableDates(Vector<Date> unavailableDates) 
	{
		this.unavailableDates = (Vector<Date>) unavailableDates.clone();
	}

	@InDatabase
	public Vector<Date> getUnavailableDates() 
	{
		return (Vector<Date>) unavailableDates.clone();
	}
	
	public void addUnavailableDate(Date date)
	{
		unavailableDates.add(date);
	}
	
	public void removeUnavailableDate(Date date)
	{
		unavailableDates.remove(date);
	}
}
