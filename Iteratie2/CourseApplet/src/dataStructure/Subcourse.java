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
		this();
		Name = name;
		this.studentCounter = studentCounter;
		this.properties = properties;
		this.totalnumberHours = totalnumberHours;
		this.blockHours = blockHours; //Je kan best blockHours gewoon op 1 instellen, makkelijker om in te stellen!
		this.neededHardware = (Vector<Hardware>) neededHardware.clone();
		this.educators = (Vector<Educator>) educators.clone();
	}
	
	public Subcourse()
	{
		neededHardware = new Vector<Hardware>();
		educators = new Vector<Educator>();
	}

	public Subcourse(String name)
	{
		this();
		this.Name=name;
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

	public void setHoursPerWeek(int hoursPerWeek)
	{
		this.hoursPerWeek = hoursPerWeek;
	}

	public int getHoursPerWeek()
	{
		return hoursPerWeek;
	}

	public void setBeginWeek(int beginWeek)
	{
		this.beginWeek = beginWeek;
	}

	public int getBeginWeek()
	{
		return beginWeek;
	}

	public void setSemester(int semester)
	{
		this.semester = semester;
	}

	public int getSemester()
	{
		return semester;
	}
	
	public void addEducator(Educator e)
	{
		if (e!=null && !educators.contains(e))
		{
			educators.add(e);
			e.addSubcourse(this);
		}
	}
	
	public void removeEducator(Educator e)
	{
		if (e!=null)
		{
			educators.remove(e);
			e.removeSubcourse(this);
		}
	}
}