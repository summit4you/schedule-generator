package dataStructure;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version 2.0
 */
public class Subcourse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String Name; //Dit wordt vermeld in het rooster.
	private int studentCounter;
	private String properties;
	private int totalnumberHours; //Het hele aantal uren
	private int blockHours; //Het minimum aantal uren per blok
	private int hoursPerWeek;
	private int beginWeek;
	private int semester;
	private Vector<Hardware> neededHardware;
	private Vector<Educator> educators;
	
	
	public Subcourse(String name, int studentCounter, String properties, int totalnumberHours, int blockHours,Vector<Hardware> neededHardware, Vector<Educator> educators)
	{
		super();
		Name = name;
		this.studentCounter = studentCounter;
		this.properties = properties;
		this.totalnumberHours = totalnumberHours;
		this.blockHours = blockHours; //Je kan best blockHours gewoon op 1 instellen, makkelijker om in te stellen!
		this.neededHardware = (Vector<Hardware>) neededHardware.clone();
		this.educators = (Vector<Educator>) educators.clone();
		educators = new Vector<Educator>();
		neededHardware = new Vector<Hardware>();
	}
	
	public Subcourse()
	{
		neededHardware = new Vector<Hardware>();
		educators = new Vector<Educator>();
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
	
	public void incStudentCounter()
	{
		studentCounter = studentCounter+1;
	}
	
	public void decStudentCounter()
	{
		studentCounter = studentCounter-1;
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

	public Vector<Hardware> getNeededHardware()
	{
		return (Vector<Hardware>) neededHardware.clone();
	}

	public void setNeededHardware(Vector<Hardware> neededHardware)
	{
		this.neededHardware = (Vector<Hardware>) neededHardware.clone();
	}

	public Vector<Educator> getEducators()
	{
		return (Vector<Educator>) educators.clone();
	}

	public void setEducators(Vector<Educator> educators)
	{
		this.educators =  (Vector<Educator>) educators.clone();
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

	public String getCalendarfile()
	{
		return id.toString();
	}
	
	@Override
	public String toString()
	{
		return getName();
	}

	public void addEducator(Educator educator)
	{
		if (!educators.contains(educator))
		{
			educators.add(educator);
		}		
	}

	public void removeEducator(Educator educator)
	{
		educators.remove(educator);
	}
}