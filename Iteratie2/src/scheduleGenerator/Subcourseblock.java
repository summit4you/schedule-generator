package scheduleGenerator;

import java.util.Vector;

import dataStructure.Course;
import dataStructure.Room;
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
	/**
	 * Als stmIndex ==-1, wilt dit zeggen dat er geen plaats is toegekend voor deze block.
	 * */
	private int stmIndex;
	private int week;
	private int hourInDay;
	private int day;
	private int room;
	
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
					sub.getBlocks().add(block);
					tot = tot-blockhours;
				}
				if(tot>0)
				{
					Subcourseblock block = new Subcourseblock(sub,tot);
					blocks.add(block);
					sub.getBlocks().add(block);
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
				int weekCounter = sub.getBeginWeek();
				int hoursPerWeek = sub.getHoursPerWeek();
				int hoursInWeek = 0;
				
				System.out.println(weekCounter);
				while(tot > blockhours)
				{
					Subcourseblock block = new Subcourseblock(sub,blockhours);
					Vector<Subcourseblock> week = weeks.elementAt(weekCounter);
					week.add(block);
					hoursInWeek = hoursInWeek + blockhours;
					tot = tot-blockhours;
					if((hoursInWeek >= hoursPerWeek) && (weekCounter<weeks.size()-1))
					{
						hoursInWeek = 0;
						weekCounter++;
					}
				}
				if(tot > 0)
				{
					if((hoursInWeek + tot >= hoursPerWeek) && (weekCounter<weeks.size()-1))
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

	public void setStmIndex(int stmIndex) 
	{
		this.stmIndex = stmIndex;
	}

	public int getStmIndex() 
	{
		return stmIndex;
	}

	public void setWeek(int week) 
	{
		this.week = week;
	}

	public int getWeek() 
	{
		return week;
	}
	
	public int getHourInDay() 
	{
		return hourInDay;
	}

	public void setHourInDay(int hourInDay) 
	{
		this.hourInDay = hourInDay;
	}

	public int getDay() 
	{
		return day;
	}

	public void setDay(int day) 
	{
		this.day = day;
	}

	public void setRoom(int room)
	{
		this.room = room;
	}

	public int getRoom() 
	{
		return room;
	}
	
	
}
