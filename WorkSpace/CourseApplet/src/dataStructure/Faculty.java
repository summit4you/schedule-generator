package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Faculty implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Vector<Program> programs;
	private Vector<Educator> educators;
	
	public Faculty()
	{
		programs=new Vector<Program>();
		educators=new Vector<Educator>();
	}
	
	public Faculty(String name)
	{
		this();
		this.name=name;
	}
	
	public void addEducator(Educator e)
	{
		educators.add(e);
	}
	
	public void addProgram(Program p)
	{
		programs.add(p);
	}

	public void removeEducator(Educator e)
	{
		educators.remove(e);
	}
	
	public void setEducators(Vector<Educator> educators)
	{
		this.educators = educators;
	}
	
	public Vector<Educator> getEducators()
	{
		return educators;
	}
	
	public Vector<Program> getPrograms()
	{
		return (Vector<Program>) programs.clone();
	}

	public void setPrograms(Vector<Program> programs)
	{
		this.programs = (Vector<Program>) programs.clone();
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public void removeProgram(Program item)
	{
		programs.remove(item);		
	}
}