package calendar;

import java.util.Calendar;
import java.util.Vector;

/**
 * Converts a start and end date to a vector of all dates in between.
 * @author Zjef
 * @version 1.0
 */
public class AcademicYear
{
	private Vector<Calendar> days;
	private Vector<Integer> includedDays;
	
	/**
	 * Creates all days in this year, from start to stop.<br>
	 * Start and end can be in different years.
	 * @param start - first day of this AcademicYear
	 * @param end - last day of this AcademnicYear (this day is still included)
	 */
	public AcademicYear(Calendar start,Calendar end)
	{
		this(start,end,null);
	}
	
	/**
	 * Creates all days in this year, from start to stop.<br>
	 * Start and end can be in different years.<br>
	 * @param start - first day of this AcademicYear
	 * @param end - last day of this AcademnicYear (this day is still included)
	 * @param includedDays - optional field, if specified, only days (monday,tuesday,...) of this type will be added to this AcademicYear<br>
	 * The integer values of the days are the values declared in {@link java.util.Calendar}. ({@link Calendar#MONDAY} etc.)<br>
	 * If this vector is <code>null</code> then all days will be included.
	 */
	public AcademicYear(Calendar start,Calendar end,Vector<Integer> includedDays)
	{
		if (includedDays==null)
		{
			this.includedDays=null;
		}
		else
		{
			this.includedDays=(Vector<Integer>)includedDays.clone();
		}
		generate(start,end);
	}
	
	/**
	 * generates the days of this AcademicCalendar
	 */
	private void generate(Calendar start,Calendar end)
	{
		days=new Vector<Calendar>(0);
		days.add(start);
		Calendar next=(Calendar)start.clone();
		do 
		{
			next=((Calendar)next.clone());
			next.add(Calendar.DAY_OF_YEAR,1);
			if ((includedDays==null)||(includedDays.contains(next.get(Calendar.DAY_OF_WEEK))))
			{
				days.add(next);
			}
		}while(next.compareTo(end)!=0);
	}
	
	/**
	 * @return the first day of this AcademicYear
	 */
	public Calendar getStart()
	{
		return days.get(0);
	}
	
	/**
	 * @return the last day of this AcademicYear
	 */
	public Calendar getEnd()
	{
		return days.lastElement();
	}
	
	/**
	 * @return a copy of the days of this AcademicYear.<br>
	 * The days are sorted by day. The first element is the first day, the last element is the last day.
	 */
	public Vector<Calendar> getDays()
	{
		return (Vector<Calendar>) days.clone();
	}
	
	/**
	 * Removes a day from this AcademicCalendar<br>
	 * This has to be an object inside the vector returned by {@link #getDays()}
	 * @param day - day to remove
	 */
	public void removeDay(Calendar day)
	{
		days.remove(day);
	}
}