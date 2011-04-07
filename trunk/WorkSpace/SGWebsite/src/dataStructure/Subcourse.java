package dataStructure;

import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

import java.util.Vector;

import database.*;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Subcourse implements Databasable,HTMLTablable
{

	private String Name; //Dit wordt vermeld in het rooster.
	private int studentCounter;
	private String properties;
	private int totalnumberHours; //Het hele aantal uren
	private int blockHours; //Het minimum aantal uren per blok
	private Vector<Hardware> neededHardware;
	private Vector<Educator> educators;
	private String calendarfile;
	
	
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

	@InDatabase
	@TableInput(order=1,text="##Name_SubCourse_Table##")
	public String getName()
	{
		return Name;
	}

	@OutDatabase
	public void setName(String name)
	{
		Name = name;
	}

	@InDatabase
	public int getStudentCounter()
	{
		return studentCounter;
	}

	@OutDatabase
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

	@InDatabase
	public String getProperties()
	{
		return properties;
	}

	@OutDatabase
	public void setProperties(String properties)
	{
		this.properties = properties;
	}

	@InDatabase
	@TableInput(order=2,text="##NumberHours_SubCourse_Table##")
	public int getTotalnumberHours()
	{
		return totalnumberHours;
	}

	@OutDatabase
	public void setTotalnumberHours(int totalnumberHours)
	{
		this.totalnumberHours = totalnumberHours;
	}

	@InDatabase
	@TableInput(order=3,text="##BlockHours_SubCourse_Table##")
	public int getBlockHours()
	{
		return blockHours;
	}

	@OutDatabase
	public void setBlockHours(int blockHours)
	{
		this.blockHours = blockHours;
	}

	@InDatabase
	public Vector<Hardware> getNeededHardware()
	{
		return (Vector<Hardware>) neededHardware.clone();
	}

	@OutDatabase(Hardware.class)
	public void setNeededHardware(Vector<Hardware> neededHardware)
	{
		this.neededHardware = (Vector<Hardware>) neededHardware.clone();
	}

	@InDatabase
	public Vector<Educator> getEducators()
	{
		return (Vector<Educator>) educators.clone();
	}

	@OutDatabase(Educator.class)
	public void setEducators(Vector<Educator> educators)
	{
		this.educators =  (Vector<Educator>) educators.clone();
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

	public void setCalendarfile(String calendarfile)
	{
		this.calendarfile = calendarfile;
	}

	public String getCalendarfile()
	{
		return calendarfile;
	}
}
