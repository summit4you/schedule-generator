package dataStructure;

import htmlInterfaces.HTMLTablable;

import java.util.Vector;

import database.*;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Course implements Databasable,HTMLTablable
{
	private String name;
	private Educator responsible;
	private Vector<Subcourse> subcourses;
	private Vector<Program> programs;
	
	public Course(String name, Vector<Subcourse> subcourses,Educator responsible)
	{
		super();
		this.name = name;
		this.subcourses = subcourses;
		this.responsible = responsible;
	}
	
	public Course()
	{
		subcourses = new Vector<Subcourse>();
	}
	
	@InDatabase
	@TableInput(order=1,text="#Name_Course_Table#")
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
	public Vector<Subcourse> getSubcourses()
	{
		return (Vector<Subcourse>) subcourses.clone();
	}

	@OutDatabase(Subcourse.class)
	public void setSubcourses(Vector<Subcourse> subcourses)
	{
		this.subcourses = (Vector<Subcourse>) subcourses.clone();
	}
	
	@InDatabase
	@TableInput(order=2,text="#Responsible_Course_Table#")
	public Educator getResponsible()
	{
		return responsible;
	}

	@OutDatabase
	public void setResponsible(Educator responsible)
	{
		this.responsible = responsible;
	}
	
	public void addSubcourse(Subcourse subcourse)
	{
		subcourses.add(subcourse);
	}
	
	public void removeSubcourse(Subcourse subcourse)
	{
		subcourses.remove(subcourse);
	}
	
	@InDatabase
	@TableInput(order=3,text="#Program_Course_Table#")
	public Vector<Program> getPrograms()
	{
		return (Vector<Program>) programs.clone();
	}

	@OutDatabase(Program.class)
	public void setPrograms(Vector<Program> programs)
	{
		this.programs = (Vector<Program>) programs.clone();
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
}
