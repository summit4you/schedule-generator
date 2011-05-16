package scheduleGenerator;

import java.util.Vector;

import dataStructure.Course;
import dataStructure.Subcourse;

/**
 * 
 * @author matthiascaenepeel
 * @version0.1
 */
public class Subcourseblock
{
	private Subcourse subcourse;
	private int hours;
	
	public Subcourseblock(Subcourse sub, int hours) 
	{
		super();
		this.subcourse = sub;
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
	
	//Methode om een vector van blocks te genereren!
	
	@Deprecated
	static public Vector<Subcourseblock> generateBlocks(Vector<Course> courses)
	{	
		Vector<Subcourseblock> blocks = new Vector<Subcourseblock>();
		
		for(Course course:courses)
		{
			Vector<Subcourse> subs = course.getSubcourses();
			for(Subcourse sub:subs)
			{
				//Hier wordt de link gemaakt van de subcourse naar de course!
				sub.setCourse(course);
				int tot = sub.getTotalnumberHours();
				int blockhours = sub.getBlockHours();
				while(tot > blockhours)
				{	
					Subcourseblock block = new Subcourseblock(sub,blockhours);
					blocks.add(block);
					tot = tot-blockhours;
				}
				if(tot>0)
				{
					blocks.add(new Subcourseblock(sub,tot));
				}
			}
		}
			return blocks;
	}
	
	
	static public Vector<Vector<Subcourseblock>> generateBlocksPerWeek(Vector<Course> courses,int numberOfWeeks)
	{
		Vector<Vector<Subcourseblock>> weeks = new Vector<Vector<Subcourseblock>>();
		
		for(int i=0;i<numberOfWeeks;i++)
		{
			Vector<Subcourseblock> week = new Vector<Subcourseblock>();
			weeks.add(week);
		}
		
		for(Course course:courses)
		{
			Vector<Subcourse> subs = course.getSubcourses();
			for(Subcourse sub:subs)
			{
				sub.setCourse(course);
				int tot = sub.getTotalnumberHours();
				int blockhours = sub.getBlockHours();
				int weekCounter = sub.getBeginweek();
				int hoursPerWeek = sub.getHoursPerWeek();
				int hoursInWeek = 0;
				
				while(tot > blockhours)
				{
					Subcourseblock block = new Subcourseblock(sub,blockhours);
					Vector<Subcourseblock> week = weeks.elementAt(weekCounter);
					week.add(block);
					hoursInWeek = hoursInWeek + blockhours;
					tot = tot-blockhours;
					if((hoursInWeek >= hoursPerWeek) && (weekCounter<weeks.size()))
					{
						hoursInWeek = 0;
						weekCounter++;
					}
				}
				if(tot > 0)
				{
					if((hoursInWeek + tot >= hoursPerWeek) && (weekCounter<weeks.size()))
					{
						hoursInWeek = 0;
						weekCounter++;
					}
					Subcourseblock block = new Subcourseblock(sub,tot);
					Vector<Subcourseblock> week = weeks.elementAt(weekCounter);
					week.add(block);
				}
				
			}
		}
		
		return weeks;
	}
}
