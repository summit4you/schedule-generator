package dataStructure;

import java.util.Vector;

import database.*;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */
public class Student implements Databasable
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
	}

	public Student()
	{
	
	}
	
	@InDatabase
	public String getFirstName()
	{
		return firstName;
	}

	@OutDatabase
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@InDatabase
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
	
	public void setID(ID id)
	{
		this.id=id;
	}
	
	public ID getId()
	{
		return id;
	}
}

