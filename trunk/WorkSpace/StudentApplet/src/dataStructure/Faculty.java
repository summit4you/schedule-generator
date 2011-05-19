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
	
	public Faculty()
	{
		
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
}