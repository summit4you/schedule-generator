package dataStructure2;

import java.util.Vector;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Subcourse
{

	private String Name; //Dit wordt vermeld in het rooster.
	private int studentCounter;
	private String properties;
	private int totalnumberHours; //Het hele aantal uren
	private int blockHours; //Het minimum aantal uren per blok
	private Hardware neededHardware;
	private Vector<Educator> educators;
	
	public Subcourse(String name, int studentCounter, String properties, int totalnumberHours, int blockHours, Hardware neededHardware, Vector<Educator> educators)
	{
		super();
		Name = name;
		this.studentCounter = studentCounter;
		this.properties = properties;
		this.totalnumberHours = totalnumberHours;
		this.blockHours = blockHours;
		this.neededHardware = neededHardware;
		this.educators = educators;
	}
	
	public Subcourse()
	{
		
	}

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public int getStudentCounter()
	{
		return studentCounter;
	}

	public void setStudentCounter(int studentCounter)
	{
		this.studentCounter = studentCounter;
	}

	public String getProperties()
	{
		return properties;
	}

	public void setProperties(String properties)
	{
		this.properties = properties;
	}

	public int getTotalnumberHours()
	{
		return totalnumberHours;
	}

	public void setTotalnumberHours(int totalnumberHours)
	{
		this.totalnumberHours = totalnumberHours;
	}

	public int getBlockHours()
	{
		return blockHours;
	}

	public void setBlockHours(int blockHours)
	{
		this.blockHours = blockHours;
	}

	public Hardware getNeededHardware()
	{
		return neededHardware;
	}

	public void setNeededHardware(Hardware neededHardware)
	{
		this.neededHardware = neededHardware;
	}

	public Vector<Educator> getEducators()
	{
		return educators;
	}

	public void setEducators(Vector<Educator> educators)
	{
		this.educators = educators;
	}
}
