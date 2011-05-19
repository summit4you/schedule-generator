package dataStructure;

import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

import java.io.Serializable;
import java.util.Vector;

import scheduleGenerator.Subcourseblock;

import calendar.CalendarKeeper;
import calendar.IcsCalendar;

import database.*;
/**
 * 
 * @author matthiascaenepeel
 * @version 2.0
 */

public class Subcourse implements Databasable,HTMLTablable,CalendarKeeper,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String Name; //Dit wordt vermeld in het rooster.
	private int studentCounter;
	private String properties;
	private int totalnumberHours; //Het hele aantal uren
	private int blockHours; //Het minimum aantal uren per blok
	private Vector<Hardware> neededHardware;
	private Vector<Educator> educators;
	private Course course;
	private int beginWeek;
	private int hoursPerWeek;
	private int semester;
	transient private Vector<Subcourseblock> blocks;
	
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
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)||(obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
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
	@TableInput(order=4,text="##ID##")
	public ID getID()
	{
		return id;
	}

	public String getCalendarfile()
	{
		return id.toString();
	}
	
	public Course getCourse() 
	{
		return course;
	}

	public void setCourse(Course course) 
	{
		this.course = course;
	}

	@InDatabase
	public int getBeginWeek() 
	{
		return beginWeek;
	}

	@OutDatabase
	public void setBeginWeek(int beginWeek)
	{
		this.beginWeek = beginWeek;
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

	@Override
	public IcsCalendar getCalendar()
	{
		return calendar.Translator.loadSubcourseCalendar(id.toString());
	}
	
	public Vector<Subcourseblock> getBlocks() 
	{
		return blocks;
	}

	public void setBlocks(Vector<Subcourseblock> blocks) 
	{
		this.blocks = blocks;
	}
	
	public void addBlock(Subcourseblock block)
	{
		this.getBlocks().add(block);
	}

	public void removeBlock(Subcourseblock block)
	{
		this.getBlocks().remove(block);
	}

	public void setSemester(int semester)
	{
		this.semester = semester;
	}

	public int getSemester()
	{
		return semester;
	}
	
	@Override
	public String toString()
	{
		return Name;
	}
}