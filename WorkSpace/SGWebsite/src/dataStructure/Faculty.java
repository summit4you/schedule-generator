package dataStructure;

import java.util.Vector;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Faculty implements Databasable
{
	private String name;
	private Vector<Program> programs;
	
	
	public Faculty(String newname)
	{
		name = newname;
	}
	
	public Faculty()
	{

	}
	
	@InDatabase
	public Vector<Program> getPrograms()
	{
		return (Vector<Program>) programs.clone();
	}

	@OutDatabase(Program.class)
	public void setPrograms(Vector<Program> programs)
	{
		this.programs = (Vector<Program>) programs.clone();
	}
	
	
	@InDatabase
	public String getName()
	{
		return name;
	}

	@OutDatabase
	public void setName(String name)
	{
		this.name = name;
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
