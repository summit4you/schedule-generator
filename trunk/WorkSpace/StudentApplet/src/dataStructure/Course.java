package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * @author matthiascaenepeel
 * @version 2.0
 */
public class Course implements Serializable,Cloneable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Educator responsible;
	private Vector<Subcourse> subcourses;
	private Vector<Program> programs;
	
	public Course(String name)
	{
		this.name=name;
		this.subcourses=new Vector<Subcourse>();
		this.programs=new Vector<Program>();
	}
	
	public Course()
	{
		subcourses = new Vector<Subcourse>();
		programs=new Vector<Program>();
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
		return (Vector<Subcourse>) subcourses.clone();
	}

	public void setSubcourses(Vector<Subcourse> subcourses)
	{
		this.subcourses = (Vector<Subcourse>) subcourses.clone();
	}
	
	public Educator getResponsible()
	{
		return responsible;
	}
	
	public void addSubcourse(Subcourse subcourse)
	{
		if (!subcourses.contains(subcourse))
		{
			subcourses.add(subcourse);
		}
	}
	
	public void removeSubcourse(Subcourse subcourse)
	{
		subcourses.remove(subcourse);
	}
	
	public Vector<Program> getPrograms()
	{
		return (Vector<Program>) programs.clone();
	}

	public void setPrograms(Vector<Program> programs)
	{
		this.programs = (Vector<Program>) programs.clone();
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
	
	@Override
	public String toString()
	{
		return getName();
	}

	public void removeProgram(Program p)
	{
		programs.remove(p);
	}

	public void addProgram(Program p)
	{
		if (!programs.contains(p))
		{
			programs.add(p);
		}
	}
}