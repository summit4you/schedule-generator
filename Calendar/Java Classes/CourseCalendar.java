package calendar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.HasPropertyRule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Location;

/**
 * Extension of the iCal net.fortuna.ical4j.model.Calendar<br>
 * This extension should make it easier to create and manage calendars.<br><br>
 * @author Zjef
 * @version 1.0
 */
public class CourseCalendar extends Calendar
{
	final public static String fileExtension=".ics";
	
	private String filename;
	
	public CourseCalendar(String filename)
	{
		super();
		setFilename(filename);
	}
	
	public void addEvent(VEvent event)
	{
		getComponents().add(event);
	}
	
	/**
	 * @return all events in this CourseCalendar
	 */
	public Vector<VEvent> getEvents()
	{
		return new Vector<VEvent>(getComponents(Component.VEVENT));
	}
	
	/**
	 * @return all events on the specified location
	 */
	public Vector<VEvent> getEvents(String location)
	{
		return new Vector<VEvent>(new Filter(new HasPropertyRule(new Location(location))).filter(getComponents(Component.VEVENT)));
	}

	public String getFilename()
	{
		return filename;
	}
	
	public void setFilename(String filename)
	{
		if (!filename.endsWith(fileExtension))
		{
			filename+=fileExtension;
		}
		this.filename=filename;
	}
	
	/**
	 * Reads an iCal file
	 * @return <code>null</code> if an error occurred while reading the file.
	 */
	public static CourseCalendar load(String filename)
	{
		try
		{
			FileInputStream in=new FileInputStream(filename);
			CalendarBuilder builder=new CalendarBuilder();
			CourseCalendar c=(CourseCalendar)builder.build(in);
			c.setFilename(filename);
			return c;
		} catch (Exception e)
		{
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
			new CalendarOutputter().output(this, out);
		} catch (Exception e)
		{
		}
	}
}