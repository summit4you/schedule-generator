package planner;

import java.util.Vector;

import dataStructure2.Subcourse;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */
public class Subcourseblock
{
	private Subcourse subcourse;
	private int hours;
	
	public Subcourseblock(Subcourse subcourse,int hours)
	{
		super();
		this.subcourse = subcourse;
		this.hours = hours;
	}
	
	public Subcourse getSubcourse()
	{
		return subcourse;
	}

	public void setSubcourse(Subcourse subcourse)
	{
		this.subcourse = subcourse;
	}
	
	public int getHours()
	{
		return hours;
	}

	public void setHours(int hours)
	{
		this.hours = hours;
	}
}
