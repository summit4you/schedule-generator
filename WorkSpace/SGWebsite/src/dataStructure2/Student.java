package dataStructure2;

import java.util.Vector;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */
public class Student extends Person
{
	private int studentNumber;
	private Vector<Program> programs;
	private Vector<Course> courses;
	
	public Student(int newstudentNumber,String newfirstName, String newsurName)
	{
		super(newfirstName,newsurName);
		setstudentNumber(newstudentNumber);
		programs = new Vector<Program>();
		courses = new Vector<Course>();
	}
	
	public Student()
	{
		super();
	}
	
	/**
	 * .clone wordt gebruikt om aan te geven dat men met een kopie werkt, dus gebruik {@link #setPrograms}
	 * om wijzigingen door te voeren
	 * 
	 */
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
		courses.add(course);
	}
	
	public void removeCourse(Course course)
	{
		courses.remove(course);
	}
}

