package planner;


import java.util.Vector;


import dataStructure.Subcourse;
import dataStructure.Course;
/**
 * 
 * @author matthiascaenepeel
 * @version1.1
 */

public class Subcourseblocksgenerator
{
	static public Vector<Subcourseblock> generateBlocks(Vector<Course> courses)
	{	
		Vector<Subcourseblock> blocks = new Vector<Subcourseblock>();
		
		for(Course course:courses)
		{
			Vector<Subcourse> subs = course.getSubcourses();
			for(Subcourse sub:subs)
			{
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
		}
			return blocks;
	}
	
	static public Vector<Integer> giveHours(Vector<Subcourseblock> blocks)
	{
		Vector<Integer> hours = new Vector<Integer>();
		for(Subcourseblock block:blocks)
		{
			hours.add(block.getHours());
		}
		return hours;
	}
	
	static public Vector<Subcourse> giveSubcourses(Vector<Subcourseblock> blocks)
	{
		Vector<Subcourse> subcourses = new Vector<Subcourse>();
		for(Subcourseblock block:blocks)
		{
			subcourses.add(block.getSubcourse());
		}
		return subcourses;
	}

	
}
