package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */
public class Student implements Serializable
{
	private static final long serialVersionUID = 1L;
	
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
	
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Vector<Program> getPrograms()
	{
		return (Vector<Program>) programs.clone(); 
	}
	
	public void addProgram(Program program)
	{
		if (program!=null && !programs.contains(program))
		{
			programs.add(program);
		}
	}
	
	public void removeProgram(Program program)
	{
		programs.remove(program);
	}
	
	public void setPrograms(Vector<Program> programs)
	{
		this.programs = programs;
	}
	
	public int getstudentNumber()
	{
		return studentNumber;
	}
	
	public void setstudentNumber(int newstudentNumber)
	{
		studentNumber = newstudentNumber;
	}
	
	public Vector<Course> getCourses()
	{
		return courses;
	}

	public void setCourses(Vector<Course> courses)
	{
		this.courses = courses;
	}
	
	public void addCourse(Course course)
	{
		if (course!=null && !courses.contains(course))
		{
			courses.add(course);
		}
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
	
	public ID getID()
	{
		return id;
	}

	public int getOwnID()
	{
		return studentNumber;
	}
	
	@Override
	public String toString()
	{
		return surName+" "+firstName;
	}
}