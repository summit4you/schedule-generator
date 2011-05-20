package dataStructure;

import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

import java.io.Serializable;
import java.util.Vector;

import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Faculty implements Databasable,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Vector<Program> programs;
	private Vector<Educator> educators;
	
	public Faculty(String name, Vector<Program> programs, Vector<Educator> educators)
	{
		this.name = name;
		this.programs = (Vector<Program>) programs.clone();
		this.educators = (Vector<Educator>) educators.clone();
	}
	
	public Faculty()
	{
		programs=new Vector<Program>();
		educators=new Vector<Educator>();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)||(obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
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
	@TableInput(order=1,text="##Name_Faculty_Table##")
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

	@OutDatabase(Educator.class)
	public void setEducators(Vector<Educator> educators)
	{
		this.educators = educators;
	}
	
	@InDatabase
	public Vector<Educator> getEducators()
	{
		return educators;
	}
	
	@Override
	public String toString() 
	{
		return getName();
	}
}