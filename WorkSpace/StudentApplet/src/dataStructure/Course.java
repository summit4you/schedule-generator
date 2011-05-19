package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version 2.0
 */
public class Course implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Educator responsible;
	private Vector<Program> programs;
	
	public Course()
	{
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Educator getResponsible()
	{
		return responsible;
	}

	public void setResponsible(Educator responsible)
	{
		this.responsible = responsible;
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
}