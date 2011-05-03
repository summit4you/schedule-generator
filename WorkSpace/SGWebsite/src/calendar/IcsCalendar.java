package calendar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.HasPropertyRule;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

/**
 * Extension of the iCal net.fortuna.ical4j.model.Calendar<br>
 * This extension should make it easier to create and manage calendars.<br><br>
 * @author Zjef
 * @version 1.0
 */
public class IcsCalendar
{
	final public static String fileExtension=".ics";
	
	private String filename;
	private Calendar cal;
	
	public IcsCalendar(String filename)
	{
		this(new Calendar(),filename);
	}
	
	private IcsCalendar(Calendar cal,String filename)
	{
		setFilename(filename);
		if (cal==null)
		{
			cal=new Calendar();
			cal.getProperties().add(new ProdId("Calendar"));
			cal.getProperties().add(CalScale.GREGORIAN);
			cal.getProperties().add(Version.VERSION_2_0);
		}
		else
		{
			this.cal=cal;
		}
	}
	
	public Calendar getCalendar()
	{
		return cal;
	}
	
	public void addSubCourseEvent(SubCourseEvent event)
	{
		cal.getComponents().add(event);
	}
	
	/**
	 * @return <code>true</code> if the specified range of time overlaps with any event in the calendar
	 */
	public boolean overlaps(java.util.Calendar start,java.util.Calendar end)
	{
		Period period = new Period(new DateTime(Transformation.calendarToDate(start)),new DateTime(Transformation.calendarToDate(end)));
		return new Filter(new PeriodRule(period)).filter(cal.getComponents(Component.VEVENT)).size()>0;
	}
	
	/**
	 * @return all events in this CourseCalendar
	 */
	public Vector<VEvent> getEvents()
	{
		return new Vector<VEvent>(cal.getComponents(Component.VEVENT));
	}
	
	public VEvent getEvent(Uid uid)
	{
		for (VEvent event:getEvents())
		{
			if (event.getUid().equals(uid))
			{
				return event;
			}
		}
		return null;
	}
	
	public SubCourseEvent getSubCourseEvent(Uid uid)
	{
		VEvent event=getEvent(uid);
		return event==null?null:new SubCourseEvent(event);
	}
	
	/**
	 * @return all events on the specified location
	 */
	public Vector<VEvent> getEvents(String location)
	{
		return new Vector<VEvent>(new Filter(new HasPropertyRule(new Location(location))).filter(cal.getComponents(Component.VEVENT)));
	}

	public String getFilename()
	{
		return filename;
	}
	
	public void setFilename(String filename)
	{
		this.filename=filename.endsWith(fileExtension)?filename:filename+fileExtension;
	}
	
	/**
	 * Reads an iCal fil, and loads the events
	 * @return <code>null</code> if an error occurred while reading the file.
	 */
	public static IcsCalendar load(String filename)
	{
		try
		{
			FileInputStream in=new FileInputStream(filename);
			CalendarBuilder builder=new CalendarBuilder();
			return new IcsCalendar(builder.build(in),filename);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Writes this calendar to a file, specified by the filename of this CourseCalendar
	 */
	public void write()
	{
		try
		{
			FileOutputStream out=new FileOutputStream(filename);
			new CalendarOutputter().output(cal,out);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] ae)
	{
		System.out.println(new Uid("20110315T220026Z-1@fe80:0:0:0:3d3d:3820:d39b:1187!!").equals(new Uid("20110315T220026Z-1@fe80:0:0:0:3d3d:3820:d39b:1187%13")));
	}
}