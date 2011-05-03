package dataStructure;

import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

import java.util.Vector;

import database.*;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */
public class Student implements Databasable,HTMLTablable
{
	private int studentNumber;
	private String firstName;
	private String surName;
	private Vector<Program> programs;
	private Vector<Course> courses;
	
	
	public Student(int studentNumber, String firstName, String surName) 
	{
		super();
		this.studentNumber = studentNumber;
		this.firstName = firstName;
		this.surName = surName;
		programs = new Vector<Program>();
		courses = new Vector<Course>();
	}

	public Student()
	{
	
	}
	
	@InDatabase
	@TableInput(order=1,text="##Firstname_Student_Table##")
	public String getFirstName()
	{
		return firstName;
	}

	@OutDatabase
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@InDatabase
	@TableInput(order=2,text="##Surname_Student_Table##")
	public String getSurName() {
		return surName;
	}

	@OutDatabase
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * .clone wordt gebruikt om aan te geven dat men met een kopie werkt, dus gebruik {@link #setPrograms}
	 * om wijzigingen door te voeren
	 * 
	 */
	
	@InDatabase
	public Vector<Program> getPrograms()
	{
		return (Vector<Program>) programs.clone(); 
	}
	
	public void addProgram(Program program)
	{
		programs.add(program);
	}
	
	public void removeProgram(Program program)
	{
		programs.remove(program);
	}
	
	@OutDatabase(Program.class)
	public void setPrograms(Vector<Program> programs)
	{
		this.programs = programs;
	}
	
	@InDatabase
	@TableInput(order=3,text="##Number_Student_Table##")
	public int getstudentNumber()
	{
		return studentNumber;
	}
	
	@OutDatabase
	public void setstudentNumber(int newstudentNumber)
	{
		studentNumber = newstudentNumber;
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
	
	public void addCourse(Course course)
	{
		courses.add(course);
	}
	
	public void removeCourse(Course course)
	{
		courses.remove(course);
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
}

