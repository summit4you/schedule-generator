package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Extension of {@link VEvent}. Includes methods to facilitate the use of VEvent
 * @author Zjef
 * @version 1.0
 */
public class CourseEvent extends VEvent
{
	/**
	 * See {@link VEvent#VEvent(Date, Date, String)}
	 */
	public CourseEvent(Date start,Date end,String description)
	{
		super(start,end,description);
	}
	
	/**
	 * See {@link VEvent#VEvent(Date, Date, String)}
	 */
	public CourseEvent(Calendar day, Hour start,Hour duration,String eventName)
	{
		super(new DateTime(new GregorianCalendar(day.get(Calendar.YEAR),day.get(Calendar.MONTH),day.get(Calendar.DAY_OF_MONTH),start.getHour(),start.getMinutes()).getTime()),new DateTime(getEnd(day,start,duration).getTime()),eventName);
	}
	
	/**
	 * Used by {@link ²#CourseEvent(Calendar, Hour, Hour, String)}
	 */
	private static Calendar getEnd(Calendar day,Hour start,Hour duration)
	{
		Calendar endTime=(Calendar)new GregorianCalendar(day.get(Calendar.YEAR),day.get(Calendar.MONTH),day.get(Calendar.DAY_OF_MONTH),start.getHour(),start.getMinutes()).clone();
		endTime.add(Calendar.HOUR_OF_DAY,duration.getHour());
		endTime.add(Calendar.MINUTE,duration.getMinutes());
		return endTime;
	}
	
	/**
	 * @param location - location for this CourseEvent
	 */
	public void setLocation(String location)
	{
		try
		{
			getProperty(Property.LOCATION).setValue(location);
		} catch (Exception e)
		{
		}
	}	
}