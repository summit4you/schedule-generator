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
	private Course course;
	private int beginweek;
	private int hoursPerWeek;
	
	public Subcourse(String name, int studentCounter, String properties, int totalnumberHours, int blockHours,Vector<Hardware> neededHardware)
	{
		super();
		Name = name;
		this.studentCounter = studentCounter;
		this.properties = properties;
		this.totalnumberHours = totalnumberHours;
		this.blockHours = blockHours; //Je kan best blockHours gewoon op 1 instellen, makkelijker om in te stellen!
		this.neededHardware = (Vector<Hardware>) neededHardware.clone();
		neededHardware = new Vector<Hardware>();
	}
	
	public Subcourse()
	{
		neededHardware = new Vector<Hardware>();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
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

	public String getCalendarfile()
	{
		return id.toString();
	}

	public void setCourse(Course course) 
	{
		this.course = course;
	}

	public Course getCourse() 
	{
		return course;
	}
	
	@InDatabase
	public int getBeginweek() 
	{
		return beginweek;
	}

	@OutDatabase
	public void setBeginweek(int beginweek) 
	{
		this.beginweek = beginweek;
	}

	@InDatabase
	public int getHoursPerWeek() 
	{
		return hoursPerWeek;
	}

	@OutDatabase
	public void setHoursPerWeek(int hoursPerWeek)
	{
		this.hoursPerWeek = hoursPerWeek;
	}
}
