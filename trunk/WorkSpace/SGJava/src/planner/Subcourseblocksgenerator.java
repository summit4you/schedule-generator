package planner;

import java.util.Iterator;
import java.util.Vector;

import dataStructure2.Subcourse;
import dataStructure2.Course;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Subcourseblocksgenerator
{
	private Course course;
	
	public Subcourseblocksgenerator(Course course)
	{
		super();
		this.course = course;
	}

	public Course getCourse()
	{
		return course;
	}

	public void setCourse(Course course)
	{
		this.course = course;
	}
	
	static public Vector<Subcourseblock> generateBlocks(Course course)
	{	
		Vector<Subcourseblock> blocks = new Vector<Subcourseblock>();
		Iterator<Subcourse> i =course.getSubcourses().iterator();
		while(i.hasNext())
		{
			Subcourse sub=i.next();
			int tot = sub.getTotalnumberHours();
			int block = sub.getBlockHours();
			while(tot > block)
			{
				blocks.add(new Subcourseblock(sub,block));
				tot = tot-block;
			}
			if(tot>0)
			{
				blocks.add(new Subcourseblock(sub,tot));
			}
		}
		return blocks;
	}
	
}
